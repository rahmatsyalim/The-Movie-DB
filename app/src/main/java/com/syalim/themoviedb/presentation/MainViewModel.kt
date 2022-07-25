package com.syalim.themoviedb.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.syalim.themoviedb.domain.model.GenreItemEntity
import com.syalim.themoviedb.domain.model.MovieItemEntity
import com.syalim.themoviedb.domain.use_case.get_movie_genre_use_case.GetMovieGenreUseCase
import com.syalim.themoviedb.domain.use_case.get_movies_by_genre_use_case.GetMoviesByGenreUseCase
import com.syalim.themoviedb.domain.use_case.get_now_playing_movies_use_case.GetNowPlayingMoviesUseCase
import com.syalim.themoviedb.domain.use_case.get_popular_movies_use_case.GetPopularMoviesUseCase
import com.syalim.themoviedb.domain.use_case.get_top_rated_movies_use_case.GetTopRatedMoviesUseCase
import com.syalim.themoviedb.domain.use_case.get_upcoming_movies_use_case.GetUpcomingMoviesUseCase
import com.syalim.themoviedb.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
   private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
   private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
   private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
   private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
   private val getMoviesByGenreUseCase: GetMoviesByGenreUseCase,
   private val getMovieGenreUseCase: GetMovieGenreUseCase
) : BaseViewModel() {

   private val _upcomingState: MutableStateFlow<State<List<MovieItemEntity>>> =
      MutableStateFlow(State())
   private val _popularState: MutableStateFlow<State<List<MovieItemEntity>>> =
      MutableStateFlow(State())
   private val _nowPlayingState: MutableStateFlow<State<List<MovieItemEntity>>> =
      MutableStateFlow(State())
   private val _topRatedState: MutableStateFlow<State<List<MovieItemEntity>>> =
      MutableStateFlow(State())
   private val _filterState: MutableStateFlow<State<List<GenreItemEntity>>> =
      MutableStateFlow(State())

   val upcomingState: StateFlow<State<List<MovieItemEntity>>> get() = _upcomingState
   val popularState: StateFlow<State<List<MovieItemEntity>>> get() = _popularState
   val nowPlayingState: StateFlow<State<List<MovieItemEntity>>> get() = _nowPlayingState
   val topRatedState: StateFlow<State<List<MovieItemEntity>>> get() = _topRatedState
   val filterState: StateFlow<State<List<GenreItemEntity>>> get() = _filterState

   init {
      loadMovies(true)
   }

   fun loadMovies(isFirstLoading: Boolean) = viewModelScope.launch {
      getUpcomingMovies(isFirstLoading)
      getPopularMovies(isFirstLoading)
      getNowPlayingMovies(isFirstLoading)
      getTopRatedMovies(isFirstLoading)
   }


   private suspend fun getUpcomingMovies(isFirstLoading: Boolean) =
      handleRequest(_upcomingState, getUpcomingMoviesUseCase(), isFirstLoading)

   private suspend fun getPopularMovies(isFirstLoading: Boolean) =
      handleRequest(_popularState, getPopularMoviesUseCase(), isFirstLoading)

   private suspend fun getNowPlayingMovies(isFirstLoading: Boolean) =
      handleRequest(_nowPlayingState, getNowPlayingMoviesUseCase(), isFirstLoading)

   private suspend fun getTopRatedMovies(isFirstLoading: Boolean) =
      handleRequest(_topRatedState, getTopRatedMoviesUseCase(), isFirstLoading)

   suspend fun getGenre() =
      handleRequest(_filterState, getMovieGenreUseCase())


   var currentGenre: List<String> = emptyList()
   private fun getGenreString() = currentGenre.joinToString(",")

   var moviesByGenre = getMoviesByGenreUseCase.invoke(getGenreString()).cachedIn(viewModelScope)

   fun getMoviesByGenre() {
      moviesByGenre = getMoviesByGenreUseCase.invoke(getGenreString()).cachedIn(viewModelScope)
   }

}