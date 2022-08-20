package com.syalim.themoviedb.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.syalim.themoviedb.R
import com.syalim.themoviedb.databinding.ActivityMainBinding
import com.syalim.themoviedb.presentation.common.utils.getConnectivityStateMessage
import com.syalim.themoviedb.presentation.common.utils.hideView
import com.syalim.themoviedb.presentation.common.utils.showView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

   val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

   private val viewModel by viewModels<MainViewModel>()

   private val navController by lazy {
      (supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment)
         .findNavController()
   }

   val menuTune: MenuItem? by lazy { binding.toolbar.menu.findItem(R.id.menu_tune) }
   val menuSearch: MenuItem? by lazy { binding.toolbar.menu.findItem(R.id.menu_search) }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(binding.root)

      setAppBar()

      observeConnectivityState()

   }

   private fun setAppBar() {
      binding.bottomNavigation.setupWithNavController(navController)

      val topLevelDest = setOf(
         R.id.home_fragment,
         R.id.discover_fragment,
         R.id.bookmarks_fragment
      )

      navController.addOnDestinationChangedListener { _, destination, _ ->

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

   private fun observeConnectivityState() {
      lifecycleScope.launch {
         viewModel.connectivityState.collectLatest {
            applicationContext.getConnectivityStateMessage(it)?.let { message ->
               binding.tvNetworkState.apply {
                  text = message
                  showView()
               }
            } ?: binding.tvNetworkState.hideView()
         }
      }
   }

}