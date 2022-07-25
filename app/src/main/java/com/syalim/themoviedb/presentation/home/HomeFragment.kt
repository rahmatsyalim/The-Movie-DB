package com.syalim.themoviedb.presentation.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.syalim.themoviedb.R
import com.syalim.themoviedb.common.showSnackBar
import com.syalim.themoviedb.databinding.FragmentHomeBinding
import com.syalim.themoviedb.presentation.MainViewModel
import com.syalim.themoviedb.presentation.State
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

   private lateinit var handler: Handler

   private var nextPage = 0

   private var pagesSize = 0

   override fun init() {

      collectHomeState()

      setViewPagerUpcoming()

      setRecyclerViewPopular()

      setRecyclerViewNowPlaying()

      setRecyclerViewTopRated()

      binding.swipeRefreshLayout.setOnRefreshListener {
         viewModel.loadMovies(false)
      }

   }

   private fun setRecyclerViewPopular() {
      val recyclerAdapter = HomeMoviesAdapter()
      with(recyclerAdapter) {
         binding.rvPopular.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
         binding.rvPopular.adapter = this
         setOnItemClickListener {
            val bundle = Bundle().apply {
               putString("id", it.id.toString())
            }
            findNavController().navigate(R.id.action_home_fragment_to_movie_detail_fragment, bundle)
         }
         collectPopular()
      }
   }

   private fun setRecyclerViewNowPlaying() {
      val recyclerAdapter = HomeMoviesAdapter()
      with(recyclerAdapter) {
         binding.rvInTheatre.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
         binding.rvInTheatre.adapter = recyclerAdapter
         setOnItemClickListener {
            val bundle = Bundle().apply {
               putString("id", it.id.toString())
            }
            findNavController().navigate(R.id.action_home_fragment_to_movie_detail_fragment, bundle)
         }
         collectNowPlaying()
      }
   }

   private fun setRecyclerViewTopRated() {
      val recyclerAdapter = HomeMoviesAdapter()
      with(recyclerAdapter) {
         binding.rvTopRated.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
         binding.rvTopRated.adapter = recyclerAdapter
         setOnItemClickListener {
            val bundle = Bundle().apply {
               putString("id", it.id.toString())
            }
            findNavController().navigate(R.id.action_home_fragment_to_movie_detail_fragment, bundle)
         }
         collectTopRated()
      }
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
            State.Handle(state)(
               onError = { message ->
                  message?.let { binding.root.showSnackBar(it, true) }
               }
            )
         }
      }
   }

   private fun HomeMoviesAdapter.collectPopular() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.popularState.collectLatest { state ->
            State.Handle(state)(
               onSuccess = {
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

   private fun HomeMoviesAdapter.collectNowPlaying() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.nowPlayingState.collectLatest { state ->
            State.Handle(state)(
               onSuccess = {
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

   private fun HomeMoviesAdapter.collectTopRated() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.topRatedState.collectLatest { state ->
            State.Handle(state)(
               onSuccess = {
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

   private fun HomeCarouselAdapter.collectUpcoming() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.upcomingState.collectLatest { state ->
            State.Handle(state)(
               onSuccess = {
                  this@collectUpcoming.data.submitList(it)
                  binding.shimmerUpcoming.apply {
                     stopShimmer()
                     isVisible = false
                  }
                  binding.viewUpcoming.isVisible = true
                  this@collectUpcoming.setImageCarousel()
               }
            )
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
            handler.removeCallbacks(viewPagerUpdate)
            handler.postDelayed(viewPagerUpdate, 3000)

            for (i in 0..pagesSize) {
               // TODO: set unselected dot image
            }
            // TODO: set selected dot image at position
         }
      })
   }

   private val viewPagerUpdate = Runnable {
      if (nextPage == pagesSize) {
         nextPage = 0
      }
      binding.vpUpcoming.setCurrentItem(nextPage, true)
   }

   override fun onDestroy() {
      super.onDestroy()
      handler.removeCallbacks(viewPagerUpdate)
   }

}