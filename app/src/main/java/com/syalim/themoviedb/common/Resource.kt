package com.syalim.themoviedb.common


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
sealed class Resource<T>(val data: T? = null, val message: String? = null, val status: Status?) {
   class Success<T>(data: T?) : Resource<T>(data = data, status = Status.SUCCESS)
   class Error<T>(message: String?) : Resource<T>(message = message, status = Status.ERROR)
   class Loading<T> : Resource<T>(status = Status.LOADING)

   class Handle<T>(private val resource: Resource<T>) {
      operator fun invoke(
         onSuccess: (T?) -> Unit,
         onError: (String?) -> Unit,
         onLoading: (Boolean) -> Unit
      ) {
         onLoading(resource is Loading)
         onError(resource.message)
         if (resource is Success) onSuccess(resource.data)
      }
   }
}
