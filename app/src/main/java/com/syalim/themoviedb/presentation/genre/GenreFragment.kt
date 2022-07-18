package com.syalim.themoviedb.presentation.genre

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.syalim.themoviedb.common.getScreenHeight
import com.syalim.themoviedb.common.showToast
import com.syalim.themoviedb.databinding.BottomSheetFilterBinding
import com.syalim.themoviedb.databinding.FragmentGenreBinding
import com.syalim.themoviedb.presentation.MainActivity
import com.syalim.themoviedb.presentation.MainViewModel
import com.syalim.themoviedb.presentation.adapter.GenreFilterAdapter
import com.syalim.themoviedb.presentation.adapter.MoviesPagerAdapter
import com.syalim.themoviedb.presentation.adapter.PagingLoadStateAdapter
import com.syalim.themoviedb.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


/**
 * Created by Rahmat Syalim on 2022/07/17
 * rahmatsyalim@gmail.com
 */

@AndroidEntryPoint
class GenreFragment : BaseFragment<FragmentGenreBinding>(FragmentGenreBinding::inflate) {

   private val viewModel: MainViewModel by activityViewModels()

   private val moviesAdapter = MoviesPagerAdapter()

   private lateinit var genreFilterAdapter : GenreFilterAdapter

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

      collectMoviesByGenre(viewModel.currentGenre)

      loadStateListener()

      binding.swipeRefreshLayout.setOnRefreshListener {
         moviesAdapter.refresh()
      }

      ivFilter.setOnClickListener {
         setBottomSheetAkun()
         viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getGenre()
         }
      }

   }

   private fun collectMoviesByGenre(genre: List<String>?) {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.getMoviesByGenre(genre = genre).collectLatest { pagingData ->
            moviesAdapter.submitData(pagingData)
         }
      }
   }

   private fun GenreFilterAdapter.collectGenre() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.filterState.collectLatest { state ->
            bindingBottomSheet.progressBar.isVisible = state.isLoading
            state.data?.let {
               this@collectGenre.data.submitList(it)
            }
         }
      }
   }

   private fun setMoviesRecyclerView() {
      binding.rvMovies.layoutManager = GridLayoutManager(requireContext(), 3)
      with(moviesAdapter) {
         binding.rvMovies.adapter = withLoadStateFooter(PagingLoadStateAdapter())
         onItemClickListener {
            val bundle = Bundle().apply {
               putString("id", it.id.toString())
            }
//            findNavController().navigate(destid, bundle)
         }
      }
   }

   private fun loadStateListener() {
      moviesAdapter.addLoadStateListener { loadState ->

         binding.swipeRefreshLayout.isRefreshing = loadState.refresh is LoadState.Loading

         val errorState = when {
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            else -> null
         }
         errorState?.error?.message?.let {
            requireContext().showToast(it)
         }
      }
   }

   private fun setFilterRecyclerView(genre: List<String>?) {
      genreFilterAdapter = GenreFilterAdapter(genre)
      genreFilterAdapter.setOnItemClickListener { item, isChecked ->
         if (isChecked) {
            pickedGenre.add(item.id.toString())
         }
         if (!isChecked) {
            pickedGenre.remove(item.id.toString())
         }
      }

      bindingBottomSheet.rvFilter.apply {
         layoutManager = LinearLayoutManager(requireContext())
         adapter = genreFilterAdapter
      }

      genreFilterAdapter.collectGenre()

   }


   private fun setBottomSheetAkun() {
      bottomSheetdialog.setContentView(bindingBottomSheet.root)
      bottomSheetdialog.behavior.maxHeight = requireActivity().getScreenHeight() - 150

      viewModel.currentGenre?.let {
         for (i in it){
            if (!pickedGenre.contains(i)){
               pickedGenre.add(i)
            }
         }
      }

      setFilterRecyclerView(pickedGenre)

      bindingBottomSheet.btnFilter.setOnClickListener {
         collectMoviesByGenre(pickedGenre)
         bottomSheetdialog.dismiss()
      }

      bottomSheetdialog.show()
   }
}