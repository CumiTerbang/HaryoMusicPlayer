package com.haryop.haryomusicplayer.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.haryop.haryomusicplayer.R
import com.haryop.haryomusicplayer.databinding.ActivityMainBinding
import com.haryop.haryomusicplayer.ui.AboutActivity
import com.haryop.haryomusicplayer.utils.BaseActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            blackscreen.visibility = View.GONE
            mainToolbar.menu.findItem(R.id.action_search)?.collapseActionView()

            if (destination.id == R.id.musicListFragment) {
                mainToolbar.subtitle = ""
                isLastFragment = true
            } else {
                isLastFragment = false
            }
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
            R.id.action_about -> {
                openAbout()
            }
            R.id.action_search -> {
                var searchView: SearchView = item.actionView as SearchView
                searchView.queryHint = getString(R.string.search_hint)
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

    fun openAbout() {
        var intent = Intent(this@MainActivity, AboutActivity::class.java)
        startActivity(intent)
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

    var isLastFragment: Boolean = false
    private var doubleBackToExitPressedOnce = false
    val activityScope = CoroutineScope(Dispatchers.Main)
    override fun onBackPressed() {
        if (binding.blackscreen.visibility == View.VISIBLE) {
            binding.blackscreen.visibility = View.GONE
            binding.mainToolbar.menu.findItem(R.id.action_search)?.collapseActionView()
        } else if (isLastFragment) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }

            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

            activityScope.launch {
                delay(2000)
                doubleBackToExitPressedOnce = false
            }
        } else {
            super.onBackPressed()
        }
    }

}