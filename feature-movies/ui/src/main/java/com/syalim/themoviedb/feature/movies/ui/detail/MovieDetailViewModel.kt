package com.syalim.themoviedb.feature.movies.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syalim.themoviedb.core.common.Result
import com.syalim.themoviedb.feature.movies.domain.usecase.detail.GetMovieDetails
import com.syalim.themoviedb.feature.movies.domain.usecase.detail.IsMovieBookmarked
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
   private val getMovieDetails: GetMovieDetails,
   private val isMovieBookmarked: IsMovieBookmarked
) : ViewModel() {

   private val _movieDetailUiState = MutableStateFlow(MovieDetailUiState())
   val movieDetailUiState: StateFlow<MovieDetailUiState> get() = _movieDetailUiState

   fun onFetchMovieDetail(id: String) =
      getMovieDetails.invoke(id)
         .onStart { _movieDetailUiState.update { it.copy(isDefault = false, isLoading = true) } }
         .onEach { result ->
            _movieDetailUiState.update {
               when (result) {
                  is Result.Success -> it.copy(data = result.data)
                  is Result.Failure -> it.copy(
                     error = result.cause,
                     data = result.data
                  )
               }
            }
         }
         .onCompletion {
            val bookmarked = isMovieBookmarked.invoke(id)
            _movieDetailUiState.update { it.copy(isLoading = false, isBookmarked = bookmarked) }
         }
         .launchIn(viewModelScope)


   fun onBookmark(id: String) {

   }

   fun onRemoveBookmark(id: String) {

   }

   fun onErrorShown() {
      _movieDetailUiState.update { it.copy(error = null) }
   }

}