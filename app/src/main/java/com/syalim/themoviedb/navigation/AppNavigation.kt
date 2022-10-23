package com.syalim.themoviedb.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import com.syalim.themoviedb.R
import com.syalim.themoviedb.core.navigation.AppDestination
import com.syalim.themoviedb.core.navigation.AppNavigator
import com.syalim.themoviedb.core.navigation.getCurrentFragment
import com.syalim.themoviedb.core.navigation.proceedNavigate
import com.syalim.themoviedb.core.ui.getAxisXTransition
import com.syalim.themoviedb.core.ui.getElevationScaleTransition


/**
 * Created by Rahmat Syalim on 2022/09/15
 * rahmatsyalim@gmail.com
 */

fun NavController.setupWithAppNavigator(fragmentManager: FragmentManager) {
   val navHostId = R.id.app_nav_host
   AppNavigator.navHostId = navHostId
   AppNavigator.onNavigation {
      val destId = destination.getDestinationId(fragmentManager.getCurrentFragment(navHostId))
      proceedNavigate(destId, args, navOptions, navExtras)
   }
}

@IdRes
private fun AppDestination.getDestinationId(currentFragment: Fragment?): Int {
   return when (this) {
      AppDestination.MOVIE_DETAIL -> {
         currentFragment.setRegularTransition()
         R.id.movie_detail
      }
      AppDestination.MOVIE_SEARCH -> {
         currentFragment.setRegularTransition()
         R.id.movie_search
      }
      AppDestination.DISCOVER_MOVIES -> {
         currentFragment?.apply {
            exitTransition = requireContext().getElevationScaleTransition(false)
            reenterTransition = requireContext().getElevationScaleTransition(true)
         }
         R.id.discover_movies
      }
      AppDestination.MOVIES_BY_CATEGORY -> {
         currentFragment.setRegularTransition()
         R.id.movies_by_category
      }
      AppDestination.TV_SHOW_DETAIL -> {
         currentFragment.setRegularTransition()
         R.id.tv_show_detail
      }
      AppDestination.TV_SHOW_SEARCH -> {
         currentFragment.setRegularTransition()
         R.id.tv_show_search
      }
      AppDestination.DISCOVER_TV_SHOWS -> {
         currentFragment?.apply {
            exitTransition = requireContext().getElevationScaleTransition(false)
            reenterTransition = requireContext().getElevationScaleTransition(true)
         }
         R.id.discover_tv_shows
      }
      AppDestination.TV_SHOW_BY_CATEGORY -> {
         currentFragment.setRegularTransition()
         R.id.tv_shows_by_category
      }
   }
}

private fun Fragment?.setRegularTransition() {
   this?.apply {
      exitTransition = requireContext().getAxisXTransition(true)
      reenterTransition = requireContext().getAxisXTransition(false)
   }
}