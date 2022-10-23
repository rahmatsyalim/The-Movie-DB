package com.syalim.themoviedb.feature.tvshows.ui

import com.syalim.themoviedb.feature.tvshows.domain.model.TvShowsCategorized


/**
 * Created by Rahmat Syalim on 2022/09/06
 * rahmatsyalim@gmail.com
 */

data class TvShowsUiState(
   val isDefault: Boolean = true,
   val isLoading: Boolean = false,
   val data: List<TvShowsCategorized>? = null,
   val error: Throwable? = null
) {
   inline fun onFinish(
      success: List<TvShowsCategorized>.() -> Unit,
      failure: Throwable.() -> Unit
   ) {
      if (!isLoading) {
         if (data != null) success.invoke(data)
         if (error != null) failure.invoke(error)
      }
   }
}
