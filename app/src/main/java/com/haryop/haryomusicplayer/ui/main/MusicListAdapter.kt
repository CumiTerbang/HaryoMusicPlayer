package com.haryop.synpulsefrontendchallenge.ui.companylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.haryop.haryomusicplayer.data.entities.ContentEntity
import com.haryop.haryomusicplayer.databinding.ItemBottomSpaceBinding
import com.haryop.haryomusicplayer.databinding.ItemMusicLayoutBinding
import com.haryop.haryomusicplayer.ui.BottomspaceViewHolder

class MusicListAdapter(private val listener: MusicItemListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface MusicItemListener {
        fun onClickedItem(previewUrl: String, isStreamable:Boolean)
    }

    private val items = ArrayList<Any>()

    fun getItems(): ArrayList<Any>{
        return items
    }

    fun setItems(items: ArrayList<Any>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    val ITEM_TYPE_SOURCE_ITEM_LAYOUT = 0
    val ITEM_TYPE_BOTTOMSPACE_LAYOUT = 1
    override fun getItemViewType(position: Int): Int {
        if (items[position] is ContentEntity) {
            return ITEM_TYPE_SOURCE_ITEM_LAYOUT
        } else {
            return ITEM_TYPE_BOTTOMSPACE_LAYOUT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            ITEM_TYPE_SOURCE_ITEM_LAYOUT -> {
                val binding: ItemMusicLayoutBinding =
                    ItemMusicLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return MusicItemViewHolder(binding, listener)
            }
            else -> {
                val binding: ItemBottomSpaceBinding =
                    ItemBottomSpaceBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return BottomspaceViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.getItemViewType()) {
            ITEM_TYPE_SOURCE_ITEM_LAYOUT -> {
                var mHolder = holder as MusicItemViewHolder
                mHolder.bind(items[position] as ContentEntity)
            }

            ITEM_TYPE_BOTTOMSPACE_LAYOUT -> {
                //do nothing
            }

        }
    }

}