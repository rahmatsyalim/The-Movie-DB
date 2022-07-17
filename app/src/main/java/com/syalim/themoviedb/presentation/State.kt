package com.syalim.themoviedb.presentation


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
data class State<T>(
   val isLoading: Boolean = false,
   val isReloading: Boolean = false,
   val errorMessage: String? = null,
   val data: T? = null
)