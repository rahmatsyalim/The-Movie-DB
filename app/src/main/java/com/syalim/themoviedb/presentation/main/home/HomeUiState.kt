package com.syalim.themoviedb.presentation.main.home

/**
 * Created by Rahmat Syalim on 2022/08/08
 * rahmatsyalim@gmail.com
 */

data class HomeUiState(
   val isLoading: Boolean = false,
   val data: List<com.syalim.themoviedb.domain.movie.model.Movies>? = null,
   val error: Throwable? = null
) {
   inline fun onFinish(
      success: List<com.syalim.themoviedb.domain.movie.model.Movies>.() -> Unit,
      failure: Throwable.() -> Unit
   ) {
      if (!isLoading) {
         if (data != null) success.invoke(data)
         if (error != null) failure.invoke(error)
      }
   }
}