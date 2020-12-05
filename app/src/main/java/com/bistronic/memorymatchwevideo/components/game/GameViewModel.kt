package com.bistronic.memorymatchwevideo.components.game

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bistronic.memorymatchwevideo.components.game.cards.Card
import com.bistronic.memorymatchwevideo.core.RequestResponse
import com.bistronic.memorymatchwevideo.core.repositories.LoremPicsumRepository
import com.bistronic.memorymatchwevideo.data.model.LoremPicsumPhotoModel
import com.bistronic.memorymatchwevideo.utils.SharedPreferencesUtils
import com.bistronic.memorymatchwevideo.utils.coroutineDelay
import kotlinx.coroutines.launch

class GameViewModel(
    private val loremPicsumRepository: LoremPicsumRepository
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _cards = MutableLiveData<ArrayList<Card>?>()
    val cards: MutableLiveData<ArrayList<Card>?> = _cards

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score

    private val _time = MutableLiveData<Int>()
    val time: LiveData<Int> get() = _time

    private val _gameState = MutableLiveData<GameState>()
    val gameState: LiveData<GameState> get() = _gameState

    var photoLimit = 0
    var matchesNumber = 0

    var selectedCard_1: Pair<Card?, Int?>? = null
    var selectedCard_2: Pair<Card?, Int?>? = null

    var timer: CountDownTimer? = null

    fun fetchPhotos() {
        viewModelScope.launch {
            _loading.postValue(true)

            when (SharedPreferencesUtils.gridSize) {
                2f -> photoLimit = 2
                4f -> photoLimit = 8
                6f -> photoLimit = 18
                8f -> photoLimit = 32
            }

            when (val response = loremPicsumRepository.getPhotos(photoLimit)) {
                is RequestResponse.Success -> {
                    val auxArray: MutableList<LoremPicsumPhotoModel>? = mutableListOf()
                    response.body?.let {
                        auxArray?.addAll(it)
                        // Add an "1" to the id at the second addition of the list
                        it.forEach {
                            val aux = it
                            aux.id = aux.id + "1"
                            auxArray?.add(aux)
                        }
                        auxArray?.shuffle()
                        if (auxArray != null) {
                            generateCardsArray(auxArray)
                            _score.postValue(0)
                            _time.postValue(Companion.INITIAL_TIME)
                        }
                    }
                    // Hide loading ProgressBar
                    _loading.postValue(false)
                }
                is RequestResponse.Error -> {
                    // Hide loading ProgressBar
                    _loading.postValue(false)
                }
            }
        }
    }

    private fun generateCardsArray(list: List<LoremPicsumPhotoModel>) {
        val auxArray = ArrayList<Card>()
        var index = 0
        list.forEach { photo ->
            auxArray.add(Card(index, photo.download_url))
            index++
        }
        _cards.postValue(auxArray)
    }

    fun resetSelectedCards() {
        selectedCard_1 = null
        selectedCard_2 = null
    }

    fun increaseScore() {
        score.value?.let { score ->
            _score.postValue(score.plus(2))
        }
    }

    fun decreaseScore() {
        score.value?.let { score ->
            if (score > 0)
                _score.postValue(score.minus(1))
        }
    }

    fun getFinalScore() {
        _score.postValue(_time.value?.let { seconds -> score.value?.times(seconds) })
    }

    fun resetScore() {
        _score.postValue(0)
    }

    fun setPreGameStatus() {
        _gameState.postValue(GameState.PRE_GAME)
    }

    fun startGame() {
        _gameState.postValue(GameState.GAME_IN_PROGRESS)
    }

    fun endGame() {
        _gameState.postValue(GameState.POST_GAME)
    }

    fun startTimer() {
        timer = object : CountDownTimer((INITIAL_TIME * 1000).toLong(), 1000) {
            override fun onFinish() {
                _time.value = 0
                _gameState.postValue(GameState.POST_GAME)
            }

            override fun onTick(millisUntilFinished: Long) {
                _time.value = (millisUntilFinished / 1000).toInt()
            }
        }.start()
    }

    fun stopTimer() {
        timer?.cancel()
    }

    fun resetTimer() {
        _time.value = INITIAL_TIME
    }

    fun increaseMatchesNumber() {
        matchesNumber++
        checkAllMatchesFound()
    }

    private fun resetMatchesNumber() {
        matchesNumber = 0
    }

    private fun checkAllMatchesFound() {
        if (matchesNumber == photoLimit) {
            coroutineDelay(500) {
                _gameState.postValue(GameState.POST_GAME)
                resetMatchesNumber()
            }
        }
    }

    companion object {
        const val INITIAL_TIME = 60
    }
}