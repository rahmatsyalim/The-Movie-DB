package com.syalim.themoviedb.core.common

import androidx.annotation.StringRes


/**
 * Created by Rahmat Syalim on 2022/08/16
 * rahmatsyalim@gmail.com
 */

sealed interface AppException {
   data class StringMsg(override val message: String) : Exception(message)
   data class StringResMsg(@StringRes val messageRes: Int) : Exception()
}