package com.syalim.themoviedb.presentation.discover

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.syalim.themoviedb.R
import com.syalim.themoviedb.databinding.FragmentDiscoverBinding
import com.syalim.themoviedb.presentation.MainActivity
import com.syalim.themoviedb.presentation.MainViewModel
import com.syalim.themoviedb.presentation.MoviesPagerAdapter
import com.syalim.themoviedb.presentation.PagingLoadStateAdapter
import com.syalim.themoviedb.presentation.common.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/17
 * rahmatsyalim@gmail.com
 */

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DiscoverFragment : Fragment(R.layout.fragment_discover) {

   private val binding by viewBinding(FragmentDiscoverBinding::bind)

   private val viewModel: MainViewModel by activityViewModels()

   @Inject
   lateinit var moviesAdapter: MoviesPagerAdapter

   private val genreFilterAdapter = GenreFilterAdapter()

   private val menuTune by lazy {
      (requireActivity() as MainActivity).menuTune
   }

   private val selectedGenreFilter: ArrayList<String> = arrayListOf()

   private lateinit var bottomSheetFilterBehavior: BottomSheetBehavior<ConstraintLayout>

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      setDiscoverMovieRecyclerView()

      setDiscoverMovieLoadStateListener()

      collectDiscoverMovie()

      setupMenuTune()

      binding.swipeRefreshLayout.setOnRefreshListener {
         moviesAdapter.refresh()
      }

      setBottomSheetFilter()
   }

   private fun setupMenuTune() {
      menuTune?.setOnMenuItemClickListener {
         bottomSheetFilterBehavior.state = BottomSheetBehavior.STATE_EXPANDED
         return@setOnMenuItemClickListener false
      }
   }

   private fun collectDiscoverMovie() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.discoverMovies.collect { pagingData ->
            moviesAdapter.submitData(pagingData)
         }
      }
   }

   private var collectGenreJob: Job? = null
   private fun collectGenre() {
      collectGenreJob?.cancel()
      collectGenreJob = viewLifecycleOwner.lifecycleScope.launch {
         viewModel.filterGenreContentState.collect { state ->
            state?.onSuccess {
               genreFilterAdapter.let {
                  it.selectedGenre = viewModel.selectedGenreFilter.value
                  it.submitData(this)
               }
            }
         }
      }
   }

   private fun setDiscoverMovieRecyclerView() {
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
               findNavController().navigate(
                  R.id.action_genre_fragment_to_movie_detail_fragment,
                  Bundle().apply { putString("id", it.id.toString()) }
               )
            }
         }.withLoadStateFooter(PagingLoadStateAdapter(moviesAdapter::retry))
      }
   }

   private fun RecyclerView.setFilterRecyclerView() {
      genreFilterAdapter.onItemClickListener = { item, isChecked ->
         if (isChecked) {
            selectedGenreFilter.add(item.id.toString())
         }
         if (!isChecked) {
            selectedGenreFilter.remove(item.id.toString())
         }
      }
      adapter = genreFilterAdapter
   }

   private fun setDiscoverMovieLoadStateListener() {
      moviesAdapter.apply {
         addLoadStateListener { loadState ->
            loadState.setListener(
               itemCount,
               onRefresh = {
                  binding.swipeRefreshLayout.isRefreshing = it
               },
               onEmpty = {
                  binding.tvInfoGenre.apply {
                     showView()
                     text = getString(R.string.no_data_movies)
                  }
                  binding.shimmer.hideView()
               },
               onExist = {
                  binding.tvInfoGenre.hideView()
                  binding.shimmer.apply {
                     stopShimmer()
                     hideView()
                  }
                  binding.rvMovies.showView()
               }
            )
         }
      }
   }

   private fun setBottomSheetFilter() {
      val bottomSheetFilterContainer = binding.bottomSheetFilterParent
      bottomSheetFilterBehavior = BottomSheetBehavior.from(bottomSheetFilterContainer.bottomSheetFilter)
      bottomSheetFilterBehavior.apply {
         state = BottomSheetBehavior.STATE_HIDDEN
         skipCollapsed = true
      }
      bottomSheetFilterContainer.rvFilter.setFilterRecyclerView()
      bottomSheetFilterBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
         override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_EXPANDED) {
               viewModel.selectedGenreFilter.value?.forEach {
                  if (!selectedGenreFilter.contains(it)) {
                     selectedGenreFilter.add(it)
                  }
               }
               collectGenre()
               bottomSheetFilterContainer.btnFilter.setOnClickListener {
                  viewModel.fetchListDiscoverMovies(selectedGenreFilter)
                  bottomSheetFilterBehavior.state = BottomSheetBehavior.STATE_HIDDEN
               }
            }
         }

         override fun onSlide(bottomSheet: View, slideOffset: Float) {}
      })
   }
}