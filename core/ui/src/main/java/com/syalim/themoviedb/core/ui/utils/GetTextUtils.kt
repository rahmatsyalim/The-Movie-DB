package com.syalim.themoviedb.core.ui.utils

import android.content.Context
import com.syalim.themoviedb.core.common.AppException
import com.syalim.themoviedb.core.ui.R


/**
 * Created by Rahmat Syalim on 2022/09/05
 * rahmatsyalim@gmail.com
 */

fun Throwable.getThrownMessage(context: Context): String {
   return when (this) {
      is AppException.StringResMsg -> context.getString(messageRes)
      is AppException.StringMsg -> message
      else -> context.getString(R.string.unexpected_error)
   }
}