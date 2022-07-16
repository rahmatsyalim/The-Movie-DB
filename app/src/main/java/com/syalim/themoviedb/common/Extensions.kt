package com.syalim.themoviedb.common

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */

fun Context.showToast(message: String) {
   Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun View.showSnackBar(message: String, indefinite: Boolean) {
   Snackbar.make(
      this,
      message,
      if (indefinite) Snackbar.LENGTH_INDEFINITE else Snackbar.LENGTH_LONG
   ).apply {
      if (indefinite) setAction("OK"){
         this.dismiss()
      }
   }.show()
}