package com.bistronic.memorymatchwevideo.components.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.bistronic.memorymatchwevideo.R
import com.bistronic.memorymatchwevideo.components.game.cards.Card
import com.bistronic.memorymatchwevideo.components.game.cards.CardsAdapter
import com.bistronic.memorymatchwevideo.components.game.cards.OnCardClickedListener
import com.bistronic.memorymatchwevideo.components.scoreboard.GameType
import com.bistronic.memorymatchwevideo.core.ViewModelFactory
import com.bistronic.memorymatchwevideo.databinding.FragmentGameBinding
import com.bistronic.memorymatchwevideo.utils.SharedPreferencesUtils
import com.bistronic.memorymatchwevideo.utils.coroutineDelay

class GameFragment : Fragment(), OnCardClickedListener {
    private lateinit var viewModel: GameViewModel
    private lateinit var binding: FragmentGameBinding

    private val cardsAdapter = CardsAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        viewModel = ViewModelProvider(this, ViewModelFactory(requireContext())).get(GameViewModel::class.java)

        viewModel.fetchPhotos()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configView()
        observe()
        setControls()
    }

    private fun configView() {
        binding.recyclerView.apply {
            adapter = cardsAdapter
            layoutManager = GridLayoutManager(context, SharedPreferencesUtils.gridSize.toInt())
            itemAnimator = DefaultItemAnimator()
        }

        binding.scoreTV.text = resources.getString(R.string.score_title, 0)
    }

    private fun setControls() {
        binding.preGamePlayGameBT.setOnClickListener {
            viewModel.startGame()
        }

        binding.postGameSubmitScoreBT.setOnClickListener {
            viewModel.score.value?.let { score ->
                if (score > 0) {
                    var gameType = ""
                    when (SharedPreferencesUtils.gridSize) {
                        2f -> gameType = GameType.TWO_BY_TWO.text
                        4f -> gameType = GameType.FOUR_BY_FOUR.text
                        6f -> gameType =  GameType.SIX_BY_SIX.text
                        8f -> gameType =  GameType.EIGHT_BY_EIGHT.text
                    }
                    val action = GameFragmentDirections.actionNavigationGameToNavigationScoreboard(score, gameType)
                    this.findNavController().navigate(action)
                } else {
                    Toast.makeText(
                        this.requireActivity(),
                        resources.getString(R.string.really),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun observe() = with(viewModel) {
        loading.observe(viewLifecycleOwner, Observer { loading ->
            if (loading == null) return@Observer
            if (loading) binding.progress.visibility = View.VISIBLE
            else binding.progress.visibility = View.GONE
        })

        cards.observe(viewLifecycleOwner, Observer { arrayList ->
            cardsAdapter.submitNewCards(arrayList)
        })

        score.observe(viewLifecycleOwner, Observer {
            binding.scoreTV.text = resources.getString(R.string.score_title, it)
            binding.postGameScoreTV.text = resources.getString(R.string.final_score, it)
        })

        time.observe(viewLifecycleOwner, Observer {
            binding.timeTV.text = resources.getString(R.string.time_title, it)
            if (it == 0) {
                viewModel.endGame()
                viewModel.resetScore()
            }
        })

        gameState.observe(viewLifecycleOwner, Observer {
            when (it) {
                GameState.PRE_GAME -> {
                    binding.preGameLayoutCL.visibility = View.VISIBLE
                    binding.gameInProgressLayoutCL.visibility = View.INVISIBLE
                    binding.postGameLayoutCL.visibility = View.INVISIBLE
                }
                GameState.GAME_IN_PROGRESS -> {
                    binding.preGameLayoutCL.visibility = View.INVISIBLE
                    binding.gameInProgressLayoutCL.visibility = View.VISIBLE
                    binding.postGameLayoutCL.visibility = View.INVISIBLE
                    viewModel.startTimer()
                }
                GameState.POST_GAME -> {
                    binding.preGameLayoutCL.visibility = View.INVISIBLE
                    binding.gameInProgressLayoutCL.visibility = View.INVISIBLE
                    binding.postGameLayoutCL.visibility = View.VISIBLE
                    viewModel.stopTimer()
                    viewModel.getFinalScore()
                    viewModel.resetTimer()
                }
            }
        })
    }

    override fun performCardClick(clickedCard: Pair<Card?, Int>?) {
        // Keep track of the selected cards
        if (viewModel.selectedCard_1 == null)
            viewModel.selectedCard_1 = clickedCard
        else
            viewModel.selectedCard_2 = clickedCard

        // Wait until you have 2 cards selected
        viewModel.selectedCard_1?.let { card_1 ->
            viewModel.selectedCard_2?.let { card_2 ->
                // If the cards have the same image and different ids, you got a match
                if (card_1.first?.image == card_2.first?.image && card_1.first?.id != card_2.first?.id) {
                    card_1.first?.let {
                        card_2.first?.let { it1 ->
                            cardsAdapter.markAsFound(
                                it,
                                it1
                            )
                        }
                    }

                    card_1.second?.let { cardsAdapter.notifyItemChanged(it) }
                    card_2.second?.let { cardsAdapter.notifyItemChanged(it) }

                    cardsAdapter.clearSelectedCards()

                    viewModel.increaseScore()
                    viewModel.resetSelectedCards()
                    viewModel.increaseMatchesNumber()
                } else {
                    // Apply a delay before turning face down cards that do not match
                    coroutineDelay(1000) {
                        card_2.first?.let { it ->
                            card_1.first?.let { it1 ->
                                cardsAdapter.hideFaceUpCards(
                                    it1, it
                                )
                            }
                        }
                        card_1.second?.let { cardsAdapter.notifyItemChanged(it) }
                        card_2.second?.let { cardsAdapter.notifyItemChanged(it) }
                        viewModel.decreaseScore()
                        viewModel.resetSelectedCards()
                        cardsAdapter.clearSelectedCards()
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance() = GameFragment()
    }
}