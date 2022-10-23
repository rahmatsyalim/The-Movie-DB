package com.syalim.themoviedb.feature.tvshows.ui.tvshow_by_category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.syalim.themoviedb.feature.tvshows.domain.utils.TvShowCategory
import com.syalim.themoviedb.core.navigation.NavArgsKey
import com.syalim.themoviedb.core.navigation.getEnum
import com.syalim.themoviedb.core.ui.CustomGridLayoutManager
import com.syalim.themoviedb.core.ui.getAxisXTransition
import com.syalim.themoviedb.core.ui.utils.*
import com.syalim.themoviedb.feature.tvshows.ui.R
import com.syalim.themoviedb.feature.tvshows.ui.adapter.TvShowLoadStateAdapter
import com.syalim.themoviedb.feature.tvshows.ui.adapter.TvShowPagerAdapter
import com.syalim.themoviedb.feature.tvshows.ui.databinding.FragmentTvShowsByCategoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/09/06
 * rahmatsyalim@gmail.com
 */

@AndroidEntryPoint
class TvShowsByCategoryFragment : Fragment(R.layout.fragment_tv_shows_by_category) {

   private val binding: FragmentTvShowsByCategoryBinding by viewBinding()

   private val viewModel: TvShowsByCategoryViewModel by viewModels()

   @Inject
   lateinit var tvShowsAdapter: TvShowPagerAdapter

   private val category by lazy {
      arguments?.getEnum<TvShowCategory>(NavArgsKey.TV_SHOWS_CATEGORY)
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setupTransition()
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      initUi()
   }

   private fun setupTransition() {
      enterTransition = requireContext().getAxisXTransition(true)
      returnTransition = requireContext().getAxisXTransition(false)
   }

   private fun initUi() {
      setupRecyclerView()
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.tvShowByCategoryState.collect { pagingData ->
            tvShowsAdapter.submitData(pagingData)
         }
      }
      binding.swipeRefreshLayout.setOnRefreshListener {
         tvShowsAdapter.refresh()
      }
      if (viewModel.isStateDefault()) category?.let { viewModel.fetchTvShowsByCategory(it) }
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
            if (!binding.swipeRefreshLayout.isRefreshing) {
               binding.rvTvShows.isGone()
               binding.shimmerTvShowsByCategory.start()
            }
         },
         onRefreshSuccess = {
            binding.shimmerTvShowsByCategory.delayedStop(viewLifecycleOwner.lifecycleScope) {
               binding.rvTvShows.isVisible()
               binding.swipeRefreshLayout.stop()
            }
         },
         onRefreshEmpty = {
            // TODO: empty state
         },
         onRefreshError = {
            binding.swipeRefreshLayout.stop()
            if (itemCount > 0) {
               binding.shimmerTvShowsByCategory.delayedStop(viewLifecycleOwner.lifecycleScope) {
                  binding.rvTvShows.isVisible()
               }
            } else {
               binding.rvTvShows.isGone()
               binding.shimmerTvShowsByCategory.start()
            }
            binding.root.showSnackBar(getThrownMessage(requireContext()))
         }
      )
   }
}