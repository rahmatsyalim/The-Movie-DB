package com.syalim.themoviedb.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.syalim.themoviedb.domain.model.GenreItemEntity
import com.syalim.themoviedb.domain.model.MovieItemEntity
import com.syalim.themoviedb.domain.use_case.get_discover_movies_use_case.GetDiscoverMoviesUseCase
import com.syalim.themoviedb.domain.use_case.get_movie_genre_use_case.GetMovieGenreUseCase
import com.syalim.themoviedb.domain.use_case.get_now_playing_movies_use_case.GetNowPlayingMoviesUseCase
import com.syalim.themoviedb.domain.use_case.get_popular_movies_use_case.GetPopularMoviesUseCase
import com.syalim.themoviedb.domain.use_case.get_top_rated_movies_use_case.GetTopRatedMoviesUseCase
import com.syalim.themoviedb.domain.use_case.get_upcoming_movies_use_case.GetUpcomingMoviesUseCase
import com.syalim.themoviedb.presentation.common.State
import com.syalim.themoviedb.presentation.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
   private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
   private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
   private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase,
   private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
   private val getDiscoverMoviesUseCase: GetDiscoverMoviesUseCase,
   private val getMovieGenreUseCase: GetMovieGenreUseCase
) : BaseViewModel() {

   private val _homeScreenState: MutableStateFlow<State<Any>> = MutableStateFlow(State.Idle())
   private val _upcomingState: MutableStateFlow<State<List<MovieItemEntity>>> =
      MutableStateFlow(State.Idle())
   private val _popularState: MutableStateFlow<State<List<MovieItemEntity>>> =
      MutableStateFlow(State.Idle())
   private val _nowPlayingState: MutableStateFlow<State<List<MovieItemEntity>>> =
      MutableStateFlow(State.Idle())
   private val _topRatedState: MutableStateFlow<State<List<MovieItemEntity>>> =
      MutableStateFlow(State.Idle())
   private val _filterGenreState: MutableStateFlow<State<List<GenreItemEntity>>> =
      MutableStateFlow(State.Idle())

   val homeScreenState: StateFlow<State<Any>> get() = _homeScreenState
   val upcomingState: StateFlow<State<List<MovieItemEntity>>> get() = _upcomingState
   val popularState: StateFlow<State<List<MovieItemEntity>>> get() = _popularState
   val nowPlayingState: StateFlow<State<List<MovieItemEntity>>> get() = _nowPlayingState
   val topRatedState: StateFlow<State<List<MovieItemEntity>>> get() = _topRatedState
   val filterGenreState: StateFlow<State<List<GenreItemEntity>>> get() = _filterGenreState

   fun loadMovies(isFirstLoading: Boolean) = viewModelScope.launch {

      _homeScreenState.value = State.Loading(isFirstLoading = isFirstLoading)

      getUpcomingMovies(isFirstLoading)
      getPopularMovies(isFirstLoading)
      getNowPlayingMovies(isFirstLoading)
      getTopRatedMovies(isFirstLoading)

      _homeScreenState.value = State.Loaded()

   }


   private suspend fun getUpcomingMovies(isFirstLoading: Boolean) =
      handleResource(_upcomingState, getUpcomingMoviesUseCase(), isFirstLoading)

   private suspend fun getPopularMovies(isFirstLoading: Boolean) =
      handleResource(_popularState, getPopularMoviesUseCase(), isFirstLoading)

   private suspend fun getNowPlayingMovies(isFirstLoading: Boolean) =
      handleResource(_nowPlayingState, getNowPlayingMoviesUseCase(), isFirstLoading)

   private suspend fun getTopRatedMovies(isFirstLoading: Boolean) =
      handleResource(_topRatedState, getTopRatedMoviesUseCase(), isFirstLoading)

   suspend fun getMovieGenre() = handleResource(_filterGenreState, getMovieGenreUseCase())

   private val _currentSelectedGenre = MutableLiveData(emptyList<String>())
   val currentSelectedGenre: LiveData<List<String>> get() = _currentSelectedGenre

   fun setCurrentGenre(genreList: List<String>) {
      _currentSelectedGenre.value = genreList
   }

   val discoverMovies = currentSelectedGenre.asFlow().flatMapLatest {
      getDiscoverMoviesUseCase(it.joinToString(","))
   }.cachedIn(viewModelScope)

}