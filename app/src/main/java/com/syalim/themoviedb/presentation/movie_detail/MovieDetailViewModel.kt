package com.syalim.themoviedb.presentation.movie_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.syalim.themoviedb.domain.model.MovieDetailEntity
import com.syalim.themoviedb.domain.model.MovieItemEntity
import com.syalim.themoviedb.domain.model.MovieTrailerEntity
import com.syalim.themoviedb.domain.use_case.get_movie_detail_use_case.GetMovieDetailUseCase
import com.syalim.themoviedb.domain.use_case.get_movie_reviews_use_case.GetMovieReviewsUseCase
import com.syalim.themoviedb.domain.use_case.get_movie_trailer_use_case.GetMovieTrailerUseCase
import com.syalim.themoviedb.domain.use_case.get_recommendation_movies_use_case.GetRecommendationMoviesUseCase
import com.syalim.themoviedb.presentation.common.State
import com.syalim.themoviedb.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */

@ExperimentalCoroutinesApi
@HiltViewModel
class MovieDetailViewModel @Inject constructor(
   private val getMovieDetailUseCase: GetMovieDetailUseCase,
   private val getMovieReviewsUseCase: GetMovieReviewsUseCase,
   private val getMovieTrailerUseCase: GetMovieTrailerUseCase,
   private val getRecommendationMoviesUseCase: GetRecommendationMoviesUseCase
) : BaseViewModel() {

   private val _detailScreenState: MutableStateFlow<State<Any>> = MutableStateFlow(State.Default())
   private val _movieDetailState: MutableStateFlow<State<MovieDetailEntity>> =
      MutableStateFlow(State.Default())
   private val _movieTrailerState: MutableStateFlow<State<MovieTrailerEntity>> =
      MutableStateFlow(State.Default())
   private val _recommendationMoviesState: MutableStateFlow<State<List<MovieItemEntity>>> =
      MutableStateFlow(State.Default())

   val detailScreenState: StateFlow<State<Any>> get() = _detailScreenState
   val movieDetailState: StateFlow<State<MovieDetailEntity>> get() = _movieDetailState
   val movieTrailerState: StateFlow<State<MovieTrailerEntity>> get() = _movieTrailerState
   val recommendationMoviesState: StateFlow<State<List<MovieItemEntity>>> get() = _recommendationMoviesState

   fun loadDetails(id: String, isFirstLoading: Boolean) = viewModelScope.launch(Dispatchers.IO) {

      _detailScreenState.value = State.Loading(isFirstLoading = isFirstLoading)

      getMovieDetail(id, isFirstLoading)
      getMovieTrailer(id, isFirstLoading)
      getRecommendationMovies(id, isFirstLoading)

      _detailScreenState.value = State.Loaded()
   }

   private val movieId: MutableLiveData<String> = MutableLiveData()

   fun setMovieId(id: String){
      movieId.value = id
   }

   val movieReviews = movieId.asFlow().flatMapLatest {
      getMovieReviewsUseCase.invoke(it)
   }.cachedIn(viewModelScope)

   private suspend fun getMovieDetail(id: String, isFirstLoading: Boolean) {
      handleResult(_movieDetailState, getMovieDetailUseCase.invoke(id), isFirstLoading)
   }

   private suspend fun getMovieTrailer(id: String, isFirstLoading: Boolean) {
      handleResult(_movieTrailerState, getMovieTrailerUseCase(id), isFirstLoading)
   }

   private suspend fun getRecommendationMovies(id: String, isFirstLoading: Boolean) {
      handleResult(_recommendationMoviesState, getRecommendationMoviesUseCase(id), isFirstLoading)
   }

}