package com.syalim.themoviedb.presentation

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.syalim.themoviedb.common.ConnectivityStatus
import com.syalim.themoviedb.domain.use_case.connectivity.GetConnectivityStateUseCase
import com.syalim.themoviedb.domain.use_case.discover.GetDiscoverMoviesUseCase
import com.syalim.themoviedb.domain.use_case.discover.GetMovieGenreUseCase
import com.syalim.themoviedb.domain.use_case.home.GetHomeInTheaterMoviesUseCase
import com.syalim.themoviedb.domain.use_case.home.GetHomePopularMoviesUseCase
import com.syalim.themoviedb.domain.use_case.home.GetHomeTopRatedMoviesUseCase
import com.syalim.themoviedb.domain.use_case.home.GetHomeUpcomingMoviesUseCase
import com.syalim.themoviedb.presentation.common.utils.asContentState
import com.syalim.themoviedb.presentation.common.utils.isLoading
import com.syalim.themoviedb.presentation.home.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */

@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
   private val getHomeInTheaterMoviesUseCase: GetHomeInTheaterMoviesUseCase,
   private val getHomePopularMoviesUseCase: GetHomePopularMoviesUseCase,
   private val getHomeTopRatedMoviesUseCase: GetHomeTopRatedMoviesUseCase,
   private val getHomeUpcomingMoviesUseCase: GetHomeUpcomingMoviesUseCase,
   private val getDiscoverMoviesUseCase: GetDiscoverMoviesUseCase,
   getMovieGenreUseCase: GetMovieGenreUseCase,
   getNetworkStateUseCase: GetConnectivityStateUseCase
) : ViewModel() {

   val connectivityState: StateFlow<ConnectivityStatus> = getNetworkStateUseCase().stateIn(
      viewModelScope,
      SharingStarted.Lazily,
      ConnectivityStatus.Available
   )

   // region Home
   private val loadHomeContents = MutableLiveData<HomeUiState.Events.LoadContents>()

   val homeUiState: StateFlow<HomeUiState> = loadHomeContents.asFlow().flatMapLatest {
      combine(
         getHomeInTheaterMoviesUseCase.invoke(),
         getHomePopularMoviesUseCase.invoke(),
         getHomeTopRatedMoviesUseCase.invoke(),
         getHomeUpcomingMoviesUseCase.invoke()
      ) { inTheater, popular, topRated, upcoming ->
         HomeUiState.Contents(
            setOf(inTheater, popular, topRated, upcoming).any { it.isLoading } && it.refresh,
            inTheater.asContentState(),
            popular.asContentState(),
            topRated.asContentState(),
            upcoming.asContentState()
         )
      }
   }.stateIn(
      viewModelScope,
      SharingStarted.Lazily,
      HomeUiState.Default
   )

   fun updateHomeUiEvents(event: HomeUiState.Events) {
      when (event) {
         is HomeUiState.Events.LoadContents -> loadHomeContents.postValue(event)
      }
   }
   // endregion

   // region Discover
   val filterGenreContentState =
      getMovieGenreUseCase.invoke()
         .map { it.asContentState() }
         .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            null
         )

   private val _currentSelectedGenre = MutableLiveData(mutableListOf<String>())
   val selectedGenreFilter: LiveData<MutableList<String>> get() = _currentSelectedGenre

   fun fetchListDiscoverMovies(genreList: MutableList<String>) {
      _currentSelectedGenre.postValue(genreList)
   }

   val discoverMovies = selectedGenreFilter.asFlow().flatMapLatest {
      getDiscoverMoviesUseCase(it.joinToString(","))
   }.cachedIn(viewModelScope)
   // endregion

}


