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
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


/**
 * Created by Rahmat Syalim on 2022/07/17
 * rahmatsyalim@gmail.com
 */

@AndroidEntryPoint
class GenreFragment : BaseFragment<FragmentGenreBinding>(FragmentGenreBinding::inflate) {

   private val viewModel: MainViewModel by activityViewModels()

   private lateinit var moviesAdapter: MoviesPagerAdapter

   private lateinit var genreFilterAdapter: GenreFilterAdapter

   private val ivFilter by lazy {
      (requireActivity() as MainActivity).ivFiler
   }

   private val pickedGenre: ArrayList<String> = arrayListOf()

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

      collectMoviesByGenre()

      setMoviesLoadStateListener()

      binding.swipeRefreshLayout.setOnRefreshListener {
         moviesAdapter.refresh()
      }

      moviesAdapter.retry()

      ivFilter.setOnClickListener {
         setBottomSheetAkun()
         viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getGenre()
         }
      }

   }

   private var collectMoviesByGenreJob: Job? = null

   private fun collectMoviesByGenre() {
      collectMoviesByGenreJob?.cancel()
      collectMoviesByGenreJob = viewLifecycleOwner.lifecycleScope.launch {
         viewModel.moviesByGenre.collectLatest { pagingData ->
            moviesAdapter.submitData(pagingData)
         }
      }
   }

   private var collectGenreJob: Job? = null

   private fun collectGenre() {
      collectGenreJob?.cancel()
      collectGenreJob = viewLifecycleOwner.lifecycleScope.launch {
         viewModel.filterState.collectLatest { state ->
            State.Handle(state)(
               onLoading = {
                  bindingBottomSheet.progressBar.isVisible = it
               },
               onSuccess = {
                  genreFilterAdapter.data.submitList(it)
               }
            )
         }
      }
   }

   private fun setMoviesRecyclerView() {
      moviesAdapter = MoviesPagerAdapter()
      binding.rvMovies.apply {
         layoutManager = GridLayoutManager(requireContext(), 3)
         adapter = moviesAdapter.withLoadStateFooter(PagingLoadStateAdapter())
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

   private fun setFilterRecyclerView(genre: List<String>?) {
      genreFilterAdapter = GenreFilterAdapter(genre)
      bindingBottomSheet.rvFilter.apply {
         layoutManager = LinearLayoutManager(requireContext())
         adapter = genreFilterAdapter
      }
      genreFilterAdapter.setOnItemClickListener { item, isChecked ->
         if (isChecked) {
            pickedGenre.add(item.id.toString())
         }
         if (!isChecked) {
            pickedGenre.remove(item.id.toString())
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
         onSuccess = {
            binding.tvInfoGenre.isVisible = false
            binding.shimmer.apply {
               stopShimmer()
               isVisible = false
            }
            binding.rvMovies.isVisible = true
         }
      )
   }

   private fun setBottomSheetAkun() {
      bottomSheetdialog.setContentView(bindingBottomSheet.root)
      bottomSheetdialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
      bottomSheetdialog.behavior.skipCollapsed = true
      bottomSheetdialog.behavior.maxHeight = requireActivity().getScreenHeight() - 150

      for (i in viewModel.currentGenre) {
         if (!pickedGenre.contains(i)) {
            pickedGenre.add(i)
         }
      }

      setFilterRecyclerView(viewModel.currentGenre)

      collectGenre()

      bindingBottomSheet.btnFilter.setOnClickListener {
         viewModel.currentGenre = pickedGenre
         viewModel.getMoviesByGenre()
         collectMoviesByGenre()
         bottomSheetdialog.dismiss()
      }

      bottomSheetdialog.show()
   }
}