package com.bistronic.memorymatchwevideo.components.game.cards

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.bistronic.memorymatchwevideo.R
import com.bistronic.memorymatchwevideo.databinding.ItemCardBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

/**
 * Created by paulbisioc on 11/30/2020.
 */
class CardViewHolder(
    val binding: ItemCardBinding,
    val listener: OnCardClickedListener,
    val adapter: CardsAdapter
) :
    RecyclerView.ViewHolder(binding.root) {
    private var mCard: Card? = null
    private val mAdapter: CardsAdapter = adapter
    fun bind(card: Card) {
        mCard = card
        setImage()
        setControls()
    }

    private fun setControls() {
        itemView.setOnClickListener {
            // This blocks the user from clicking other Cards while there are 2 cards already selected
            if (mAdapter.canCardsBeClicked())
                when (mCard?.cardState) {
                    CardState.FACE_UP -> {
                        mCard?.cardState = CardState.NOT_FOUND
                        setImage()
                    }
                    CardState.NOT_FOUND -> {
                        mCard?.cardState = CardState.FACE_UP
                        setImage()
                        mAdapter.performCardClickAdapter(Pair(mCard, adapterPosition))
                    }
                    CardState.FOUND -> {
                        // do nothing when user clicks an already found card
                    }
                }
        }
    }

    private fun setImage() {
        when (mCard?.cardState) {
            CardState.FOUND -> {
                Glide.with(binding.root.context)
                    .asBitmap()
                    .load(mCard?.image)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .apply(RequestOptions().placeholder(R.drawable.ic_wevideo_logo))
                    .into(binding.cardImageIV)
                // Set a gray color filter over the already found Cards
                binding.cardImageIV.setColorFilter(Color.GRAY)
            }
            CardState.NOT_FOUND -> {
                binding.cardImageIV.setImageResource(R.drawable.bg_card_back_side)
            }
            CardState.FACE_UP -> {
                Glide.with(binding.root.context)
                    .load(mCard?.image)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .apply(RequestOptions().placeholder(R.drawable.ic_wevideo_logo))
                    .into(binding.cardImageIV)
            }
        }
    }
}