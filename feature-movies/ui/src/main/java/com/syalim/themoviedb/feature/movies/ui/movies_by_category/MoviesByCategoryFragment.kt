package com.syalim.themoviedb.feature.movies.ui.movies_by_category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.syalim.themoviedb.feature.movies.domain.utils.MovieCategory
import com.syalim.themoviedb.core.navigation.NavArgsKey
import com.syalim.themoviedb.core.navigation.getEnum
import com.syalim.themoviedb.core.ui.CustomGridLayoutManager
import com.syalim.themoviedb.core.ui.getAxisXTransition
import com.syalim.themoviedb.core.ui.utils.*
import com.syalim.themoviedb.feature.movies.ui.R
import com.syalim.themoviedb.feature.movies.ui.adapter.MovieLoadStateAdapter
import com.syalim.themoviedb.feature.movies.ui.adapter.MoviePagerAdapter
import com.syalim.themoviedb.feature.movies.ui.databinding.FragmentMoviesByCategoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/09/04
 * rahmatsyalim@gmail.com
 */

@AndroidEntryPoint
class MoviesByCategoryFragment : Fragment(R.layout.fragment_movies_by_category) {

   private val binding: FragmentMoviesByCategoryBinding by viewBinding()

   private val viewModel: MoviesByCategoryViewModel by viewModels()

   @Inject
   lateinit var moviesAdapter: MoviePagerAdapter

   private val category by lazy {
      arguments?.getEnum<MovieCategory>(NavArgsKey.MOVIES_CATEGORY)
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
         viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.moviesByCategoryState.collect { pagingData ->
               moviesAdapter.submitData(pagingData)
            }
         }
      }
      binding.swipeRefreshLayout.setOnRefreshListener {
         moviesAdapter.refresh()
      }
      if (viewModel.isStateDefault()) category?.let { viewModel.fetchMoviesByCategory(it) }
   }

   private fun setupRecyclerView() {
      binding.rvMovies.apply {
         layoutManager = CustomGridLayoutManager(requireContext(), 3, moviesAdapter).get
         adapter = moviesAdapter.apply {
            setupLoadStateListener()
         }.withLoadStateFooter(MovieLoadStateAdapter(moviesAdapter::retry))
      }
   }

   private fun MoviePagerAdapter.setupLoadStateListener() {
      setCustomLoadStateListener(
         onRefresh = {
            if (!binding.swipeRefreshLayout.isRefreshing) {
               binding.rvMovies.isGone()
               binding.shimmerMoviesByCategory.start()
            }
         },
         onRefreshSuccess = {
            binding.shimmerMoviesByCategory.delayedStop(viewLifecycleOwner.lifecycleScope) {
               binding.rvMovies.isVisible()
               binding.swipeRefreshLayout.stop()
            }
         },
         onRefreshEmpty = {
            // TODO: empty state
         },
         onRefreshError = {
            binding.swipeRefreshLayout.stop()
            if (itemCount > 0) {
               binding.shimmerMoviesByCategory.delayedStop(viewLifecycleOwner.lifecycleScope) {
                  binding.rvMovies.isVisible()
               }
            } else {
               binding.rvMovies.isGone()
               binding.shimmerMoviesByCategory.start()
            }
            binding.root.showSnackBar(getThrownMessage(requireContext()))
         }
      )
   }
}