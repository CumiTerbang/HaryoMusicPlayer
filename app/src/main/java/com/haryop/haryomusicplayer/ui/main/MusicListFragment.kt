package com.haryop.haryomusicplayer.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.haryop.haryomusicplayer.R
import com.haryop.haryomusicplayer.data.entities.ContentEntity
import com.haryop.haryomusicplayer.databinding.FragmentMainBinding
import com.haryop.haryomusicplayer.ui.PlayerFragment
import com.haryop.mynewsportal.utils.Resource
import com.haryop.synpulsefrontendchallenge.ui.companylist.MusicListAdapter
import com.haryop.synpulsefrontendchallenge.utils.BaseFragmentBinding
import com.haryop.synpulsefrontendchallenge.utils.isNetworkAvailable
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
        stopPlayer()

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

            welcome.visibility = View.GONE

            when (it.status) {
                Resource.Status.SUCCESS -> {
                    foundLabel.visibility = View.VISIBLE
                    swipeContainer.isRefreshing = false
                    if (!it.data.isNullOrEmpty()) {
                        foundLabel.setText(
                            resources.getString(
                                R.string.found_label,
                                "" + it.data.size,
                                query
                            )
                        )

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

    override fun onClickedItem(
        preview_url: String,
        artist_name: String,
        track_name: String,
        isStreamable: Boolean,
        isPlaying: Boolean,
        position: Int
    ) {
        var items = adapter.getItems()
        for (item in items) {
            if (item is ContentEntity) {
                item.isPlaying = false
            }
        }

        (items[position] as ContentEntity).isPlaying = isPlaying

        adapter.notifyDataSetChanged()

        stopPlayer()
        if (isPlaying) {
            showPlayer(preview_url, artist_name, track_name)
        }

    }

    override fun onRefresh() {
        onReSearch(query)
    }

    fun showPlayer(media_url: String, artist_name: String, track_name: String) = with(viewbinding) {
        val bundle = bundleOf(
            "media_url" to media_url,
            "artist_name" to artist_name,
            "track_name" to track_name
        )
        for (fragment in childFragmentManager.getFragments()) {
            childFragmentManager.beginTransaction().remove(fragment).commit()
        }
        playerContainer.removeAllViews()

        childFragmentManager.commit {
            setReorderingAllowed(true)
            add<PlayerFragment>(R.id.playerContainer, args = bundle)
        }
        playerContainer.visibility = View.VISIBLE
    }

    fun stopPlayer() = with(viewbinding) {
        for (fragment in childFragmentManager.getFragments()) {
            childFragmentManager.beginTransaction().remove(fragment).commit()
        }
        playerContainer.removeAllViews()
        playerContainer.visibility = View.GONE

    }

    override fun onDestroy() {
        super.onDestroy()
        stopPlayer()
    }

    override fun onResume() {
        super.onResume()
        checkInternet(viewbinding.root)
    }

    fun checkInternet(view: View)=with(viewbinding) {
        if (requireContext().isNetworkAvailable() == false) {
            onInternetUnavailable(view)
            welcome.text = "No Internet\nPlease connect to your internet connection"
        }else{
            welcome.text = resources.getText(R.string.welcome)
        }
    }

    fun onInternetUnavailable(view: View) {

        //Instantiate builder variable
        val builder = AlertDialog.Builder(view.context)

        // set title
        builder.setTitle("No Internet")

        //set content area
        builder.setMessage("Please connect to your internet connection")

        builder.setPositiveButton("OK") { dialog, id ->

        }

        builder.show()
    }
}