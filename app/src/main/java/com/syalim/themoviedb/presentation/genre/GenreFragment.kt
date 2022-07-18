package com.syalim.themoviedb.presentation.genre

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.syalim.themoviedb.databinding.FragmentGenreBinding
import com.syalim.themoviedb.presentation.MainViewModel
import com.syalim.themoviedb.presentation.adapter.HomeMoviesAdapter
import com.syalim.themoviedb.presentation.adapter.MoviesPagerAdapter
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

   override fun init() {

   }

   private fun collectMoviesByGenre(genre: String?){
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.getMoviesByGenre(genre = genre).collectLatest { pagingData ->
            moviesAdapter.submitData(pagingData)
         }
      }
   }
}