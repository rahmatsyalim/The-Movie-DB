package com.syalim.themoviedb.presentation.common.extensions

import com.syalim.themoviedb.utils.Constants.IMAGE_URL


/**
 * Created by Rahmat Syalim on 2022/07/31
 * rahmatsyalim@gmail.com
 */

fun String?.setImageUrl(imageSize: String): String? {
   return if (isNullOrBlank()) {
      null
   } else {
      IMAGE_URL + imageSize + this
   }
}

fun String?.dateToViewDate(): String {
   return if (equals("0000-00-00") || isNullOrBlank()) {
      ""
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
      val day = if (values[2].length == 1) "0${values[2]}" else values[2]
      "$month $day, $year"
   }
}

fun String?.dateGetYear(): String {
   return if (equals("0000-00-00") || isNullOrBlank()) {
      ""
   } else {
      val values = this.split("-".toRegex()).toTypedArray()
      values[0]
   }
}