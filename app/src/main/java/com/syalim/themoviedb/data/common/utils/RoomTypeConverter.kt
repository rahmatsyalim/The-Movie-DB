package com.syalim.themoviedb.data.common.utils

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.util.*


/**
 * Created by Rahmat Syalim on 2022/08/19
 * rahmatsyalim@gmail.com
 */

@ProvidedTypeConverter
object RoomTypeConverter {

   @TypeConverter
   fun dateToTime(date: Date?): Long? {
      return date?.time
   }

   @TypeConverter
   fun timeToDate(time: Long?): Date? {
      return time?.let(::Date)
   }
}