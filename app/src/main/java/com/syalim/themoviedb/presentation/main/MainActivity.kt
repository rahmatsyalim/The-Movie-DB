package com.syalim.themoviedb.presentation.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.syalim.themoviedb.R
import com.syalim.themoviedb.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

   private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

   private val navController by lazy {
      (supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment)
         .findNavController()
   }

   val toolbar by lazy { binding.toolbar }

   val menuTune: MenuItem? by lazy { toolbar.menu.findItem(R.id.menu_tune) }
   val menuSearch: MenuItem? by lazy { toolbar.menu.findItem(R.id.menu_search) }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(binding.root)

      setAppBar()

   }

   private fun setAppBar() {
      binding.bottomNavigation.setupWithNavController(navController)

      val topLevelDest = setOf(
         R.id.home_fragment,
         R.id.discover_fragment,
         R.id.bookmarks_fragment
      )

      navController.addOnDestinationChangedListener { _, destination, _ ->

         toolbar.title = destination.label
         menuSearch?.isVisible = destination.id == R.id.home_fragment || destination.id == R.id.discover_fragment
         menuTune?.isVisible = destination.id == R.id.discover_fragment

         topLevelDest.apply {
            if (this.contains(destination.id)) {
               binding.topAppbar.setExpanded(true)
               binding.bottomAppBar.performShow()
            } else {
               binding.topAppbar.setExpanded(false)
               binding.bottomAppBar.performHide()
            }
         }
      }
   }

}