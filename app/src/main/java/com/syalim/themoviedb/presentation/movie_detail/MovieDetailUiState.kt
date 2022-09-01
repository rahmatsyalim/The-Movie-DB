package com.syalim.themoviedb.presentation.movie_detail


/**
 * Created by Rahmat Syalim on 2022/08/23
 * rahmatsyalim@gmail.com
 */

data class MovieDetailUiState(
   val isInitial: Boolean = true,
   val isLoading: Boolean = false,
   val data: com.syalim.themoviedb.domain.movie.model.MovieDetail? = null,
   val isBookmarked: Boolean = false,
   val error: Throwable? = null
) {
   inline fun onInitial(block: () -> Unit) {
      if (isInitial) block.invoke()
   }

   inline fun onFinish(
      onSuccess: com.syalim.themoviedb.domain.movie.model.MovieDetail.() -> Unit,
      onFailure: Throwable.() -> Unit
   ) {
      if (!isLoading) {
         if (data != null) onSuccess.invoke(data)
         if (error != null) onFailure.invoke(error)
      }
   }
}
