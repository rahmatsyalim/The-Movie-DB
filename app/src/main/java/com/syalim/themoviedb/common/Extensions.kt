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

fun String?.dateToViewDate(): String {
   if (this == "0000-00-00" || this == null || this == "") {
      return "Unknown"
   } else {
      val values = this.split("-".toRegex()).toTypedArray()
      val year = values[0]
      val month = when (values[1]) {
         "01" -> "Jan"
         "02" -> "Feb"
         "03" -> "Mar"
         "04" -> "Apr"
         "05" -> "May"
         "06" -> "Jun"
         "07" -> "Jul"
         "08" -> "Aug"
         "09" -> "Sep"
         "10" -> "Oct"
         "11" -> "Nov"
         else -> "Des"
      }
      val day = when (values[2]) {
         "01" -> "1"
         "02" -> "2"
         "03" -> "3"
         "04" -> "4"
         "05" -> "5"
         "06" -> "6"
         "07" -> "7"
         "08" -> "8"
         "09" -> "9"
         else -> values[2]
      }
      return "$month $day, $year"
   }
}