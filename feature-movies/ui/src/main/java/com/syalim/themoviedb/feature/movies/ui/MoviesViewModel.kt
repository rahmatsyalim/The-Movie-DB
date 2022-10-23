package com.syalim.themoviedb.feature.movies.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syalim.themoviedb.feature.movies.domain.utils.MovieCategory
import com.syalim.themoviedb.core.common.Result
import com.syalim.themoviedb.feature.movies.domain.model.MoviesCategorized
import com.syalim.themoviedb.feature.movies.domain.usecase.GetMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * Created by Rahmat Syalim on 2022/08/28
 * rahmatsyalim@gmail.com
 */

@HiltViewModel
class MoviesViewModel @Inject constructor(
   private val getMovies: GetMovies
) : ViewModel() {

   private val _moviesUiState = MutableStateFlow(MoviesUiState())
   val moviesUiState get() = _moviesUiState.asStateFlow()

   init {
      fetchContents()
   }

   var carouselPosition = 0

   private fun fetchContents() {
      var data = emptyList<MoviesCategorized>()
      var error = emptyList<Throwable>()
      combine(
         getMovies.invoke(MovieCategory.IN_THEATER),
         getMovies.invoke(MovieCategory.UPCOMING),
         getMovies.invoke(MovieCategory.POPULAR),
         getMovies.invoke(MovieCategory.TOP_RATED)
      ) { a, b, c, d ->
         listOf(a, b, c, d).forEach { result ->
            when (result) {
               is Result.Success -> data = data + result.data
               is Result.Failure -> {
                  result.data?.let { data = data + it }
                  error = error + result.cause
               }
            }
         }
      }.onStart {
         _moviesUiState.update { it.copy(isLoading = true) }
      }.onCompletion {
         _moviesUiState.update {
            it.copy(
               isDefault = false,
               isLoading = false,
               data = data,
               error = if (error.isNotEmpty()) error.first() else null
            )
         }
      }.launchIn(viewModelScope)
   }

   fun onRefresh() = fetchContents()

   fun onErrorShown() = _moviesUiState.update { it.copy(error = null) }

}