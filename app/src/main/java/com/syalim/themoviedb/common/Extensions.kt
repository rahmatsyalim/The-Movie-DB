package com.syalim.themoviedb.common

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.syalim.themoviedb.common.Constants.HTTP_ERROR_401
import com.syalim.themoviedb.common.Constants.HTTP_ERROR_404
import com.syalim.themoviedb.common.Constants.HTTP_ERROR_500
import com.syalim.themoviedb.common.Constants.HTTP_ERROR
import com.syalim.themoviedb.common.Constants.IMAGE_URL
import retrofit2.HttpException


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
      if (indefinite) setAction("OK") {
         this.dismiss()
      }
   }.show()
}

fun ImageView.setImage(uri: String) {
   Glide.with(context)
      .load(uri)
      .centerCrop()
      .into(this)
}

fun String.setImageUrl(): String {
   return IMAGE_URL + this
}

fun HttpException.getErrorMessage(): String {
   return when (this.code()) {
      401 -> HTTP_ERROR_401
      404 -> HTTP_ERROR_404
      500 -> HTTP_ERROR_500
      else -> HTTP_ERROR
   }
}