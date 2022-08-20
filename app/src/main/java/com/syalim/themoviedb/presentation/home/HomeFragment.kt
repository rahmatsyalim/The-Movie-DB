package com.syalim.themoviedb.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
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
import com.syalim.themoviedb.domain.model.Movie
import com.syalim.themoviedb.domain.model.MovieCarousel
import com.syalim.themoviedb.presentation.MainViewModel
import com.syalim.themoviedb.presentation.MoviesAdapter
import com.syalim.themoviedb.presentation.common.ContentState
import com.syalim.themoviedb.presentation.common.utils.hideView
import com.syalim.themoviedb.presentation.common.utils.onSuccess
import com.syalim.themoviedb.presentation.common.utils.showView
import com.syalim.themoviedb.presentation.common.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

   private companion object {
      const val CAROUSEL_SLIDE_INTERVAL = 3000L
   }

   private val binding by viewBinding(FragmentHomeBinding::bind)
   private val viewModel: MainViewModel by activityViewModels()

   @Inject
   lateinit var inTheaterMoviesAdapter: CarouselAdapter

   @Inject
   lateinit var upcomingMoviesAdapter: MoviesAdapter

   @Inject
   lateinit var popularMoviesAdapter: MoviesAdapter

   @Inject
   lateinit var topRatedMoviesAdapter: MoviesAdapter

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      setViewPagerInTheater()

      setRecyclerViewPopular()

      setRecyclerUpcoming()

      setRecyclerViewTopRated()

      collectHomeUiState()

      binding.swipeRefreshLayout.setOnRefreshListener {
         viewModel.updateHomeUiEvents(HomeUiState.Events.LoadContents(true))
      }

   }

   private fun collectHomeUiState() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.homeUiState.collect { states ->
            when (states) {
               is HomeUiState.Default ->
                  viewModel.updateHomeUiEvents(HomeUiState.Events.LoadContents(false))
               is HomeUiState.Contents -> {
                  binding.swipeRefreshLayout.isRefreshing = states.isRefreshing
                  states.inTheaterContentState.renderInTheater()
                  states.popularContentState.renderPopular()
                  states.topRatedContentState.renderTopRated()
                  states.upcomingContentState.renderUpcoming()
               }
            }
         }
      }
   }

   // region In Theater Movies
   private fun ContentState<List<MovieCarousel>>.renderInTheater() {
      onSuccess {
         inTheaterMoviesAdapter.submitData(this)
         binding.vpInTheater.apply {
            setInTheaterCarouselJob()
            setInTheaterCarouselDots()
         }
         binding.inTheaterContainer.showView()
         binding.shimmerInTheater.apply {
            stopShimmer()
            hideView()
         }
      }
   }

   private fun setViewPagerInTheater() {
      binding.vpInTheater.apply {
         offscreenPageLimit = 2
         clipToPadding = false
         clipChildren = false
         getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
         adapter = inTheaterMoviesAdapter.apply {
            onItemClickListener = {
               findNavController().navigate(
                  R.id.action_home_fragment_to_movie_detail_fragment,
                  Bundle().apply { putString("id", it.id.toString()) }
               )
            }
         }
         setPageTransformer(
            CompositePageTransformer()
               .apply {
                  addTransformer(MarginPageTransformer(24))
                  addTransformer { page, position ->
                     val r = 1 - abs(position)
                     page.scaleY = 0.85f + r * 0.14f
                  }
               }
         )
      }
   }

   private fun ViewPager2.setInTheaterCarouselDots() {
      TabLayoutMediator(binding.tabLayoutCarousel, this) { _, _ -> }.attach()
   }

   private var inTheaterCarouselJob: Job? = null
   private fun ViewPager2.setInTheaterCarouselJob() {
      inTheaterCarouselJob?.cancel()
      inTheaterCarouselJob = viewLifecycleOwner.lifecycleScope.launch {
         setInTheaterCarousel(CAROUSEL_SLIDE_INTERVAL)
      }
   }

   private suspend fun ViewPager2.setInTheaterCarousel(interval: Long) {
      delay(interval)

      val itemSize = adapter?.itemCount ?: 0
      val lastItem = if (itemSize > 0) itemSize - 1 else 0
      val nextItem = if (currentItem == lastItem) 0 else currentItem + 1

      setCurrentItem(nextItem, true)

      setInTheaterCarousel(interval)
   }
   // endregion

   // region Popular Movies
   private fun ContentState<List<Movie>>.renderPopular() {
      onSuccess {
         popularMoviesAdapter.submitData(this)
         binding.popularContainer.showView()
         binding.shimmerPopular.apply {
            stopShimmer()
            hideView()
         }
      }
   }

   private fun setRecyclerViewPopular() {
      binding.rvPopular.apply {
         layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
         )
         adapter = popularMoviesAdapter.apply {
            onItemClickListener = {
               findNavController().navigate(
                  R.id.action_home_fragment_to_movie_detail_fragment,
                  Bundle().apply { putString("id", it.id.toString()) }
               )
            }
         }
      }
   }
   // endregion

   // region Top Rated Movies
   private fun ContentState<List<Movie>>.renderTopRated() {
      onSuccess {
         topRatedMoviesAdapter.submitData(this)
         binding.topRatedContainer.showView()
         binding.shimmerTopRated.apply {
            stopShimmer()
            hideView()
         }
      }
   }

   private fun setRecyclerViewTopRated() {
      binding.rvTopRated.apply {
         layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
         )
         adapter = topRatedMoviesAdapter.apply {
            onItemClickListener = {
               val bundle = Bundle().apply { putString("id", it.id.toString()) }
               findNavController().navigate(R.id.action_home_fragment_to_movie_detail_fragment, bundle)
            }
         }
      }
   }
   // endregion

   // region Upcoming Movies
   private fun ContentState<List<Movie>>.renderUpcoming() {
      onSuccess {
         upcomingMoviesAdapter.submitData(this)
         binding.upcomingContainer.showView()
         binding.shimmerUpcoming.apply {
            stopShimmer()
            hideView()
         }
      }
   }

   private fun setRecyclerUpcoming() {
      binding.rvUpcoming.apply {
         layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
         )
         adapter = upcomingMoviesAdapter.apply {
            onItemClickListener = {
               val bundle = Bundle().apply { putString("id", it.id.toString()) }
               findNavController().navigate(R.id.action_home_fragment_to_movie_detail_fragment, bundle)
            }
         }
      }
   }
   // endregion

}