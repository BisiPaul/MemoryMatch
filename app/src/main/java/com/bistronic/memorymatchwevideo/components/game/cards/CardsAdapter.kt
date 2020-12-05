package com.bistronic.memorymatchwevideo.components.game.cards

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bistronic.memorymatchwevideo.R

/**
 * Created by paulbisioc on 11/30/2020.
 */

class CardsAdapter(private val listener: OnCardClickedListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var cards: ArrayList<Card> = ArrayList()

    private var adapterSelectedCard_1: Pair<Card?, Int?>? = null
    private var adapterSelectedCard_2: Pair<Card?, Int?>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CardViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_card,
                parent,
                false
            ), listener, this
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CardViewHolder).bind(cards[holder.adapterPosition])
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    fun submitNewCards(newCards: ArrayList<Card>?) {
        newCards?.let { newCards ->
            cards.clear()
            cards.addAll(newCards)
            notifyDataSetChanged()
        }
    }

    fun hideAllCards() {
        cards.forEach { card ->
            if (card.cardState != CardState.FOUND)
                card.cardState = CardState.NOT_FOUND
        }
        notifyDataSetChanged()
    }

    fun hideFaceUpCards(card1: Card, card2: Card) {
        (cards.find { card -> card == card1 }).let {
            it?.cardState = CardState.NOT_FOUND
        }

        (cards.find { card -> card == card2 }).let {
            it?.cardState = CardState.NOT_FOUND
        }
    }

    fun markAsFound(card1: Card, card2: Card) {
        (cards.find { card -> card == card1 }).let {
            it?.cardState = CardState.FOUND
        }

        (cards.find { card -> card == card2 }).let {
            it?.cardState = CardState.FOUND
        }
    }

    fun resetCardsState() {
        for(i in 0 until cards.size) {
            if(cards[i].cardState != CardState.NOT_FOUND) {
                cards[i].cardState = CardState.NOT_FOUND
            }
            notifyItemChanged(i)
        }
    }

    fun shuffleCards() {
        cards.shuffle()
    }

    fun performCardClickAdapter(clickedCard: Pair<Card?, Int>) {
        when {
            adapterSelectedCard_1 == null -> {
                adapterSelectedCard_1 = clickedCard
                listener.performCardClick(clickedCard)
            }
            adapterSelectedCard_2 == null -> {
                adapterSelectedCard_2 = clickedCard
                listener.performCardClick(clickedCard)
            }
            else -> {
                // do nothing
            }
        }
    }

    fun clearSelectedCards() {
        adapterSelectedCard_1 = null
        adapterSelectedCard_2 = null
    }

    fun canCardsBeClicked() : Boolean {
        return adapterSelectedCard_1 == null || adapterSelectedCard_2 == null
    }
}