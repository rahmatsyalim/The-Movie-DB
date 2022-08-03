package com.syalim.themoviedb.presentation.main.home

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.syalim.themoviedb.R
import com.syalim.themoviedb.databinding.FragmentHomeBinding
import com.syalim.themoviedb.presentation.common.State
import com.syalim.themoviedb.presentation.common.base.BaseFragment
import com.syalim.themoviedb.presentation.common.extensions.showSnackBar
import com.syalim.themoviedb.presentation.main.MainViewModel
import com.syalim.themoviedb.presentation.main.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.abs


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

   private val viewModel: MainViewModel by activityViewModels()

   override fun init() {

      if (viewModel.homeScreenState.value is State.Idle) {
         viewModel.loadMovies(isFirstLoading = true)
      }

      collectHomePageState()

      setViewPagerUpcoming()

      setRecyclerViewPopular()

      setRecyclerViewNowPlaying()

      setRecyclerViewTopRated()

      binding.swipeRefreshLayout.setOnRefreshListener {
         viewModel.loadMovies(isFirstLoading = false)
      }

   }

   private fun collectHomePageState() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.homeScreenState.collectLatest { state ->
            state.handle(
               onLoading = {
                  binding.swipeRefreshLayout.isRefreshing = it
               }
            )
         }
      }
   }

   private fun setRecyclerViewPopular() {
      val recyclerAdapter = MoviesAdapter()
      recyclerAdapter.apply {
         setOnItemClickListener {
            val bundle = Bundle().apply {
               putString("id", it.id.toString())
            }
            findNavController().navigate(R.id.action_home_fragment_to_movie_detail_fragment, bundle)
         }
         collectPopular()
      }
      binding.rvPopular.apply {
         layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
         adapter = recyclerAdapter
      }
   }

   private fun setRecyclerViewNowPlaying() {
      val recyclerAdapter = MoviesAdapter()
      recyclerAdapter.apply {
         setOnItemClickListener {
            val bundle = Bundle().apply {
               putString("id", it.id.toString())
            }
            findNavController().navigate(R.id.action_home_fragment_to_movie_detail_fragment, bundle)
         }
         collectNowPlaying()
      }
      binding.rvInTheater.apply {
         layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
         adapter = recyclerAdapter
      }
   }

   private fun setRecyclerViewTopRated() {
      val recyclerAdapter = MoviesAdapter()
      recyclerAdapter.apply {
         setOnItemClickListener {
            val bundle = Bundle().apply {
               putString("id", it.id.toString())
            }
            findNavController().navigate(R.id.action_home_fragment_to_movie_detail_fragment, bundle)
         }
         collectTopRated()
      }
      binding.rvTopRated.apply {
         layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
         adapter = recyclerAdapter
      }
   }

   private fun setViewPagerUpcoming() {
      val carouselAdapter = CarouselAdapter()
      binding.vpUpcoming.apply {
         offscreenPageLimit = 2
         clipToPadding = false
         clipChildren = false
         getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
         setCarouselTransformer()
         adapter = carouselAdapter
      }
      carouselAdapter.apply {
         setOnItemClickListener {
            val bundle = Bundle().apply {
               putString("id", it.id.toString())
            }
            findNavController().navigate(R.id.action_home_fragment_to_movie_detail_fragment, bundle)
         }
         collectUpcoming()
      }

   }

   private fun MoviesAdapter.collectPopular() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.popularState.collectLatest { state ->
            state.handle(
               onError = {
                  binding.root.showSnackBar(it)
               },
               onLoaded = {
                  this@collectPopular.data.submitList(it)
                  binding.shimmerPopular.apply {
                     stopShimmer()
                     isVisible = false
                  }
                  binding.viewPopular.isVisible = true
               }
            )
         }
      }
   }

   private fun MoviesAdapter.collectNowPlaying() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.nowPlayingState.collectLatest { state ->
            state.handle(
               onError = {
                  binding.root.showSnackBar(it)
               },
               onLoaded = {
                  binding.shimmerNowPlaying.apply {
                     this@collectNowPlaying.data.submitList(it)
                     stopShimmer()
                     isVisible = false
                  }
                  binding.viewNowPlaying.isVisible = true
               }
            )
         }
      }
   }

   private fun MoviesAdapter.collectTopRated() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.topRatedState.collectLatest { state ->
            state.handle(
               onError = {
                  binding.root.showSnackBar(it)
               },
               onLoaded = {
                  this@collectTopRated.data.submitList(it)
                  binding.shimmerTopRated.apply {
                     stopShimmer()
                     isVisible = false
                  }
                  binding.viewTopRated.isVisible = true
               }
            )
         }
      }
   }

   private fun CarouselAdapter.collectUpcoming() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.upcomingState.collectLatest { state ->
            state.handle(
               onLoading = {
                  binding.swipeRefreshLayout.isRefreshing = it
               },
               onError = {
                  binding.root.showSnackBar(it)
               },
               onLoaded = {
                  this@collectUpcoming.data.submitList(it)
                  binding.shimmerUpcoming.apply {
                     stopShimmer()
                     isVisible = false
                  }
                  binding.viewUpcoming.apply {
                     isVisible = true
                  }
                  binding.vpUpcoming.apply {
                     setCarouselJob()
                     setDots()
                  }
               }
            )
         }
      }
   }

   private fun ViewPager2.setCarouselTransformer() {
      val transformer = CompositePageTransformer()
      transformer.addTransformer(MarginPageTransformer(40))
      transformer.addTransformer { page, position ->
         val r = 1 - abs(position)
         page.scaleY = 0.85f + r * 0.14f
      }
      setPageTransformer(transformer)
   }

   private suspend fun ViewPager2.setImageCarousel(interval: Long) {
      delay(interval)

      val itemSize = adapter?.itemCount ?: 0
      val lastItem = if (itemSize > 0) itemSize - 1 else 0
      val nextItem = if (currentItem == lastItem) 0 else currentItem + 1

      setCurrentItem(nextItem, true)

      setImageCarousel(interval)
   }

   private fun ViewPager2.setDots() {
      TabLayoutMediator(binding.tabLayoutCarousel, this) { _, _ ->
      }.attach()
   }

   private var carouselJob: Job? = null

   private fun ViewPager2.setCarouselJob() {
      carouselJob?.cancel()
      carouselJob = viewLifecycleOwner.lifecycleScope.launchWhenResumed {
         setImageCarousel(3000L)
      }
   }

}