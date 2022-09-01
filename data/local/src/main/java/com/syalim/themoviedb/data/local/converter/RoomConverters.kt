package com.syalim.themoviedb.data.local.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.syalim.themoviedb.data.local.utils.JsonParser
import java.util.*


/**
 * Created by Rahmat Syalim on 2022/08/19
 * rahmatsyalim@gmail.com
 */

@ProvidedTypeConverter
class RoomConverters(
   private val jsonParser: JsonParser
) {
   @TypeConverter
   fun fromDateToTime(date: Date?): Long? {
      return date?.time
   }

   @TypeConverter
   fun fromTimeToDate(time: Long?): Date? {
      return time?.let(::Date)
   }

//   @TypeConverter
//   fun fromMoviesToJson(movies: List<MovieDto>): String {
//      return jsonParser.toJson(
//         obj = movies,
//         type = object : TypeToken<ArrayList<MovieDto>>() {}.type
//      ) ?: "[]"
//   }
//
//   @TypeConverter
//   fun fromJsonToMovies(json: String): List<MovieDto> {
//      return jsonParser.fromJson<ArrayList<MovieDto>>(
//         json = json,
//         type = object : TypeToken<ArrayList<MovieDto>>() {}.type
//      ) ?: emptyList()
//   }
}