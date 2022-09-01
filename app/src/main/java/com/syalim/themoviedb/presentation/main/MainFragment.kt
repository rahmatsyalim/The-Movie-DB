package com.syalim.themoviedb.presentation.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.syalim.themoviedb.R
import com.syalim.themoviedb.databinding.FragmentMainBinding
import com.syalim.themoviedb.presentation.main.discover.DiscoverFilterFragment
import com.syalim.themoviedb.presentation.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * Created by Rahmat Syalim on 2022/08/23
 * rahmatsyalim@gmail.com
 */

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

   private val binding by viewBinding(FragmentMainBinding::bind)

   private val navController by lazy {
      (childFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment).findNavController()
   }

   private val discoverFilterBottomSheet by lazy { DiscoverFilterFragment() }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      setupAppBar()

   }

   private fun setupAppBar() {
      binding.bottomNavigation.setupWithNavController(navController)
      val topLevelDest = setOf(
         R.id.home_fragment,
         R.id.discover_fragment,
         R.id.bookmarks_fragment
      )

      navController.addOnDestinationChangedListener { _, destination, _ ->
         topLevelDest.apply {
            binding.topAppbar.setExpanded(contains(destination.id))
            binding.bottomAppbar.hideOnScroll = destination.id == R.id.discover_fragment
            binding.fabFilter.apply {
               if (destination.id == R.id.discover_fragment) show() else hide()
            }
         }
      }
      binding.fabFilter.setOnClickListener {
         discoverFilterBottomSheet.show(childFragmentManager, discoverFilterBottomSheet.javaClass.simpleName)
      }
   }
}