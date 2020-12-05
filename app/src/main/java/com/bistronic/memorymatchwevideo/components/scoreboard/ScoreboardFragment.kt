package com.bistronic.memorymatchwevideo.components.scoreboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bistronic.memorymatchwevideo.R
import com.bistronic.memorymatchwevideo.core.ViewModelFactory
import com.bistronic.memorymatchwevideo.data.model.Score
import com.bistronic.memorymatchwevideo.databinding.FragmentScoreboardBinding
import com.bistronic.memorymatchwevideo.utils.Constants
import com.bistronic.memorymatchwevideo.utils.SharedPreferencesUtils

/**
 * Created by paulbisioc on 11/30/2020.
 */
class ScoreboardFragment : Fragment(), InsertNameDialogFragmentInteractionListener {
    private lateinit var binding: FragmentScoreboardBinding
    private lateinit var viewModel: ScoreboardViewModel

    private val args: ScoreboardFragmentArgs by navArgs()
    private val scoreboardAdapter = ScoreboardAdapter()

    private val insertNameDialogFragment: InsertNameDialogFragment =
        InsertNameDialogFragment(R.layout.fragment_dialog_insert_name, this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_scoreboard, container, false)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(requireContext())
        ).get(ScoreboardViewModel::class.java)

        viewModel.insert(Score(123, GameType.TWO_BY_TWO.text, "Xardas", 420))
        viewModel.insert(Score(1234, GameType.SIX_BY_SIX.text, "Milo", 210))
        viewModel.insert(Score(12223, GameType.FOUR_BY_FOUR.text, "Lester", 120))

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        configView()
        setControls()
        observe()
    }

    private fun configView() {
        binding.recyclerView.apply {
            adapter = scoreboardAdapter
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
        }
        viewModel.getAllScores()
        arguments?.getString(Constants.BUNDLE_KEY_GAME_TYPE)?.let { gameType ->
            arguments?.getInt(Constants.BUNDLE_KEY_SCORE)?.let { score ->
                SharedPreferencesUtils.score = score
                SharedPreferencesUtils.gameType = gameType
                showInsertNameDialog()
            }
        }
    }

    private fun setControls() {

    }

    private fun observe() = with(viewModel) {
        getAllScores().observe(viewLifecycleOwner, Observer<List<Score>> { list ->
            updateUI(list)
        })
    }

    private fun updateUI(list: List<Score>) {
        if (!list.isNullOrEmpty()) {
            val sectionedList: List<RecyclerItem> = list
                .groupBy { it.gameType }
                .flatMap { (category, scores) ->
                    listOf<RecyclerItem>(RecyclerItem.SectionItem(category)) + scores.map {
                        RecyclerItem.ScoreItem(
                            it.name,
                            it.score
                        )
                    }
                }

            scoreboardAdapter.setAdapterData(sectionedList)
        }
    }

    private fun showInsertNameDialog() {
        if (!insertNameDialogFragment.isAdded && !insertNameDialogFragment.isVisible)
            insertNameDialogFragment.show(
                parentFragmentManager,
                InsertNameDialogFragment::class.simpleName
            )
    }

    override fun onDone(name: String) {
        viewModel.insert(
            Score(
                System.nanoTime().toInt(),
                SharedPreferencesUtils.gameType,
                name,
                SharedPreferencesUtils.score
            )
        )
        insertNameDialogFragment.dismiss()
        SharedPreferencesUtils.gameType = ""
        SharedPreferencesUtils.score = 0

    }
}