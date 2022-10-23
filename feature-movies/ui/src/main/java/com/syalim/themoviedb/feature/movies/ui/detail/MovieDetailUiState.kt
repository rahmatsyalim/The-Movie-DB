package com.syalim.themoviedb.feature.movies.ui.detail

import com.syalim.themoviedb.feature.movies.domain.model.MovieDetails


/**
 * Created by Rahmat Syalim on 2022/08/23
 * rahmatsyalim@gmail.com
 */

data class MovieDetailUiState(
   val isDefault: Boolean = true,
   val isLoading: Boolean = false,
   val data: MovieDetails? = null,
   val isBookmarked: Boolean = false,
   val error: Throwable? = null
) {

   inline fun onFinish(
      onSuccess: MovieDetails.() -> Unit,
      onFailure: Throwable.() -> Unit
   ) {
      if (!isLoading) {
         if (data != null) onSuccess.invoke(data)
         if (error != null) onFailure.invoke(error)
      }
   }
}
