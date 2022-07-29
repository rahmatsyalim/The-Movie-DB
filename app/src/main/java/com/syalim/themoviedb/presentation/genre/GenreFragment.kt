package com.syalim.themoviedb.presentation.genre

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.syalim.themoviedb.R
import com.syalim.themoviedb.common.getScreenHeight
import com.syalim.themoviedb.common.showSnackBar
import com.syalim.themoviedb.databinding.BottomSheetFilterBinding
import com.syalim.themoviedb.databinding.FragmentGenreBinding
import com.syalim.themoviedb.presentation.MainActivity
import com.syalim.themoviedb.presentation.MainViewModel
import com.syalim.themoviedb.presentation.PagingLoadState
import com.syalim.themoviedb.presentation.State
import com.syalim.themoviedb.presentation.adapter.GenreFilterAdapter
import com.syalim.themoviedb.presentation.adapter.MoviesPagerAdapter
import com.syalim.themoviedb.presentation.adapter.PagingLoadStateAdapter
import com.syalim.themoviedb.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch


/**
 * Created by Rahmat Syalim on 2022/07/17
 * rahmatsyalim@gmail.com
 */

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class GenreFragment : BaseFragment<FragmentGenreBinding>(FragmentGenreBinding::inflate) {

   private val viewModel: MainViewModel by activityViewModels()

   private lateinit var moviesAdapter: MoviesPagerAdapter

   private lateinit var moviesLoadStateAdapter: PagingLoadStateAdapter

   private val genreFilterAdapter = GenreFilterAdapter()

   private val menuTune by lazy {
      (requireActivity() as MainActivity).menuTune
   }

   private val currentSelectedGenre: ArrayList<String> = arrayListOf()

   private val bindingBottomSheet by lazy {
      BottomSheetFilterBinding.inflate(
         LayoutInflater.from(
            requireContext()
         )
      )
   }

   private val bottomSheetdialog by lazy { BottomSheetDialog(requireContext()) }

   override fun init() {

      setMoviesRecyclerView()

      setMoviesLoadStateListener()

      collectMoviesByGenre()

      collectGenre()

      setupMenuTune()

      binding.swipeRefreshLayout.setOnRefreshListener {
         moviesAdapter.refresh()
      }

   }

   private fun setupMenuTune() {
      menuTune?.setOnMenuItemClickListener {
         setBottomSheetFilter()
         if (viewModel.filterGenreState.value is State.Default) {
            viewLifecycleOwner.lifecycleScope.launch {
               viewModel.getGenre()
            }
         }
         return@setOnMenuItemClickListener false
      }
   }

   private var collectMoviesByGenreJob: Job? = null
   private fun collectMoviesByGenre() {
      collectMoviesByGenreJob?.cancel()
      collectMoviesByGenreJob = viewLifecycleOwner.lifecycleScope.launch {
         viewModel.moviesByGenre.distinctUntilChanged().collectLatest { pagingData ->
            moviesAdapter.submitData(pagingData)
         }
      }
   }

   private fun collectGenre() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.filterGenreState.collectLatest { state ->
            State.Handle(state)(
               onLoading = {
                  bindingBottomSheet.progressBar.isVisible = it
               },
               onLoaded = {
                  genreFilterAdapter.data.submitList(it)
               }
            )
         }
      }
   }

   private fun setMoviesRecyclerView() {
      moviesAdapter = MoviesPagerAdapter()
      moviesLoadStateAdapter = PagingLoadStateAdapter()
      val gridLayoutManager = GridLayoutManager(requireContext(), 3)
      gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
         override fun getSpanSize(position: Int): Int {
            return if (position == moviesAdapter.itemCount && moviesLoadStateAdapter.itemCount > 0) {
               3
            } else {
               1
            }
         }
      }
      binding.rvMovies.apply {
         layoutManager = gridLayoutManager
         adapter = moviesAdapter.withLoadStateFooter(moviesLoadStateAdapter)
      }
      moviesAdapter.onItemClickListener {
         val bundle = Bundle().apply {
            putString("id", it.id.toString())
         }
         findNavController().navigate(
            R.id.action_genre_fragment_to_movie_detail_fragment,
            bundle
         )
      }
   }

   private fun setFilterRecyclerView(selectedGenre: List<String>?) {
      genreFilterAdapter.selectedGenre = selectedGenre
      bindingBottomSheet.rvFilter.apply {
         layoutManager = LinearLayoutManager(requireContext())
         adapter = genreFilterAdapter
      }
      genreFilterAdapter.setOnItemClickListener { item, isChecked ->
         if (isChecked) {
            currentSelectedGenre.add(item.id.toString())
         }
         if (!isChecked) {
            currentSelectedGenre.remove(item.id.toString())
         }
      }
   }

   private fun setMoviesLoadStateListener() {
      PagingLoadState(moviesAdapter).invoke(
         onLoading = {
            binding.swipeRefreshLayout.isRefreshing = it
         },
         onError = {
            binding.root.showSnackBar(it, true)
         },
         onEmpty = {
            binding.tvInfoGenre.isVisible = true
            binding.tvInfoGenre.text = "No data found"
            binding.rvMovies.isVisible = false
            binding.shimmer.isVisible = false
         },
         onLoaded = {
            binding.tvInfoGenre.isVisible = false
            binding.shimmer.apply {
               stopShimmer()
               isVisible = false
            }
            binding.rvMovies.isVisible = true
         }
      )
   }

   private fun setBottomSheetFilter() {
      bottomSheetdialog.setContentView(bindingBottomSheet.root)
      bottomSheetdialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
      bottomSheetdialog.behavior.skipCollapsed = true
      bottomSheetdialog.behavior.maxHeight = requireActivity().getScreenHeight() - 150

      setFilterRecyclerView(viewModel.currentSelectedGenre.value)

      viewModel.currentSelectedGenre.value?.let { genre ->
         genre.forEach {
            if (!currentSelectedGenre.contains(it)) {
               currentSelectedGenre.add(it)
            }
         }
      }

      bindingBottomSheet.btnFilter.setOnClickListener {
         viewModel.setCurrentGenre(currentSelectedGenre)
         bottomSheetdialog.dismiss()
      }

      bottomSheetdialog.show()
   }
}