package com.syalim.themoviedb.feature.movies.ui.discover

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
import com.syalim.themoviedb.feature.movies.ui.R
import com.syalim.themoviedb.feature.movies.ui.adapter.MovieLoadStateAdapter
import com.syalim.themoviedb.feature.movies.ui.adapter.MoviePagerAdapter
import com.syalim.themoviedb.feature.movies.ui.databinding.FragmentDiscoverMoviesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/17
 * rahmatsyalim@gmail.com
 */

@AndroidEntryPoint
class DiscoverMoviesFragment : Fragment(R.layout.fragment_discover_movies) {

   private val binding: FragmentDiscoverMoviesBinding by viewBinding()

   private val viewModel by viewModels<DiscoverMoviesViewModel>(
      ownerProducer = { requireActivity() }
   )

   @Inject
   lateinit var moviesAdapter: MoviePagerAdapter

   private val discoverMoviesFilterBottomSheet by lazy { DiscoverMoviesFilterFragment(viewModel) }

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
            viewModel.discoverMoviesState.collect { pagingData ->
               moviesAdapter.submitData(pagingData)
            }
         }
      }
      binding.swipeRefreshLayout.setOnRefreshListener {
         moviesAdapter.refresh()
      }
   }

   private fun setupAppBar() {
      binding.toolbar.apply {
         title = getString(R.string.discover_movies)
         setNavigationOnClickListener { findNavController().navigateUp() }
      }
      binding.includeFilterButton.fabFilter.setOnClickListener {
         discoverMoviesFilterBottomSheet.show(
            parentFragmentManager,
            discoverMoviesFilterBottomSheet.javaClass.simpleName
         )
      }
   }

   private fun setupRecyclerView() {
      binding.rvMovies.apply {
         layoutManager = CustomGridLayoutManager(requireContext(), 3, moviesAdapter).get
         adapter = moviesAdapter.apply {
            setupLoadStateListener()
         }.withLoadStateFooter(MovieLoadStateAdapter(moviesAdapter::retry))

         setVerticalScrollListener { offset, isScrollingDown ->
            binding.includeFilterButton.fabFilter.apply {
               if (isScrollingDown && offset != 0) {
                  hide()
               } else {
                  show()
               }
            }
         }
      }
   }

   private fun MoviePagerAdapter.setupLoadStateListener() {
      setCustomLoadStateListener(
         onRefresh = {
            binding.tvInfo.isGone()
            if (!binding.swipeRefreshLayout.isRefreshing) {
               binding.rvMovies.isGone()
               binding.shimmerDiscover.start()
            }
         },
         onRefreshSuccess = {
            binding.shimmerDiscover.delayedStop(viewLifecycleOwner.lifecycleScope) {
               binding.rvMovies.isVisible()
               binding.swipeRefreshLayout.stop()
            }
         },
         onRefreshEmpty = {
            binding.shimmerDiscover.delayedStop(viewLifecycleOwner.lifecycleScope) {
               binding.swipeRefreshLayout.stop()
               binding.tvInfo.apply {
                  text = getString(R.string.no_movies_discovered)
                  isVisible()
               }
            }
         },
         onRefreshError = {
            binding.swipeRefreshLayout.stop()
            if (itemCount > 0) {
               binding.shimmerDiscover.delayedStop(viewLifecycleOwner.lifecycleScope) {
                  binding.rvMovies.isVisible()
               }
            } else {
               binding.rvMovies.isGone()
               binding.shimmerDiscover.start()
            }
            binding.root.showSnackBar(getThrownMessage(requireContext()))
         }
      )
   }

}