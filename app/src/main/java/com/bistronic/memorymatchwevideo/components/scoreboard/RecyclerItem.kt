package com.bistronic.memorymatchwevideo.components.scoreboard

/**
 * Created by paulbisioc on 12/4/2020.
 */
sealed class RecyclerItem {
    data class ScoreItem(val name: String, val score: Int): RecyclerItem()
    data class SectionItem(val title: String): RecyclerItem()
}