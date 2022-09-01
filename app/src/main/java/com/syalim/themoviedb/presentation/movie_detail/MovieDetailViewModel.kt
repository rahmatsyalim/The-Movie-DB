package com.syalim.themoviedb.presentation.movie_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syalim.themoviedb.domain.movie.usecase.movie_detail.GetMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
   private val getMovieDetailUseCase: GetMovieDetailUseCase
) : ViewModel() {

   private val _movieDetailUiState = MutableStateFlow(MovieDetailUiState())
   val movieDetailUiState: StateFlow<MovieDetailUiState> get() = _movieDetailUiState

   fun onFetchMovieDetail(id: String) =
      getMovieDetailUseCase.invoke(id)
         .onStart { _movieDetailUiState.update { it.copy(isInitial = false, isLoading = true) } }
         .onEach { result ->
            _movieDetailUiState.update {
               when (result) {
                  is com.syalim.themoviedb.core.common.Result.Success -> it.copy(data = result.data)
                  is com.syalim.themoviedb.core.common.Result.Failure -> it.copy(
                     error = result.cause,
                     data = result.data
                  )
               }
            }
         }
         .onCompletion { _movieDetailUiState.update { it.copy(isLoading = false) } }
         .launchIn(viewModelScope)


   fun onBookmark(id: String) {

   }

   fun onRemoveBookmark(id: String) {

   }

   fun onErrorShown() {
      _movieDetailUiState.update { it.copy(error = null) }
   }

}