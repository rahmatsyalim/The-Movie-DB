package com.syalim.themoviedb.presentation.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.syalim.themoviedb.R
import com.syalim.themoviedb.databinding.FragmentHomeBinding
import com.syalim.themoviedb.presentation.utils.ext.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

   private val binding by viewBinding(FragmentHomeBinding::bind)

   private val viewModel: HomeViewModel by hiltNavGraphViewModels(R.id.nav_movie_main)

   @Inject
   lateinit var homeAdapter: HomeAdapter

   private val navController by lazy { parentFragment?.parentFragment?.findNavController() }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      initUi()

   }

   private fun HomeUiState.updateState() {
      onFinish(
         success = {
            homeAdapter.submitData(this)
            binding.shimmerHome.delayedStop(viewLifecycleOwner.lifecycleScope) {
               binding.rvHome.isVisible()
               binding.swipeRefreshLayout.stop()
            }
         },
         failure = {
            binding.swipeRefreshLayout.stop()
            binding.root.showSnackBar(getThrownMessage(requireContext()))
            viewModel.onHomeErrorShown()
         }
      )
   }

   private fun initUi() {
      setupHome()
      binding.swipeRefreshLayout.setOnRefreshListener {
         viewModel.onRefresh()
      }
      viewLifecycleOwner.lifecycleScope.launch {
         viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.homeUiState.collect { it.updateState() }
         }
      }
   }

   private fun setupHome() {
      binding.rvHome.adapter = homeAdapter.apply {
         onItemNavigation = { id, arg -> navController?.navigate(id, arg) }
         onViewMoreClicked = { id, arg -> navController?.navigate(id, arg) }
         carouselPosition = {
            setCurrentItem(viewModel.carouselPosition, false)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
               override fun onPageSelected(position: Int) {
                  viewModel.carouselPosition = position
                  super.onPageSelected(position)
               }
            })
         }
         coroutineScope = viewLifecycleOwner.lifecycleScope
      }
   }

   override fun onDestroy() {
      homeAdapter.clearState()
      super.onDestroy()
   }

}