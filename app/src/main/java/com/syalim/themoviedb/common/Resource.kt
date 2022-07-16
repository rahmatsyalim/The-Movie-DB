package com.syalim.themoviedb.common


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
   class Success<T>(data: T?) : Resource<T>(data)
   class Error<T>(message: String?, data: T? = null) : Resource<T>(data, message)
   class Loading<T>(isLoading: Boolean = true) : Resource<T>(null)
}
