package com.syalim.themoviedb.feature.bookmarks.ui


/**
 * Created by Rahmat Syalim on 2022/09/03
 * rahmatsyalim@gmail.com
 */

class BookmarksUiState(
   val isDefault: Boolean = true,
   val isLoading: Boolean = false,
   val error: Throwable? = null,
   val data: Any? = null
) {
   inline fun onFinish(
      success: Any.() -> Unit,
      failure: Throwable.() -> Unit
   ) {
      if (!isLoading) {
         if (data != null) success.invoke(data)
         if (error != null) failure.invoke(error)
      }
   }
}