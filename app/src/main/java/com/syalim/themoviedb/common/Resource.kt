package com.syalim.themoviedb.common

/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */

sealed interface Resource<out T> {
   data class Success<T>(val data: T, val empty: Boolean = false) : Resource<T>
   data class Error(val cause: Throwable) : Resource<Nothing>
   data class Loading<T>(val data: T? = null, val empty: Boolean = false) : Resource<T>
}