package com.syalim.themoviedb.presentation.home

import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.syalim.themoviedb.common.showSnackBar
import com.syalim.themoviedb.databinding.FragmentHomeBinding
import com.syalim.themoviedb.presentation.MainViewModel
import com.syalim.themoviedb.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

   private val viewModel: MainViewModel by activityViewModels()

   private val progressBarLayout by lazy { binding.includedProgressBar.progressBarLayout }

   override fun init() {

      collectHomeState()

      binding.swipeRefreshLayout.setOnRefreshListener {
         viewModel.loadMovies(true)
      }

   }

   private fun collectHomeState() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.homeState.collectLatest { state ->

            binding.swipeRefreshLayout.isRefreshing = state.isReloading
            progressBarLayout.isVisible = state.isLoading && !state.isReloading

            state.errorMessage?.let {
               binding.root.showSnackBar(it,false)
            }
         }
      }
   }

}