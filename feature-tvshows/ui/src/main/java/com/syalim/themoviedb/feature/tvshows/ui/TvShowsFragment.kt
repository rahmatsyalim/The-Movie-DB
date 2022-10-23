package com.syalim.themoviedb.feature.tvshows.ui

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
import com.syalim.themoviedb.feature.tvshows.ui.adapter.TvShowsAdapter
import com.syalim.themoviedb.feature.tvshows.ui.databinding.FragmentTvShowsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/09/05
 * rahmatsyalim@gmail.com
 */

@AndroidEntryPoint
class TvShowsFragment : Fragment(R.layout.fragment_tv_shows) {

   private val binding: FragmentTvShowsBinding by viewBinding()

   private val viewModel: TvShowsViewModel by viewModels()

   @Inject
   lateinit var tvShowsAdapter: TvShowsAdapter

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setupTransition()
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      iniUi()
   }

   private fun setupTransition() {
      enterTransition = requireContext().getAxisXTransition(true)
      exitTransition = requireContext().getAxisXTransition(false)
      reenterTransition = requireContext().getAxisXTransition(true)
      returnTransition = requireContext().getAxisXTransition(false)
   }

   private fun TvShowsUiState.updateState() {
      if (isDefault) {
         binding.rvTvShows.isGone()
         binding.shimmerTvShows.start()
      }
      onFinish(
         success = {
            tvShowsAdapter.submitData(this)
            binding.shimmerTvShows.delayedStop(viewLifecycleOwner.lifecycleScope) {
               binding.rvTvShows.isVisible()
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

   private fun iniUi() {
      setupTvShows()
      binding.swipeRefreshLayout.setOnRefreshListener {
         viewModel.onRefresh()
      }
      viewLifecycleOwner.lifecycleScope.launch {
         viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.tvShowsUiState.collect { it.updateState() }
         }
      }
   }

   private fun setupTvShows() {
      binding.rvTvShows.apply {
         adapter = tvShowsAdapter.apply {
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