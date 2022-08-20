package com.syalim.themoviedb.common

import androidx.annotation.StringRes


/**
 * Created by Rahmat Syalim on 2022/08/16
 * rahmatsyalim@gmail.com
 */

sealed class AppExceptions {
   class Http(override val message: String) : Exception(message)
   class IoOperation(@StringRes val messageResId: Int, message: String?) : Exception(message)
   class DbOperationFailed(override val message: String): Exception(message)
}