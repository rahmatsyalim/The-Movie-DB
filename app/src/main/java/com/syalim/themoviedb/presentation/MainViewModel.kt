package com.syalim.themoviedb.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.syalim.themoviedb.domain.model.MovieItemEntity
import com.syalim.themoviedb.domain.use_case.get_movies_by_genre_use_case.GetMoviesByGenreUseCase
import com.syalim.themoviedb.domain.use_case.get_now_playing_movies_use_case.GetNowPlayingMoviesUseCase
import com.syalim.themoviedb.domain.use_case.get_popular_movies_use_case.GetPopularMoviesUseCase
import com.syalim.themoviedb.domain.use_case.get_top_rated_movies_use_case.GetTopRatedMoviesUseCase
import com.syalim.themoviedb.domain.use_case.get_upcoming_movies_use_case.GetUpcomingMoviesUseCase
import com.syalim.themoviedb.domain.use_case.internet_connected_use_case.InternetConnectedUseCase
import com.syalim.themoviedb.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */

@HiltViewModel
class MainViewModel @Inject constructor(
   private val internetConnectedUseCase: InternetConnectedUseCase,
   private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
   private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
   private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
   private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
   private val getMoviesByGenreUseCase: GetMoviesByGenreUseCase
) : BaseViewModel() {

   private val _upcomingState = MutableStateFlow(State<List<MovieItemEntity>>())
   private val _popularState = MutableStateFlow(State<List<MovieItemEntity>>())
   private val _nowPlayingState = MutableStateFlow(State<List<MovieItemEntity>>())
   private val _topRatedState = MutableStateFlow(State<List<MovieItemEntity>>())
   private val _homeState = MutableStateFlow(State<String>())

   val upcomingState: StateFlow<State<List<MovieItemEntity>>> get() = _upcomingState
   val popularState: StateFlow<State<List<MovieItemEntity>>> get() = _popularState
   val nowPlayingState: StateFlow<State<List<MovieItemEntity>>> get() = _nowPlayingState
   val topRatedState: StateFlow<State<List<MovieItemEntity>>> get() = _topRatedState
   val homeState: StateFlow<State<String>> get() = _homeState

   init {
      loadMovies(false)
   }

   fun loadMovies(isReloading: Boolean) {
      if (isConnected()) {
         viewModelScope.launch {
            _homeState.value = State(isLoading = true, isReloading = isReloading)
            awaitAll(
               ::getUpcomingMovies,
               ::getPopularMovies,
               ::getNowPlayingMovies,
               ::getTopRatedMovies
            )
            _homeState.value = State()
         }
      } else {
         _homeState.value = State(errorMessage = "No internet connection")
      }
   }

   private suspend fun getUpcomingMovies() =
      handleRequest(_upcomingState, getUpcomingMoviesUseCase())

   private suspend fun getPopularMovies() =
      handleRequest(_popularState, getPopularMoviesUseCase())

   private suspend fun getNowPlayingMovies() =
      handleRequest(_nowPlayingState, getNowPlayingMoviesUseCase())

   private suspend fun getTopRatedMovies() =
      handleRequest(_topRatedState, getTopRatedMoviesUseCase())

   private fun isConnected() = internetConnectedUseCase()


   var currentGenre: String? = null

   private var currentGenreResult: Flow<PagingData<MovieItemEntity>>? = null

   fun getMoviesByGenre(genre: String?): Flow<PagingData<MovieItemEntity>> {
      val lastResult = currentGenreResult
      if (genre == currentGenre && lastResult != null) {
         return lastResult
      }
      val newResult = getMoviesByGenreUseCase.invoke(genre)
         .cachedIn(viewModelScope)
      currentGenre = genre
      currentGenreResult = newResult
      return newResult
   }

}