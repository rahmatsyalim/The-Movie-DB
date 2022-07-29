package com.syalim.themoviedb.common

import android.app.Activity
import android.content.Context
import android.graphics.Insets
import android.os.Build
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowInsets
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import com.syalim.themoviedb.R
import com.syalim.themoviedb.common.Constants.HTTP_ERROR_400
import com.syalim.themoviedb.common.Constants.HTTP_ERROR_401
import com.syalim.themoviedb.common.Constants.HTTP_ERROR_404
import com.syalim.themoviedb.common.Constants.HTTP_ERROR_405
import com.syalim.themoviedb.common.Constants.HTTP_ERROR_406
import com.syalim.themoviedb.common.Constants.HTTP_ERROR_422
import com.syalim.themoviedb.common.Constants.HTTP_ERROR_500
import com.syalim.themoviedb.common.Constants.HTTP_ERROR_501
import com.syalim.themoviedb.common.Constants.HTTP_ERROR_502
import com.syalim.themoviedb.common.Constants.HTTP_ERROR_503
import com.syalim.themoviedb.common.Constants.HTTP_ERROR_504
import com.syalim.themoviedb.common.Constants.HTTP_ERROR_ELSE
import com.syalim.themoviedb.common.Constants.IMAGE_BACKDROP_BIG_SIZE
import com.syalim.themoviedb.common.Constants.IMAGE_URL
import retrofit2.HttpException
import java.math.RoundingMode
import java.text.DecimalFormat


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

fun ImageView.loadImage(uri: String, imageSize: String) {
   Glide
      .with(context)
      .load(uri.setImageUrl(imageSize))
      .diskCacheStrategy(DiskCacheStrategy.ALL)
      .error(resources.getDrawable(R.drawable.placeholder_image, context.theme))
      .placeholder(resources.getDrawable(R.drawable.bg_image_ph, context.theme))
      .into(this)
}

fun ImageView.loadProfileImage(uri: String, imageSize: String) {
   Glide
      .with(context)
      .load(uri.setImageUrl(imageSize))
      .diskCacheStrategy(DiskCacheStrategy.ALL)
      .error(resources.getDrawable(R.drawable.placeholder_avatar, context.theme))
      .placeholder(resources.getDrawable(R.drawable.bg_image_ph, context.theme))
      .into(this)
}

fun ImageView.loadBackgroundImage(uri: String) {
   Glide
      .with(context)
      .load(uri.setImageUrl(IMAGE_BACKDROP_BIG_SIZE))
      .diskCacheStrategy(DiskCacheStrategy.ALL)
      .into(this)
}

fun String.setImageUrl(imageSize: String): String {
   return IMAGE_URL + imageSize + this
}

fun HttpException.getErrorMessage(): String {
   return when (this.code()) {
      400 -> HTTP_ERROR_400
      401 -> HTTP_ERROR_401
      404 -> HTTP_ERROR_404
      405 -> HTTP_ERROR_405
      406 -> HTTP_ERROR_406
      422 -> HTTP_ERROR_422
      500 -> HTTP_ERROR_500
      501 -> HTTP_ERROR_501
      502 -> HTTP_ERROR_502
      503 -> HTTP_ERROR_503
      504 -> HTTP_ERROR_504
      else -> HTTP_ERROR_ELSE
   }
}

fun String?.dateToViewDate(): String {
   return if (this == "0000-00-00" || this.isNullOrBlank()) {
      "Unknown"
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
      val day = values[2].addZeroInFront()
      "$month $day, $year"
   }
}

fun String?.dateGetYear(): String {
   return if (this == "0000-00-00" || this.isNullOrBlank()) {
      "Unknown"
   } else {
      val values = this.split("-".toRegex()).toTypedArray()
      values[0]
   }
}

fun Int?.convertToTimeDuration(): String {
   var hour: Int
   var minute: Int
   var result = "Unknown Duration"
   this?.let {
      if (it >= 60) {
         hour = it / 60
         minute = it - hour * 60
         result = "${hour}h ${minute}m"
      } else {
         minute = it
         result = "${minute}m"
      }
   }
   return result
}

fun String.addZeroInFront(): String {
   return if (this.length == 1) {
      "0$this"
   } else {
      this
   }
}

fun Number.roundOneDecimal(): Double {
   val decimalFormat = DecimalFormat("#.#")
   decimalFormat.roundingMode = RoundingMode.CEILING
   return decimalFormat.format(this).toDouble()
}

fun Number.toPercentInt(): Int {
   return (this.roundOneDecimal() * 10).toInt()
}

fun Activity.getScreenHeight(): Int {
   return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      val windowMetrics = this.windowManager.currentWindowMetrics
      val insets: Insets = windowMetrics.windowInsets
         .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
      windowMetrics.bounds.height() - insets.bottom - insets.top
   } else {
      val displayMetrics = DisplayMetrics()
      this.windowManager.defaultDisplay.getMetrics(displayMetrics)
      displayMetrics.heightPixels
   }
}