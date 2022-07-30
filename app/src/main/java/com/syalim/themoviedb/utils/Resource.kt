package com.syalim.themoviedb.utils


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
sealed class Resource<T>(val data: T? = null, val message: String? = null, val status: Status?) {
   class Success<T>(data: T?) : Resource<T>(data = data, status = Status.SUCCESS)
   class Error<T>(message: String?) : Resource<T>(message = message, status = Status.ERROR)
   class Loading<T> : Resource<T>(status = Status.LOADING)

   fun handle(
      onSuccess: (T?) -> Unit,
      onError: (String?) -> Unit,
      onLoading: () -> Unit
   ){
      when(this){
         is Loading -> onLoading.invoke()
         is Error -> onError.invoke(message)
         is Success -> onSuccess.invoke(data)
      }
   }
}
