package com.syalim.themoviedb.presentation.common.utils

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.syalim.themoviedb.R
import com.syalim.themoviedb.common.AppExceptions
import com.syalim.themoviedb.common.ConnectivityStatus


/**
 * Created by Rahmat Syalim on 2022/08/16
 * rahmatsyalim@gmail.com
 */

fun Throwable.getThrownMessage(context: Context): String {
   return when (this) {
      is AppExceptions.Http -> message
      is AppExceptions.IoOperation -> context.getString(messageResId)
      else -> message ?: context.getString(R.string.error_unexpected)
   }
}

fun Context.getConnectivityStateMessage(connectivityStatus: ConnectivityStatus): String? {
   return when (connectivityStatus) {
      ConnectivityStatus.Available -> null
      else -> getString(R.string.no_internet_con)
   }
}

fun Context.showLongToast(message: String) {
   Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showShortToast(message: String) {
   Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.showSnackBar(message: String, anchor: View? = null) {
   Snackbar.make(rootView, message, Snackbar.LENGTH_LONG)
      .setAnchorView(anchor)
      .apply {
         view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
            textSize = 12f
            typeface = context.fontNormal
         }
      }
      .show()
}

fun View.showSnackBarWithAction(message: String, actionText: String, anchor: View? = null, block: Snackbar.() -> Unit) {
   Snackbar.make(rootView, message, Snackbar.LENGTH_INDEFINITE)
      .setAnchorView(anchor)
      .apply {
         setAction(actionText) {
            block.invoke(this)
         }
         view.apply {
            setBackgroundColor(context.getColorFrom(R.color.brand_dark))
            findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
               textSize = 12f
               typeface = context.fontNormal
            }
            findViewById<TextView>(com.google.android.material.R.id.snackbar_action).apply {
               textSize = 13f
               setTextColor(context.getColorFrom(R.color.brand_aqua))
               typeface = context.fontBold
            }
         }
      }
      .show()
}