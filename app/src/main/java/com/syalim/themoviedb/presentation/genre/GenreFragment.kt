package com.syalim.themoviedb.presentation.genre

import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.syalim.themoviedb.common.showToast
import com.syalim.themoviedb.databinding.BottomSheetFilterBinding
import com.syalim.themoviedb.databinding.FragmentGenreBinding
import com.syalim.themoviedb.presentation.MainActivity
import com.syalim.themoviedb.presentation.MainViewModel
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
class GenreFragment: BaseFragment<FragmentGenreBinding>(FragmentGenreBinding::inflate) {

   private val viewModel: MainViewModel by activityViewModels()

   private val moviesAdapter = MoviesPagerAdapter()

   private val ivFilter by lazy {
      (requireActivity() as MainActivity).ivFiler
   }

   override fun init() {

      setRecyclerView()

      collectMoviesByGenre(viewModel.currentGenre)

      loadStateListener()

      binding.swipeRefreshLayout.setOnRefreshListener {
         moviesAdapter.refresh()
      }

      ivFilter.setOnClickListener {
         setBottomSheetAkun()
      }

   }

   private fun collectMoviesByGenre(genre: String?){
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.getMoviesByGenre(genre = genre).collectLatest { pagingData ->
            moviesAdapter.submitData(pagingData)
         }
      }
   }

   private fun setRecyclerView(){
      binding.rvMovies.layoutManager = GridLayoutManager(requireContext(),3)
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

   fun setBottomSheetAkun() {
      val bindingBottomSheet = BottomSheetFilterBinding.inflate(LayoutInflater.from(requireContext()))
      val dialog = BottomSheetDialog(requireContext())

      dialog.setContentView(bindingBottomSheet.root)

      bindingBottomSheet.rvFilter

      dialog.show()
   }
}