package com.syalim.themoviedb.presentation.common


/**
 * Created by Rahmat Syalim on 2022/08/10
 * rahmatsyalim@gmail.com
 */

sealed interface ContentState<out T> {
   data class Success<T>(val data: T, val empty: Boolean) : ContentState<T>
   data class Error(val cause: Throwable) : ContentState<Nothing>
   data class Loading(val refresh: Boolean = false) : ContentState<Nothing>
   data class Cache<T>(val data: T? = null, val empty: Boolean) : ContentState<T>
}