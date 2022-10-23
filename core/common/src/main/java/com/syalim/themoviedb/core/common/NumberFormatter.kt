package com.syalim.themoviedb.core.common

import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*


/**
 * Created by Rahmat Syalim on 2022/08/18
 * rahmatsyalim@gmail.com
 */

object NumberFormatter {

   fun toStringDuration(runtime: Int?): String? {
      val hour: Int
      val minute: Int
      return if (runtime != null) {
         if (runtime >= 60) {
            hour = runtime.div(60)
            minute = runtime.minus(hour * 60)
            "${hour}h ${minute}m"
         } else {
            "${runtime}m"
         }
      } else null
   }

   fun toStringSimpleCount(count: Int): String {
      return if (count < 1_000) {
         count.toString()
      } else if (count >= 1_000_000) {
         count.simpleCount(1_000_000, "M")
      } else {
         count.simpleCount(1_000, "K")
      }
   }

   private fun Int.simpleCount(divider: Int, label: String): String {
      val parent = div(divider)
      val child = toDoubleOneDecimal(minus(parent * divider).toDouble().div(divider))
      return if (child > 0) {
         (parent + child).toString() + label
      } else {
         parent.toString() + label
      }
   }

   fun toDoubleOneDecimal(number: Double): Double = DecimalFormat("#.#").format(number).toDouble()

   private fun toOneDecimalRounded(number: Double): Double {
      val decimalFormat = DecimalFormat("#.#")
      decimalFormat.roundingMode = RoundingMode.CEILING
      return decimalFormat.format(number).toDouble()
   }

   fun toStringNominal(number: Int): String {
      return String.format(Locale.US, "%,d", number).replace(',', '.')
   }

   fun toIntPercent(number: Double): Int = (toOneDecimalRounded(number) * 10).toInt()

   fun toFloatRateOf5(number: Double): Float = toOneDecimalRounded(number.div(2)).toFloat()
}