package com.syalim.themoviedb.presentation.movie_detail

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.syalim.themoviedb.common.setImage
import com.syalim.themoviedb.common.setImageUrl
import com.syalim.themoviedb.databinding.FragmentMovieDetailBinding
import com.syalim.themoviedb.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */
@AndroidEntryPoint
class MovieDetailFragment :
   BaseFragment<FragmentMovieDetailBinding>(FragmentMovieDetailBinding::inflate) {

   private val viewModel: MovieDetailViewModel by viewModels()

   private val arg by navArgs<MovieDetailFragmentArgs>()

   override fun init() {

      binding.topBar.setNavigationOnClickListener {
         findNavController().navigateUp()
      }

      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.loadDetails(id = arg.id, isReloading = false)
      }

      collectDetailState()

   }

   private fun collectState(){
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.detailState.collectLatest { state ->

         }
      }
   }

   private fun collectDetailState(){
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.movieDetailState.collectLatest { state ->
            state.data?.let { data ->
               binding.tvTitle.text = data.originalTitle
               binding.tvGenre.text = data.genres?.joinToString(", ")
               binding.tvDesc.text = data.overview
               binding.ivPoster.setImage(data.posterPath!!.setImageUrl())
               binding.ivBackdrop.setImage(data.backdropPath!!.setImageUrl())
            }
         }
      }
   }
}