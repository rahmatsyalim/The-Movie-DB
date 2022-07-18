package com.syalim.themoviedb.presentation.movie_detail

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.syalim.themoviedb.databinding.FragmentMovieDetailBinding
import com.syalim.themoviedb.presentation.base.BaseFragment


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */
class MovieDetailFragment: BaseFragment<FragmentMovieDetailBinding>(FragmentMovieDetailBinding::inflate) {

   private val viewModel: MovieDetailViewModel by viewModels()

   private val arg by navArgs<MovieDetailFragmentArgs>()

   override fun init() {



   }
}