package com.syalim.themoviedb.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import com.syalim.themoviedb.R
import com.syalim.themoviedb.core.navigation.*
import com.syalim.themoviedb.core.ui.SharedUiUpdater
import com.syalim.themoviedb.core.ui.utils.getColorFrom
import com.syalim.themoviedb.core.ui.utils.setCustomCLickListener
import com.syalim.themoviedb.core.ui.utils.viewBinding
import com.syalim.themoviedb.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * Created by Rahmat Syalim on 2022/09/11
 * rahmatsyalim@gmail.com
 */

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), NavController.OnDestinationChangedListener {

   private val binding: FragmentHomeBinding by viewBinding()

   private val navHostFragment by lazy {
      childFragmentManager.findFragmentById(R.id.home_container) as NavHostFragment
   }
   private val navController by lazy { navHostFragment.navController }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      setupAppBar()
   }


   private fun setupAppBar() {
      binding.btnGroupNavigation.customSetupWithNavController(navController)
      navController.addOnDestinationChangedListener(this)
      binding.btnSearch.setCustomCLickListener {
         val args = buildArgs(sendElementTransitionName(transitionName))
         val navExtras = FragmentNavigatorExtras(sendSharedElement(this))
         when (navController.currentDestination?.id) {
            R.id.movies -> AppNavigator.navigateTo(
               NavParam(
                  destination = AppDestination.MOVIE_SEARCH,
                  args = args,
                  navExtras = navExtras
               )
            )
            R.id.tvShows -> AppNavigator.navigateTo(
               NavParam(
                  destination = AppDestination.TV_SHOW_SEARCH,
                  args = args,
                  navExtras = navExtras
               )
            )
         }
      }
      SharedUiUpdater.onHomeToolbarColorChange = { transparent ->
         binding.toolbar.setBackgroundColor(
            if (transparent) {
               Color.TRANSPARENT
            } else {
               requireContext().getColorFrom(com.syalim.themoviedb.core.ui.R.color.theme_surface)
            }
         )
      }
   }

   override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
      when (destination.id) {
         R.id.movies -> SharedUiUpdater.onFabDiscoverIconChange?.invoke(0)
         R.id.tvShows -> SharedUiUpdater.onFabDiscoverIconChange?.invoke(1)
      }
   }

}