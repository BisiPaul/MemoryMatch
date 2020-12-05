package com.bistronic.memorymatchwevideo.components.game.cards

/**
 * Created by paulbisioc on 11/30/2020.
 */
class Card(
    val id: Int,
    val image: String?
) {
    var cardState: CardState = CardState.NOT_FOUND
}

enum class CardState {
    FOUND,
    NOT_FOUND,
    FACE_UP
}