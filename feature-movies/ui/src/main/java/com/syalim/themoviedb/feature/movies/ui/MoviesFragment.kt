package com.syalim.themoviedb.feature.movies.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.syalim.themoviedb.core.ui.CustomCarousel
import com.syalim.themoviedb.core.ui.SharedUiUpdater
import com.syalim.themoviedb.core.ui.getAxisXTransition
import com.syalim.themoviedb.core.ui.utils.*
import com.syalim.themoviedb.feature.movies.ui.adapter.MoviesAdapter
import com.syalim.themoviedb.feature.movies.ui.databinding.FragmentMoviesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */

@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.fragment_movies) {

   private val binding: FragmentMoviesBinding by viewBinding()

   private val viewModel: MoviesViewModel by viewModels()

   @Inject
   lateinit var moviesAdapter: MoviesAdapter

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setupTransition()
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      initUi()
   }

   private fun setupTransition() {
      enterTransition = requireContext().getAxisXTransition(false)
      exitTransition = requireContext().getAxisXTransition(true)
      reenterTransition = requireContext().getAxisXTransition(false)
      returnTransition = requireContext().getAxisXTransition(true)
   }

   private fun MoviesUiState.updateState() {
      if (isDefault) {
         binding.rvMovies.isGone()
         binding.shimmerMovies.start()
      }
      onFinish(
         success = {
            moviesAdapter.submitData(this)
            binding.shimmerMovies.delayedStop(viewLifecycleOwner.lifecycleScope) {
               binding.rvMovies.isVisible()
               binding.swipeRefreshLayout.stop()
            }
         },
         failure = {
            binding.swipeRefreshLayout.stop()
            binding.root.showSnackBar(getThrownMessage(requireContext()))
            viewModel.onErrorShown()
         }
      )
   }

   private fun initUi() {
      binding.swipeRefreshLayout.setOnRefreshListener {
         viewModel.onRefresh()
      }
      viewLifecycleOwner.lifecycleScope.launch {
         viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.moviesUiState.collect { it.updateState() }
         }
      }
      setupMovies()
   }

   private fun setupMovies() {
      binding.rvMovies.apply {
         adapter = moviesAdapter.apply {
            setupCarousel = {
               CustomCarousel.setup(
                  this,
                  viewLifecycleOwner.lifecycleScope,
                  viewModel.carouselPosition,
                  onSavePosition = {
                     viewModel.carouselPosition = this
                  }
               )
            }
         }
         setVerticalScrollListener { offset, _ ->
            SharedUiUpdater.onHomeToolbarColorChange?.invoke(offset == 0)
         }
      }
   }

}