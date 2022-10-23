package com.syalim.themoviedb.core.common

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Rahmat Syalim on 2022/08/19
 * rahmatsyalim@gmail.com
 */

object DateFormatter {

   private val formatter get() = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

   private val monthName = listOf(
      "January", "February", "March", "April", "May", "June", "July",
      "August", "September", "October", "November", "December"
   )
   private val shortMonthName = listOf(
      "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
      "Aug", "Sep", "Oct", "Nov", "Dec"
   )

   private fun String?.stringDateIsValid(): Boolean {
      return if (isNullOrBlank() || this == "0000-00-00") {
         false
      } else {
         try {
            formatter.parse(this)
            true
         } catch (e: ParseException) {
            false
         }
      }
   }

   fun stringDateToDate(string: String?): Date? {
      return if (string.stringDateIsValid()) {
         formatter.parse(string!!)
      } else {
         null
      }
   }

   fun dateYear(date: Date?): String? {
      return date?.let {
         formatter.format(it).split("-")[0]
      }
   }

   fun stringDateYear(string: String?): String? {
      return if (string.stringDateIsValid()) {
         string?.let { it.split("-")[0] }
      } else {
         null
      }
   }

   fun dateToStringDisplay(date: Date?): String? {
      return date?.let {
         val stringDate = formatter.format(it)
         stringDateToStringDisplay(stringDate)
      }
   }

   fun stringDateToStringDisplay(string: String?): String? {
      return if (string.stringDateIsValid()) {
         val dateSplit = string!!.split("-").map { it.toInt() }
         val year = dateSplit[0]
         val month = shortMonthName[dateSplit[1] - 1]
         val day = dateSplit[2].toString() +
            if (dateSplit[2] in 11..13) {
               "th"
            } else {
               when (dateSplit[2] % 10) {
                  1 -> "st"
                  2 -> "nd"
                  3 -> "rd"
                  else -> "th"
               }
            }
         "$month $day $year"
      } else {
         null
      }
   }
}