package com.haryop.synpulsefrontendchallenge.ui.companylist

import android.view.View
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

        if (item.isStreamable == true) {
            isStream.visibility = View.VISIBLE
            onIsPlaying(item.isPlaying)
        } else {
            isStream.visibility = View.GONE
        }


        itemView.setOnClickListener {
            if (item.isStreamable == true) {
                if (item.isPlaying == true) {
                    item.isPlaying = false
                } else {
                    item.isPlaying = true
                }
            }
            onIsPlaying(item.isPlaying)
            listener.onClickedItem(item.previewUrl, item.isStreamable, item.isPlaying)

        }

        itemView.context.setImageGlide(item.artworkUrl100, itemBinding.root, itemImage)

    }

    fun onIsPlaying(_isPlaying: Boolean) = with(itemBinding) {
        if (_isPlaying == true) {
            isPlaying.visibility = View.VISIBLE
        } else {
            isPlaying.visibility = View.INVISIBLE
        }

    }

}