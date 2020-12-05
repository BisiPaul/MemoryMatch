package com.bistronic.memorymatchwevideo.components.scoreboard

import androidx.recyclerview.widget.RecyclerView
import com.bistronic.memorymatchwevideo.databinding.ItemSectionBinding

/**
 * Created by paulbisioc on 12/4/2020.
 */

class SectionViewHolder(val binding: ItemSectionBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(section: RecyclerItem.SectionItem) {
        binding.itemSectionTitleTV.text = section.title
    }
}