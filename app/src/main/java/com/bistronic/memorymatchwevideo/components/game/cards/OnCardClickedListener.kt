package com.bistronic.memorymatchwevideo.components.game.cards

/**
 * Created by paulbisioc on 12/3/2020.
 */
interface OnCardClickedListener {
    // Method that links the CardsAdapter and the GameFragment
    // It gets as a parameter a pair made up by the Card and it's position in the adapter.
    fun performCardClick(card: Pair<Card?, Int>?)
}