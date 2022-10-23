package com.syalim.themoviedb.feature.tvshows.ui.discover

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.syalim.themoviedb.core.navigation.NavArgsKey
import com.syalim.themoviedb.core.navigation.getIntOrNull
import com.syalim.themoviedb.core.ui.CustomGridLayoutManager
import com.syalim.themoviedb.core.ui.getSlideTransition
import com.syalim.themoviedb.core.ui.getTransformTransition
import com.syalim.themoviedb.core.ui.utils.*
import com.syalim.themoviedb.feature.tvshows.ui.R
import com.syalim.themoviedb.feature.tvshows.ui.adapter.TvShowLoadStateAdapter
import com.syalim.themoviedb.feature.tvshows.ui.adapter.TvShowPagerAdapter
import com.syalim.themoviedb.feature.tvshows.ui.databinding.FragmentDiscoverTvShowsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/09/06
 * rahmatsyalim@gmail.com
 */

@AndroidEntryPoint
class DiscoverTvShowsFragment : Fragment(R.layout.fragment_discover_tv_shows) {

   private val binding: FragmentDiscoverTvShowsBinding by viewBinding()

   private val viewModel: DiscoverTvShowsViewModel by viewModels(
      ownerProducer = { requireActivity() }
   )

   @Inject
   lateinit var tvShowsAdapter: TvShowPagerAdapter

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      setupTransition()
      initUi()
   }

   private fun setupTransition() {
      arguments.getIntOrNull(NavArgsKey.FAB_DISCOVER_ID)?.let {
         enterTransition = requireContext().getTransformTransition(
            requireActivity().findViewById(it),
            binding.root
         )
         returnTransition = requireContext().getSlideTransition(Gravity.BOTTOM)
      }
   }

   private fun initUi() {
      setupAppBar()
      setupRecyclerView()
      viewLifecycleOwner.lifecycleScope.launch {
         viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.discoverTvShowsState.collect {
               tvShowsAdapter.submitData(it)
            }
         }
      }
      binding.swipeRefreshLayout.setOnRefreshListener {
         tvShowsAdapter.refresh()
      }
   }

   private fun setupAppBar() {
      binding.includeToolbar.toolbar.apply {
         title = getString(R.string.discover_tv_shows)
         setNavigationOnClickListener { findNavController().navigateUp() }
      }
      binding.includeFilterButton.fabFilter.setOnClickListener {
         requireContext().showShortToast("Show Filter")
      }
   }

   private fun setupRecyclerView() {
      binding.rvTvShows.apply {
         layoutManager = CustomGridLayoutManager(requireContext(), 3, tvShowsAdapter).get
         adapter = tvShowsAdapter.apply {
            setupLoadStateListener()
         }.withLoadStateFooter(TvShowLoadStateAdapter(tvShowsAdapter::retry))
      }
   }

   private fun TvShowPagerAdapter.setupLoadStateListener() {
      setCustomLoadStateListener(
         onRefresh = {
            binding.tvInfo.isGone()
            if (!binding.swipeRefreshLayout.isRefreshing) {
               binding.rvTvShows.isGone()
               binding.shimmerDiscover.start()
            }
         },
         onRefreshSuccess = {
            binding.shimmerDiscover.delayedStop(viewLifecycleOwner.lifecycleScope) {
               binding.rvTvShows.isVisible()
               binding.swipeRefreshLayout.stop()
            }
         },
         onRefreshEmpty = {
            binding.shimmerDiscover.delayedStop(viewLifecycleOwner.lifecycleScope) {
               binding.swipeRefreshLayout.stop()
               binding.tvInfo.apply {
                  text = getString(R.string.no_tv_shows_discovered)
                  isVisible()
               }
            }
         },
         onRefreshError = {
            binding.swipeRefreshLayout.stop()
            if (itemCount > 0) {
               binding.shimmerDiscover.delayedStop(viewLifecycleOwner.lifecycleScope) {
                  binding.rvTvShows.isVisible()
               }
            } else {
               binding.rvTvShows.isGone()
               binding.shimmerDiscover.start()
            }
            binding.root.showSnackBar(getThrownMessage(requireContext()))
         }
      )
   }
}