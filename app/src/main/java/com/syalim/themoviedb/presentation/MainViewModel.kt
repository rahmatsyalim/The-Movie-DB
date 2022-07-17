package com.syalim.themoviedb.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.syalim.themoviedb.domain.model.MovieItemEntity
import com.syalim.themoviedb.domain.use_case.get_now_playing_movies_use_case.GetNowPlayingMoviesUseCase
import com.syalim.themoviedb.domain.use_case.get_popular_movies_use_case.GetPopularMoviesUseCase
import com.syalim.themoviedb.domain.use_case.get_top_rated_movies_use_case.GetTopRatedMoviesUseCase
import com.syalim.themoviedb.domain.use_case.get_upcoming_movies_use_case.GetUpcomingMoviesUseCase
import com.syalim.themoviedb.domain.use_case.internet_connected_use_case.InternetConnectedUseCase
import com.syalim.themoviedb.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
   private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase
) : BaseViewModel() {

   private val _upcomingState = MutableLiveData(State<List<MovieItemEntity>>())
   private val _popularState = MutableLiveData(State<List<MovieItemEntity>>())
   private val _nowPlayingState = MutableLiveData(State<List<MovieItemEntity>>())
   private val _topRatedState = MutableLiveData(State<List<MovieItemEntity>>())
   private val _homeState = MutableLiveData(State<String>())

   val upcomingStates: LiveData<State<List<MovieItemEntity>>> get() = _upcomingState
   val popularStateLiveData: LiveData<State<List<MovieItemEntity>>> get() = _popularState
   val nowPlayingStateLiveData: LiveData<State<List<MovieItemEntity>>> get() = _nowPlayingState
   val topRatedStateLiveData: LiveData<State<List<MovieItemEntity>>> get() = _topRatedState
   val homeState: LiveData<State<String>> get() = _homeState

   init {



   }

   fun loadMovies() {
      if (!internetConnectedUseCase()) {
         _homeState.value = State(errorMessage = "No internet connection")
      } else {
         viewModelScope.launch {
            _homeState.value = State(isLoading = true)
            awaitAll(
               ::getUpcomingMovies,
               ::getPopularMovies,
               ::getNowPlayingMovies,
               ::getTopRatedMovies
            )
            _homeState.value = State()
         }
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

}