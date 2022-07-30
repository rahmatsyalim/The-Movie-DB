package com.syalim.themoviedb.presentation.common


/**
 * Created by Rahmat Syalim on 2022/07/25
 * rahmatsyalim@gmail.com
 */
sealed class State<T>(val data: T? = null, val message: String? = null, val isFirstLoading: Boolean = false) {
   class Loading<T>(isFirstLoading: Boolean) : State<T>(isFirstLoading = isFirstLoading)
   class Error<T>(message: String?) : State<T>(message = message)
   class Loaded<T>(data: T? = null) : State<T>(data = data)
   class Default<T> : State<T>()

   fun handle(
      onFirstLoading: ((Boolean) -> Unit)? = null,
      onLoading: ((Boolean) -> Unit)? = null,
      onError: ((String) -> Unit)? = null,
      onEmpty: (() -> Unit)? = null,
      onLoaded: ((T) -> Unit)? = null
   ){
      onFirstLoading?.invoke(this is Loading && this.isFirstLoading)
      onLoading?.invoke(this is Loading && !this.isFirstLoading)
      if (this is Error) onError?.invoke(this.message!!)
      if (this is Loaded) {
         if(this.data == null || this.data == emptyList<T>()){
            onEmpty?.invoke()
         } else {
            onLoaded?.invoke(this.data)
         }
      }
   }
}