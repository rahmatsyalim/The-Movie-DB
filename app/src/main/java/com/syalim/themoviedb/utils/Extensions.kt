package com.syalim.themoviedb.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import com.syalim.themoviedb.R
import com.syalim.themoviedb.utils.Constants.HTTP_ERROR_400
import com.syalim.themoviedb.utils.Constants.HTTP_ERROR_401
import com.syalim.themoviedb.utils.Constants.HTTP_ERROR_404
import com.syalim.themoviedb.utils.Constants.HTTP_ERROR_405
import com.syalim.themoviedb.utils.Constants.HTTP_ERROR_406
import com.syalim.themoviedb.utils.Constants.HTTP_ERROR_422
import com.syalim.themoviedb.utils.Constants.HTTP_ERROR_500
import com.syalim.themoviedb.utils.Constants.HTTP_ERROR_501
import com.syalim.themoviedb.utils.Constants.HTTP_ERROR_502
import com.syalim.themoviedb.utils.Constants.HTTP_ERROR_503
import com.syalim.themoviedb.utils.Constants.HTTP_ERROR_504
import com.syalim.themoviedb.utils.Constants.HTTP_ERROR_UNEXPECTED
import com.syalim.themoviedb.utils.Constants.IMAGE_BACKDROP_BIG_SIZE
import com.syalim.themoviedb.utils.Constants.IMAGE_URL
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
   val font = ResourcesCompat.getFont(context, R.font.poppins_regular)
   Snackbar.make(
      this,
      message,
      if (indefinite) Snackbar.LENGTH_INDEFINITE else Snackbar.LENGTH_LONG
   ).apply {
      if (indefinite) setAction("Dismiss") {
         dismiss()
      }
      view.setBackgroundColor(resources.getColor(R.color.black, context.theme))
      val text = view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
      text.textSize = 12f
      text.typeface = font
      val action = view.findViewById<TextView>(com.google.android.material.R.id.snackbar_action)
      action.textSize = 13f
      action.setTextColor(resources.getColor(R.color.primary, context.theme))
      action.typeface = font
   }.show()
}

fun ImageView.loadImage(imagePath: String?, imageSize: String) {
   Glide
      .with(context)
      .load(imagePath.setImageUrl(imageSize))
      .diskCacheStrategy(DiskCacheStrategy.ALL)
      .error(ResourcesCompat.getDrawable(resources,R.drawable.placeholder_image, context.theme))
      .placeholder(ResourcesCompat.getDrawable(resources,R.drawable.bg_image_ph, context.theme))
      .into(this)
}

fun ImageView.loadProfileImage(imagePath: String?, imageSize: String) {
   Glide
      .with(context)
      .load(imagePath.setImageUrl(imageSize))
      .diskCacheStrategy(DiskCacheStrategy.ALL)
      .error(ResourcesCompat.getDrawable(resources,R.drawable.placeholder_avatar, context.theme))
      .placeholder(ResourcesCompat.getDrawable(resources,R.drawable.bg_image_ph, context.theme))
      .into(this)
}

fun ImageView.loadBackgroundImage(imagePath: String?) {
   Glide
      .with(context)
      .asBitmap()
      .load(imagePath.setImageUrl(IMAGE_BACKDROP_BIG_SIZE))
      .diskCacheStrategy(DiskCacheStrategy.ALL)
      .into(this)
}

fun String?.setImageUrl(imageSize: String): String? {
   return if (isNullOrBlank()) {
      null
   } else {
      IMAGE_URL + imageSize + this
   }
}

fun HttpException.getErrorMessage(): String {
   return when (code()) {
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
      else -> HTTP_ERROR_UNEXPECTED
   }
}

fun String?.dateToViewDate(): String {
   return if (equals("0000-00-00") || isNullOrBlank()) {
      "Unknown"
   } else {
      val values = split("-".toRegex()).toTypedArray()
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
   return if (equals("0000-00-00") || isNullOrBlank()) {
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
   return if (length == 1) {
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
   return (roundOneDecimal() * 10).toInt()
}