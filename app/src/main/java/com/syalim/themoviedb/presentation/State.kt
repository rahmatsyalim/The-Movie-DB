package com.syalim.themoviedb.presentation


/**
 * Created by Rahmat Syalim on 2022/07/25
 * rahmatsyalim@gmail.com
 */
data class State<T>(
   val isFirstLoading: Boolean = false,
   val isLoading: Boolean = false,
   val errorMsg: String? = null,
   val isEmpty: Boolean = false,
   val data: T? = null
) {
   class Handle<T>(private val state: State<T>) {
      operator fun invoke(
         onFirstLoading: ((Boolean) -> Unit)? = null,
         onLoading: ((Boolean) -> Unit)? = null,
         onError: ((String?) -> Unit)? = null,
         onEmpty: (() -> Unit)? = null,
         onSuccess: ((T) -> Unit)? = null
      ) {
         onFirstLoading?.invoke(state.isFirstLoading)
         onLoading?.invoke(state.isLoading)
         state.errorMsg?.let {
            onError?.invoke(it)
         }
         if (state.isEmpty) onEmpty?.invoke()
         state.data?.let { onSuccess?.invoke(it) }
      }
   }
}