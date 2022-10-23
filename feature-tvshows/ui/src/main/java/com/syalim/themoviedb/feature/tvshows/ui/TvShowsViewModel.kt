package com.syalim.themoviedb.feature.tvshows.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syalim.themoviedb.core.common.Result
import com.syalim.themoviedb.feature.tvshows.domain.utils.TvShowCategory
import com.syalim.themoviedb.feature.tvshows.domain.model.TvShowsCategorized
import com.syalim.themoviedb.feature.tvshows.domain.usecase.GetTvShows
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/09/06
 * rahmatsyalim@gmail.com
 */

@HiltViewModel
class TvShowsViewModel @Inject constructor(
   private val getTvShows: GetTvShows
) : ViewModel() {

   private val _tvShowsUiState = MutableStateFlow(TvShowsUiState())
   val tvShowsUiState get() = _tvShowsUiState.asStateFlow()

   init {
      fetchContents()
   }

   var carouselPosition = 0

   private fun fetchContents() {
      var data = emptyList<TvShowsCategorized>()
      var error = emptyList<Throwable>()
      combine(
         getTvShows.invoke(TvShowCategory.ON_THE_AIR),
         getTvShows.invoke(TvShowCategory.AIRING_TODAY),
         getTvShows.invoke(TvShowCategory.POPULAR),
         getTvShows.invoke(TvShowCategory.TOP_RATED),
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
         _tvShowsUiState.update { it.copy(isLoading = true) }
      }.onCompletion {
         _tvShowsUiState.update {
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

   fun onErrorShown() = _tvShowsUiState.update { it.copy(error = null) }

}