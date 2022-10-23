package com.syalim.themoviedb.feature.movies.ui

import com.syalim.themoviedb.feature.movies.domain.model.MoviesCategorized

/**
 * Created by Rahmat Syalim on 2022/08/08
 * rahmatsyalim@gmail.com
 */

data class MoviesUiState(
   val isDefault: Boolean = true,
   val isLoading: Boolean = false,
   val data: List<MoviesCategorized>? = null,
   val error: Throwable? = null
) {
   inline fun onFinish(
      success: List<MoviesCategorized>.() -> Unit,
      failure: Throwable.() -> Unit
   ) {
      if (!isLoading) {
         if (data != null) success.invoke(data)
         if (error != null) failure.invoke(error)
      }
   }
}