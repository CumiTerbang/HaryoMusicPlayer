package com.haryop.haryomusicplayer.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.haryop.haryomusicplayer.R
import com.haryop.haryomusicplayer.data.entities.ContentEntity
import com.haryop.haryomusicplayer.databinding.FragmentMainBinding
import com.haryop.synpulsefrontendchallenge.ui.companylist.MusicListAdapter
import com.haryop.synpulsefrontendchallenge.utils.BaseFragmentBinding
import com.haryop.synpulsefrontendchallenge.utils.comingSoon

class MainFragment : BaseFragmentBinding<FragmentMainBinding>(),
    MusicListAdapter.MusicItemListener, SwipeRefreshLayout.OnRefreshListener {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainBinding
        get() = FragmentMainBinding::inflate

    lateinit var viewbinding: FragmentMainBinding
    override fun setupView(binding: FragmentMainBinding) {
        viewbinding = binding
        setUpRecyclerView()
    }

    var query = ""
    fun onReSearch(_query: String) = with(viewbinding) {
        query = _query

        adapter.getItems().clear()
        adapter.notifyDataSetChanged()
        onSearchContentObserver()
    }

    lateinit var adapter: MusicListAdapter
    fun setUpRecyclerView() = with(viewbinding) {
        adapter = MusicListAdapter(this@MainFragment)
        var layManager = LinearLayoutManager(requireContext())
        newslistRecyclerView.layoutManager = layManager
        newslistRecyclerView.adapter = adapter

        swipeContainer.setOnRefreshListener(this@MainFragment)
    }

    private fun onSearchContentObserver() = with(viewbinding) {
        var items = ArrayList<Any>()
        var item = ContentEntity(
            artistName = "John Mayer",
            trackName = "New Light",
            collectionName = "unknown"
        )
        for (i in 1..20) {
            items.add(item)
        }

        adapter.setItems(items)

        foundLabel.visibility = View.VISIBLE
        foundLabel.setText(resources.getString(R.string.found_label, "100", query))

        swipeContainer.isRefreshing = false


    }

    override fun onClickedItem(preview_url: String, isStreamable: Boolean) {
        comingSoon("\nPlay Track")
    }

    override fun onRefresh() {
        onReSearch(query)
    }

}