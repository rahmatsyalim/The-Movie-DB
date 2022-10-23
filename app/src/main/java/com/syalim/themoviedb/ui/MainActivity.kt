package com.syalim.themoviedb.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.syalim.themoviedb.R
import com.syalim.themoviedb.core.navigation.*
import com.syalim.themoviedb.core.ui.SharedUiUpdater
import com.syalim.themoviedb.core.ui.getAxisXTransition
import com.syalim.themoviedb.core.ui.utils.isGone
import com.syalim.themoviedb.core.ui.utils.isVisible
import com.syalim.themoviedb.core.ui.utils.viewBinding
import com.syalim.themoviedb.databinding.ActivityMovieBinding
import com.syalim.themoviedb.navigation.setupWithAppNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

   private val binding: ActivityMovieBinding by viewBinding()
   private val viewModel: MainViewModel by viewModels()

   private val navController by lazy {
      (supportFragmentManager.findFragmentById(R.id.app_nav_host) as NavHostFragment).findNavController()
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      observeConnectivityState()
      setupNavigation()
   }

   private fun setupNavigation() {
      navController.run {
         setupWithAppNavigator(supportFragmentManager)
         addOnDestinationChangedListener(this@MainActivity)
         binding.bottomNavigation.customSetupWithNavController(this) {
            when (currentDestination?.id) {
               R.id.home -> {
                  supportFragmentManager.getCurrentFragment(R.id.app_nav_host)?.apply {
                     exitTransition = requireContext().getAxisXTransition(true)
                     reenterTransition = requireContext().getAxisXTransition(false)
                  }
               }
            }
         }
      }
      SharedUiUpdater.onFabDiscoverIconChange = {
         binding.fabDiscover.apply {
            when (it) {
               0  -> {
                  setImageState(intArrayOf(android.R.attr.state_activated), true)
                  setOnClickListener {
                     AppNavigator.navigateTo(
                        NavParam(
                           destination = AppDestination.DISCOVER_MOVIES,
                           args = buildArgs(NavArgsKey.FAB_DISCOVER_ID to R.id.fab_discover)
                        )
                     )
                  }
               }
               1 -> {
                  setImageState(intArrayOf(-android.R.attr.state_activated), true)
                  setOnClickListener {
                     AppNavigator.navigateTo(
                        NavParam(
                           destination = AppDestination.DISCOVER_TV_SHOWS,
                           args = buildArgs(NavArgsKey.FAB_DISCOVER_ID to R.id.fab_discover)
                        )
                     )
                  }
               }
            }
         }
      }
   }

   override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
      when (destination.id) {
         in setOf(R.id.home, R.id.bookmarks) -> {
            binding.run {
               bottomAppbar.performShow()
               fabDiscover.show()
            }
         }
         else -> {
            binding.run {
               bottomAppbar.performHide()
               fabDiscover.hide()
            }
         }
      }
   }

   private fun observeConnectivityState() {
      lifecycleScope.launch {
         viewModel.connectivityUiState.collectLatest { status ->
            status.message?.let {
               binding.tvNetworkState.apply {
                  text = getString(it)
                  isVisible()
               }
            } ?: binding.tvNetworkState.isGone()
         }
      }
   }
}