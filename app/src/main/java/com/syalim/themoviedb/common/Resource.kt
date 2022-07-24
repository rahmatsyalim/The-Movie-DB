package com.syalim.themoviedb.common


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
sealed class Resource<T>(
   val data: T? = null,
   val message: String? = null,
   val status: Status? = null
) {
   class Success<T>(data: T?) : Resource<T>(data = data, status = Status.SUCCESS)
   class Error<T>(message: String?) : Resource<T>(message = message, status = Status.ERROR)
   class Loading<T> : Resource<T>(status = Status.LOADING)
}
