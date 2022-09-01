package com.syalim.themoviedb.presentation.main.discover

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.syalim.themoviedb.R
import com.syalim.themoviedb.databinding.FragmentDiscoverBinding
import com.syalim.themoviedb.presentation.utils.ext.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/17
 * rahmatsyalim@gmail.com
 */

@AndroidEntryPoint
class DiscoverFragment : Fragment(R.layout.fragment_discover) {

   private val binding by viewBinding(FragmentDiscoverBinding::bind)

   private val viewModel: DiscoverViewModel by hiltNavGraphViewModels(R.id.nav_movie_main)

   @Inject
   lateinit var moviesAdapter: DiscoverPagerAdapter

   private val navController by lazy { parentFragment?.parentFragment?.findNavController() }


   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      initUi()
   }

   private fun initUi() {
      setupDiscover()
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.discoverMoviesState.collect { pagingData ->
            moviesAdapter.submitData(pagingData)
         }
      }
      binding.swipeRefreshLayout.setOnRefreshListener {
         moviesAdapter.refresh()
      }
   }

   private fun setupDiscover() {
      binding.rvMovies.apply {
         layoutManager = GridLayoutManager(requireContext(), 3).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
               override fun getSpanSize(position: Int): Int {
                  return if (position == moviesAdapter.itemCount && moviesAdapter.itemCount > 0) {
                     3
                  } else {
                     1
                  }
               }
            }
         }
         adapter = moviesAdapter.apply {
            onItemClickListener = {
               navController?.navigate(
                  R.id.action_main_fragment_to_movie_detail_fragment,
                  Bundle().apply { putString("id", it.id.toString()) }
               )
            }
            setupDiscoverLoadStateListener()
         }.withLoadStateFooter(DiscoverLoadStateAdapter(moviesAdapter::retry))
         addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
               super.onScrolled(recyclerView, dx, dy)
               if (dy <= 0) binding
            }
         })
      }
   }

   private fun DiscoverPagerAdapter.setupDiscoverLoadStateListener() {
      addLoadStateListener {
         it.onRefresh(
            itemCount = itemCount,
            started = {
               binding.tvInfo.isGone()
               if (!binding.swipeRefreshLayout.isRefreshing) {
                  binding.rvMovies.isGone()
                  binding.shimmerDiscover.start()
               }
            },
            finishExist = {
               binding.shimmerDiscover.delayedStop(viewLifecycleOwner.lifecycleScope) {
                  binding.rvMovies.isVisible()
                  binding.swipeRefreshLayout.stop()
               }
            },
            finishEmpty = {
               binding.shimmerDiscover.delayedStop(viewLifecycleOwner.lifecycleScope) {
                  binding.swipeRefreshLayout.stop()
                  binding.tvInfo.apply {
                     text = getString(R.string.no_movies_found)
                     isVisible()
                  }
               }
            },
            finishError = {
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

}