package com.bistronic.memorymatchwevideo.components.scoreboard

import androidx.lifecycle.*
import com.bistronic.memorymatchwevideo.core.repositories.ScoresRoomRepository
import com.bistronic.memorymatchwevideo.data.model.Score
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by paulbisioc on 11/30/2020.
 */
class ScoreboardViewModel(
    private val repository: ScoresRoomRepository
) : ViewModel() {
    // Using LiveData and caching what getAll returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private var allScores = MediatorLiveData<List<Score>>()

    private var _scoreSuccessfullyAdded = MutableLiveData<Boolean>()
    val scoreSuccessfullyAdded: LiveData<Boolean> get() = _scoreSuccessfullyAdded

    init {
        allScores.addSource(repository.allScores, allScores::setValue)
    }

    fun getAllScores() = allScores

    fun forceGetScores() {
        repository.forceGet()
        allScores.addSource(repository.allScores, allScores::setValue)
    }

    fun insert(obj: Score) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(obj)
            _scoreSuccessfullyAdded.postValue(true)
        }
    }
}