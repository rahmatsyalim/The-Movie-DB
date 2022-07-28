package com.syalim.themoviedb.presentation.movie_detail

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.syalim.themoviedb.domain.model.MovieDetailEntity
import com.syalim.themoviedb.domain.model.MovieItemEntity
import com.syalim.themoviedb.domain.model.MovieTrailerEntity
import com.syalim.themoviedb.domain.model.ReviewItemEntity
import com.syalim.themoviedb.domain.use_case.get_movie_detail_use_case.GetMovieDetailUseCase
import com.syalim.themoviedb.domain.use_case.get_movie_reviews_use_case.GetMovieReviewsUseCase
import com.syalim.themoviedb.domain.use_case.get_movie_trailer_use_case.GetMovieTrailerUseCase
import com.syalim.themoviedb.domain.use_case.get_recommendation_movies_use_case.GetRecommendationMoviesUseCase
import com.syalim.themoviedb.presentation.State
import com.syalim.themoviedb.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */
@HiltViewModel
class MovieDetailViewModel @Inject constructor(
   private val getMovieDetailUseCase: GetMovieDetailUseCase,
   private val getMovieReviewsUseCase: GetMovieReviewsUseCase,
   private val getMovieTrailerUseCase: GetMovieTrailerUseCase,
   private val getRecommendationMoviesUseCase: GetRecommendationMoviesUseCase
) : BaseViewModel() {

   private val _detailPageState: MutableStateFlow<State<Any>> = MutableStateFlow(State.Default())

   private val _movieDetailState: MutableStateFlow<State<MovieDetailEntity>> =
      MutableStateFlow(State.Default())
   private val _movieTrailerState: MutableStateFlow<State<MovieTrailerEntity>> =
      MutableStateFlow(State.Default())
   private val _recommendationMoviesState: MutableStateFlow<State<List<MovieItemEntity>>> =
      MutableStateFlow(State.Default())

   val detailPageState: StateFlow<State<Any>> get() = _detailPageState

   val movieDetailState: StateFlow<State<MovieDetailEntity>> get() = _movieDetailState
   val movieTrailerState: StateFlow<State<MovieTrailerEntity>> get() = _movieTrailerState
   val recommendationMoviesState: StateFlow<State<List<MovieItemEntity>>> get() = _recommendationMoviesState

   fun loadDetails(id: String) = viewModelScope.launch {
      getMovieTrailer(id)
      getRecommendationMovies(id)
      getMovieDetail(id)
      getMovieReview(id)
      _detailPageState.value = State.Loaded("")
   }

   var movieReviews: Flow<PagingData<ReviewItemEntity>> = flowOf(PagingData.empty())

   private fun getMovieReview(id: String) {

      movieReviews = getMovieReviewsUseCase.invoke(id).cachedIn(viewModelScope)
   }

   private suspend fun getMovieDetail(id: String) {
      handleRequest(_movieDetailState, getMovieDetailUseCase.invoke(id))
   }

   private suspend fun getMovieTrailer(id: String) {
      handleRequest(_movieTrailerState, getMovieTrailerUseCase(id))
   }

   private suspend fun getRecommendationMovies(id: String) {
      handleRequest(_recommendationMoviesState, getRecommendationMoviesUseCase(id))
   }

}