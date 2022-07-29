package com.syalim.themoviedb.presentation


/**
 * Created by Rahmat Syalim on 2022/07/25
 * rahmatsyalim@gmail.com
 */
sealed class State<T>(val data: T? = null, val message: String? = null, val isFirstLoading: Boolean = false) {
   class Loading<T>(isFirstLoading: Boolean) : State<T>(isFirstLoading = isFirstLoading)
   class Error<T>(message: String?) : State<T>(message = message)
   class Loaded<T>(data: T? = null) : State<T>(data = data)

   class Default<T> : State<T>()

   class Handle<T>(private val state: State<T>) {
      operator fun invoke(
         onFirstLoading: ((Boolean) -> Unit)? = null,
         onLoading: ((Boolean) -> Unit)? = null,
         onError: ((String) -> Unit)? = null,
         onEmpty: (() -> Unit)? = null,
         onLoaded: ((T) -> Unit)? = null
      ) {
         onFirstLoading?.invoke(state is Loading && state.isFirstLoading)
         onLoading?.invoke(state is Loading && !state.isFirstLoading)
         if (state is Error) onError?.invoke(state.message!!)
         if (state is Loaded) {
            if(state.data == null || state.data == emptyList<T>()){
               onEmpty?.invoke()
            } else {
               onLoaded?.invoke(state.data)
            }
         }
      }
   }
}