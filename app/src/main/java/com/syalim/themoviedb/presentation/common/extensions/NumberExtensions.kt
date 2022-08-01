package com.syalim.themoviedb.presentation.common.extensions

import java.math.RoundingMode
import java.text.DecimalFormat


/**
 * Created by Rahmat Syalim on 2022/07/31
 * rahmatsyalim@gmail.com
 */

fun Number?.convertToTimeDuration(): String {
   var hour: Int
   var minute: Int
   var result = "0m"
   this?.toInt()?.let {
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

fun Number.roundOneDecimal(): Double {
   val decimalFormat = DecimalFormat("#.#")
   decimalFormat.roundingMode = RoundingMode.CEILING
   return decimalFormat.format(this).toDouble()
}

fun Number.toPercentInt(): Int {
   return (roundOneDecimal() * 10).toInt()
}