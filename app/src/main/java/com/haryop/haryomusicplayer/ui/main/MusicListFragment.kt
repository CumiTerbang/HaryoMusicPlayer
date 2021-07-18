package com.haryop.haryomusicplayer.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.haryop.haryomusicplayer.R
import com.haryop.haryomusicplayer.data.entities.ContentEntity
import com.haryop.haryomusicplayer.databinding.FragmentMainBinding
import com.haryop.mynewsportal.utils.Resource
import com.haryop.synpulsefrontendchallenge.ui.companylist.MusicListAdapter
import com.haryop.synpulsefrontendchallenge.utils.BaseFragmentBinding
import com.haryop.synpulsefrontendchallenge.utils.comingSoon
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MusicListFragment : BaseFragmentBinding<FragmentMainBinding>(),
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
        viewModel.start(_query)
        onSearchContentObserver()
    }

    lateinit var adapter: MusicListAdapter
    fun setUpRecyclerView() = with(viewbinding) {
        adapter = MusicListAdapter(this@MusicListFragment)
        var layManager = LinearLayoutManager(requireContext())
        newslistRecyclerView.layoutManager = layManager
        newslistRecyclerView.adapter = adapter

        swipeContainer.setOnRefreshListener(this@MusicListFragment)
    }

    private val viewModel: MusicListViewModel by viewModels()
    private fun onSearchContentObserver() = with(viewbinding) {
        viewModel.getSearchContent.observe(viewLifecycleOwner, {

            when (it.status) {
                Resource.Status.SUCCESS -> {
                    foundLabel.visibility = View.VISIBLE
                    swipeContainer.isRefreshing = false
                    if (!it.data.isNullOrEmpty()) {
                        foundLabel.setText(resources.getString(R.string.found_label, ""+it.data.size, query))

                        var items = ArrayList<Any>()
                        items.addAll(ArrayList(it.data))
                        items.add(adapter.ITEM_TYPE_BOTTOMSPACE_LAYOUT)
                        adapter.setItems(items)
                        adapter.notifyDataSetChanged()
                    } else {
                        foundLabel.setText(resources.getString(R.string.found_label, "0", query))
                        Timber.e("getSearchEndpoint.observe: SUCCESS tapi null")
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
                Resource.Status.ERROR -> {
                    foundLabel.visibility = View.GONE
                    swipeContainer.isRefreshing = false
                    Timber.e("getSearchEndpoint.observe: error")
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.LOADING -> {
                    foundLabel.visibility = View.GONE
                    Timber.e("getSearchEndpoint.observe: LOADING")
                    swipeContainer.isRefreshing = true
                }
            }
        })

    }

    override fun onClickedItem(preview_url: String, isStreamable: Boolean, isPlaying: Boolean) {
        if(isStreamable==false) {
            comingSoon("\nPreview Not Availalble")
        }else if(isPlaying){
            comingSoon("\nPlay Track")
        }else{
            comingSoon("\nStop Track")
        }

        adapter.notifyDataSetChanged()
    }

    override fun onRefresh() {
        onReSearch(query)
    }

}