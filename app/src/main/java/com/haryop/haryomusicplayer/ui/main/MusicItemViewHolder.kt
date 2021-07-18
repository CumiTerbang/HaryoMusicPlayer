package com.haryop.synpulsefrontendchallenge.ui.companylist

import androidx.recyclerview.widget.RecyclerView
import com.haryop.haryomusicplayer.data.entities.ContentEntity
import com.haryop.haryomusicplayer.databinding.ItemMusicLayoutBinding
import com.haryop.synpulsefrontendchallenge.utils.setImageGlide
import java.util.*

class MusicItemViewHolder(
    private val itemBinding: ItemMusicLayoutBinding,
    private val listener: MusicListAdapter.MusicItemListener
) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(item: ContentEntity) = with(itemBinding) {
        title.text = item.trackName
        artist.text = item.artistName
        album.text = item.collectionName

        itemView.setOnClickListener { listener.onClickedItem(item.previewUrl, item.isStreamable) }

        itemView.context.setImageGlide(item.artworkUrl100, itemBinding.root, itemImage)

    }

}