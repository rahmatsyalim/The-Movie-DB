package com.syalim.themoviedb.presentation.home

import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.syalim.themoviedb.common.showSnackBar
import com.syalim.themoviedb.databinding.FragmentHomeBinding
import com.syalim.themoviedb.presentation.MainViewModel
import com.syalim.themoviedb.presentation.adapter.HomeMoviesAdapter
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

      setRecyclerViewPopular()
      setRecyclerViewNowPlaying()
      setRecyclerViewTopRated()

   }

   private fun setRecyclerViewPopular() {
      val recyclerAdapter = HomeMoviesAdapter()
      binding.rvPopular.apply {
         layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
         adapter = recyclerAdapter
      }
      recyclerAdapter.collectPopularMovies()
   }

   private fun setRecyclerViewNowPlaying() {
      val recyclerAdapter = HomeMoviesAdapter()
      binding.rvInTheatre.apply {
         layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
         adapter = recyclerAdapter
      }
      recyclerAdapter.collectNowPlayingMovies()
   }

   private fun setRecyclerViewTopRated() {
      val recyclerAdapter = HomeMoviesAdapter()
      binding.rvTopRated.apply {
         layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
         adapter = recyclerAdapter
      }
      recyclerAdapter.collectTopRatedMovies()
   }

   private fun collectHomeState() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.homeState.collectLatest { state ->

            binding.swipeRefreshLayout.isRefreshing = state.isReloading
            progressBarLayout.isVisible = state.isLoading && !state.isReloading

            state.errorMessage?.let {
               binding.root.showSnackBar(it, false)
            }
         }
      }
   }

   private fun HomeMoviesAdapter.collectPopularMovies() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.popularState.collectLatest { state ->
            state.data?.let {
               if (it.isNotEmpty()){
                  this@collectPopularMovies.data.submitList(it)
               }
            }
         }
      }
   }

   private fun HomeMoviesAdapter.collectNowPlayingMovies() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.nowPlayingState.collectLatest { state ->
            state.data?.let {
               if (it.isNotEmpty()){
                  this@collectNowPlayingMovies.data.submitList(it)
               }
            }
         }
      }
   }

   private fun HomeMoviesAdapter.collectTopRatedMovies() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.topRatedState.collectLatest { state ->
            state.data?.let {
               if (it.isNotEmpty()){
                  this@collectTopRatedMovies.data.submitList(it)
               }
            }
         }
      }
   }


}