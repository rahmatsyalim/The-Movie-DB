package com.syalim.themoviedb.presentation.home

import android.os.Handler
import android.os.Looper
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.syalim.themoviedb.common.showSnackBar
import com.syalim.themoviedb.databinding.FragmentHomeBinding
import com.syalim.themoviedb.presentation.MainViewModel
import com.syalim.themoviedb.presentation.adapter.HomeCarouselAdapter
import com.syalim.themoviedb.presentation.adapter.HomeMoviesAdapter
import com.syalim.themoviedb.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.abs


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

   private val viewModel: MainViewModel by activityViewModels()

   private val progressBarLayout by lazy { binding.includedProgressBar.progressBarLayout }

   private lateinit var handler: Handler

   private var nextPage = 0

   private var pagesSize = 0

   override fun init() {

      collectHomeState()

      setRecyclerViewPopular()

      setRecyclerViewNowPlaying()

      setRecyclerViewTopRated()

      setViewPagerUpcoming()

      binding.swipeRefreshLayout.setOnRefreshListener {
         viewModel.loadMovies(true)
      }

   }

   private fun setRecyclerViewPopular() {
      val recyclerAdapter = HomeMoviesAdapter()
      binding.rvPopular.apply {
         layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
         adapter = recyclerAdapter
      }
      recyclerAdapter.collectPopular()
   }

   private fun setRecyclerViewNowPlaying() {
      val recyclerAdapter = HomeMoviesAdapter()
      binding.rvInTheatre.apply {
         layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
         adapter = recyclerAdapter
      }
      recyclerAdapter.collectNowPlaying()
   }

   private fun setRecyclerViewTopRated() {
      val recyclerAdapter = HomeMoviesAdapter()
      binding.rvTopRated.apply {
         layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
         adapter = recyclerAdapter
      }
      recyclerAdapter.collectTopRated()
   }

   private fun setViewPagerUpcoming() {
      handler = Handler(Looper.myLooper()!!)
      val carouselAdapter = HomeCarouselAdapter(binding.vpUpcoming)

      with(binding.vpUpcoming) {
         adapter = carouselAdapter
         offscreenPageLimit = 3
         clipToPadding = false
         clipChildren = false
         getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
      }

      setCarouselTransformer()

      carouselAdapter.collectUpcoming()

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

   private fun HomeMoviesAdapter.collectPopular() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.popularState.collectLatest { state ->
            state.data?.let {
               if (it.isNotEmpty()) {
                  this@collectPopular.data.submitList(it)
               }
            }
         }
      }
   }

   private fun HomeMoviesAdapter.collectNowPlaying() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.nowPlayingState.collectLatest { state ->
            state.data?.let {
               if (it.isNotEmpty()) {
                  this@collectNowPlaying.data.submitList(it)
               }
            }
         }
      }
   }

   private fun HomeMoviesAdapter.collectTopRated() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.topRatedState.collectLatest { state ->
            state.data?.let {
               if (it.isNotEmpty()) {
                  this@collectTopRated.data.submitList(it)
               }
            }
         }
      }
   }

   private fun HomeCarouselAdapter.collectUpcoming() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.upcomingState.collectLatest { state ->
            state.data?.let {
               if (it.isNotEmpty()) {
                  val data = it.chunked(10).first()
                  this@collectUpcoming.data.submitList(data)
                  this@collectUpcoming.setImageCarousel()
               }
            }
         }
      }
   }

   private fun setCarouselTransformer() {
      val transformer = CompositePageTransformer()
      transformer.addTransformer(MarginPageTransformer(40))
      transformer.addTransformer { page, position ->
         val r = 1 - abs(position)
         page.scaleY = 0.85f + r * 0.14f
      }

      binding.vpUpcoming.setPageTransformer(transformer)
   }

   private fun HomeCarouselAdapter.setImageCarousel() {

      pagesSize = this.data.currentList.size

      binding.vpUpcoming.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
         override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            nextPage = position + 1
            handler.removeCallbacks(viewPagerUpdate())
            handler.postDelayed(viewPagerUpdate(), 2000)

            for (i in 0..pagesSize) {
               // TODO: set unselected dot image
            }
            // TODO: set selected dot image at position
         }
      })
   }

   private fun viewPagerUpdate() = Runnable {
      if (nextPage == pagesSize) {
         nextPage = 0
      }
      binding.vpUpcoming.setCurrentItem(nextPage, true)
   }

}