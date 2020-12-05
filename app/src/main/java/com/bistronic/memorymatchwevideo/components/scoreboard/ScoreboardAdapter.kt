package com.bistronic.memorymatchwevideo.components.scoreboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bistronic.memorymatchwevideo.R

/**
 * Created by paulbisioc on 12/4/2020.
 */
class ScoreboardAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var scores: ArrayList<RecyclerItem> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            TYPE_SECTION -> {
                SectionViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_section,
                        parent,
                        false
                    )
                )
            }
            TYPE_SCORE -> {
                ScoreViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_score,
                        parent,
                        false
                    )
                )
            }
            else -> {
                ScoreViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_score,
                        parent,
                        false
                    )
                )
            }
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = scores[holder.adapterPosition]) {
            is RecyclerItem.SectionItem -> (holder as SectionViewHolder).bind(item)
            is RecyclerItem.ScoreItem -> (holder as ScoreViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int {
        return scores.size
    }

    override fun getItemViewType(position: Int): Int = when (scores[position]) {
        is RecyclerItem.SectionItem -> TYPE_SECTION
        is RecyclerItem.ScoreItem -> TYPE_SCORE
    }

    fun setAdapterData(sectionedScores: List<RecyclerItem>) {
        scores.clear()
        scores.addAll(sectionedScores)
        notifyDataSetChanged()
    }

    companion object {
        private val TYPE_SECTION = 0
        private val TYPE_SCORE = 1
    }
}