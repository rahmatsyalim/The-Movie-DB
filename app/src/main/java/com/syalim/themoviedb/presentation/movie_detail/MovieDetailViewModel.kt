package com.syalim.themoviedb.presentation.movie_detail

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.syalim.themoviedb.domain.model.MovieDetailEntity
import com.syalim.themoviedb.domain.model.MovieTrailerEntity
import com.syalim.themoviedb.domain.use_case.get_movie_detail_use_case.GetMovieDetailUseCase
import com.syalim.themoviedb.domain.use_case.get_movie_reviews_use_case.GetMovieReviewsUseCase
import com.syalim.themoviedb.domain.use_case.get_movie_trailer_use_case.GetMovieTrailerUseCase
import com.syalim.themoviedb.presentation.State
import com.syalim.themoviedb.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
   private val getMovieTrailerUseCase: GetMovieTrailerUseCase
) : BaseViewModel() {

   private val _movieDetailState: MutableStateFlow<State<MovieDetailEntity>> =
      MutableStateFlow(State())
   private val _movieTrailerState: MutableStateFlow<State<MovieTrailerEntity>> =
      MutableStateFlow(State())
   private val _detailState: MutableStateFlow<State<Any>> = MutableStateFlow(State())

   val movieDetailState: StateFlow<State<MovieDetailEntity>> get() = _movieDetailState
   val movieTrailerState: StateFlow<State<MovieTrailerEntity>> get() = _movieTrailerState
   val detailState: StateFlow<State<Any>> get() = _detailState

   fun loadDetails(id: String) = viewModelScope.launch {
      _detailState.emit(State(isLoading = true))
      getMovieDetail(id)
      getMovieTrailer(id)
      delay(300)
      _detailState.emit(State(isLoading = false))
   }

   fun getMovieReview(id: String) = getMovieReviewsUseCase.invoke(id).cachedIn(viewModelScope)

   private suspend fun getMovieDetail(id: String) {
      handleRequest(_movieDetailState, getMovieDetailUseCase.invoke(id))
   }

   private suspend fun getMovieTrailer(id: String) {
      handleRequest(_movieTrailerState, getMovieTrailerUseCase(id))
   }

}