package com.syalim.themoviedb.presentation.movie_detail

import com.syalim.themoviedb.domain.use_case.get_movie_detail_use_case.GetMovieDetailUseCase
import com.syalim.themoviedb.domain.use_case.get_movie_reviews_use_case.GetMovieReviewsUseCase
import com.syalim.themoviedb.domain.use_case.get_movie_trailer_use_case.GetMovieTrailerUseCase
import com.syalim.themoviedb.presentation.base.BaseViewModel
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */
class MovieDetailViewModel @Inject constructor(
   private val getMovieDetailUseCase: GetMovieDetailUseCase,
   private val getMovieReviewsUseCase: GetMovieReviewsUseCase,
   private val getMovieTrailerUseCase: GetMovieTrailerUseCase
): BaseViewModel() {

}