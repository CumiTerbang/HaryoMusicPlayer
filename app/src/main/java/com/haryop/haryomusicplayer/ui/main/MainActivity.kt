package com.haryop.haryomusicplayer.ui.main

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.haryop.haryomusicplayer.R
import com.haryop.haryomusicplayer.databinding.ActivityMainBinding
import com.haryop.synpulsefrontendchallenge.utils.BaseActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivityBinding<ActivityMainBinding>(), Toolbar.OnMenuItemClickListener {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setupView(binding: ActivityMainBinding) {
        setUpFragments()
    }

    fun setUpFragments() = with(binding) {
        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_category_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController

        navController.setGraph(R.navigation.main_nav_graph)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
        }

        val appBarConfiguration: AppBarConfiguration = AppBarConfiguration(navController.graph)
        mainToolbar.setupWithNavController(navController, appBarConfiguration)

        mainToolbar.inflateMenu(R.menu.menu_main_activity)
        mainToolbar.setOnMenuItemClickListener(this@MainActivity)
        mainToolbar.setNavigationOnClickListener {
            blackscreen.visibility = View.GONE
            mainToolbar.menu.findItem(R.id.action_search)?.collapseActionView()
            navController.popBackStack()
        }

        mainToolbar.menu.findItem(R.id.action_search)
            ?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
                override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                    blackscreen.visibility = View.VISIBLE
                    return true
                }

                override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                    blackscreen.visibility = View.GONE
                    return true
                }

            })

    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.getItemId()) {
            R.id.action_search -> {
                var searchView: SearchView = item.actionView as SearchView
                searchView.queryHint = "search articles..."
                searchView.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        query?.let { reSearchPage(it) }

                        if (!searchView.isIconified()) {
                            searchView.setIconified(true)
                        }
                        item.collapseActionView()
                        return false;
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }

                })
            }
        }
        return false
    }

    fun reSearchPage(_query: String) = with(binding) {
        var mainFragment: MusicListFragment = getForegroundFragment() as MusicListFragment
        mainFragment.onReSearch(_query)
    }

    fun getForegroundFragment(): Fragment? {
        val navHostFragment: Fragment? =
            supportFragmentManager.findFragmentById(R.id.nav_host_category_fragment)
        return if (navHostFragment == null) null else navHostFragment.getChildFragmentManager()
            .getFragments().get(0)
    }

    override fun onBackPressed() {
        if (binding.blackscreen.visibility == View.VISIBLE) {
            binding.blackscreen.visibility = View.GONE
            binding.mainToolbar.menu.findItem(R.id.action_search)?.collapseActionView()
        }else{
            super.onBackPressed()
        }
    }

}