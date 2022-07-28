package com.syalim.themoviedb.presentation


/**
 * Created by Rahmat Syalim on 2022/07/25
 * rahmatsyalim@gmail.com
 */
sealed class State<T>(val data: T? = null, val message: String? = null) {
   class Default<T> : State<T>()
   class FirstLoading<T> : State<T>()
   class Loading<T> : State<T>()
   class Error<T>(message: String?) : State<T>(message = message)
   class Empty<T> : State<T>()
   class Loaded<T>(data: T?) : State<T>(data = data)

   class Handle<T>(private val state: State<T>) {
      operator fun invoke(
         onFirstLoading: ((Boolean) -> Unit)? = null,
         onLoading: ((Boolean) -> Unit)? = null,
         onError: ((String) -> Unit)? = null,
         onEmpty: (() -> Unit)? = null,
         onLoaded: ((T) -> Unit)? = null
      ) {
         onFirstLoading?.invoke(state is FirstLoading)
         onLoading?.invoke(state is Loading)
         if (state is Error) onError?.invoke(state.message!!)
         if (state is Empty) onEmpty?.invoke()
         if (state is Loaded) onLoaded?.invoke(state.data!!)
      }
   }
}