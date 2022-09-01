package com.syalim.themoviedb.presentation.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syalim.themoviedb.domain.movie.usecase.home.GetHomeInTheaterMoviesUseCase
import com.syalim.themoviedb.domain.movie.usecase.home.GetHomePopularMoviesUseCase
import com.syalim.themoviedb.domain.movie.usecase.home.GetHomeTopRatedMoviesUseCase
import com.syalim.themoviedb.domain.movie.usecase.home.GetHomeUpcomingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/08/28
 * rahmatsyalim@gmail.com
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
   private val getHomeInTheaterMoviesUseCase: GetHomeInTheaterMoviesUseCase,
   private val getHomePopularMoviesUseCase: GetHomePopularMoviesUseCase,
   private val getHomeTopRatedMoviesUseCase: GetHomeTopRatedMoviesUseCase,
   private val getHomeUpcomingMoviesUseCase: GetHomeUpcomingMoviesUseCase
) : ViewModel() {
   private val _homeUiState = MutableStateFlow(HomeUiState())
   val homeUiState: StateFlow<HomeUiState> get() = _homeUiState

   init {
      fetchContents()
   }

   var carouselPosition = 0

   private fun fetchContents() {
      var data = emptyList<com.syalim.themoviedb.domain.movie.model.Movies>()
      var error = emptyList<Throwable>()
      combine(
         getHomeInTheaterMoviesUseCase(),
         getHomePopularMoviesUseCase(),
         getHomeTopRatedMoviesUseCase(),
         getHomeUpcomingMoviesUseCase()
      ) { a, b, c, d ->
         listOf(a, b, c, d).forEach { result ->
            when (result) {
               is com.syalim.themoviedb.core.common.Result.Success -> data = data + result.data
               is com.syalim.themoviedb.core.common.Result.Failure -> {
                  result.data?.let { data = data + it }
                  error = error + result.cause
               }
            }
         }
      }.onStart {
         _homeUiState.update { it.copy(isLoading = true) }
      }.onCompletion {
         _homeUiState.update {
            it.copy(
               isLoading = false,
               data = data,
               error = if (error.isNotEmpty()) error.first() else null
            )
         }
      }.launchIn(viewModelScope)
   }

   fun onRefresh() = fetchContents()

   fun onHomeErrorShown() = _homeUiState.update { it.copy(error = null) }
}