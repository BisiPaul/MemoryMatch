package com.bistronic.memorymatchwevideo.components.scoreboard

import androidx.recyclerview.widget.RecyclerView
import com.bistronic.memorymatchwevideo.databinding.ItemScoreBinding

/**
 * Created by paulbisioc on 12/4/2020.
 */
class ScoreViewHolder(val binding: ItemScoreBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(score: RecyclerItem.ScoreItem) {
        binding.itemScoreNameTV.text = score.name
        binding.itemScoreScoreTV.text = score.score.toString()
    }
}