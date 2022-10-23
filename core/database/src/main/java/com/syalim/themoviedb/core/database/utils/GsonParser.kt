package com.syalim.themoviedb.core.database.utils

import com.google.gson.Gson
import java.lang.reflect.Type


/**
 * Created by Rahmat Syalim on 2022/08/28
 * rahmatsyalim@gmail.com
 */

internal class GsonParser constructor(
   private val gson: Gson
) : JsonParser {
   override fun <T> fromJson(json: String, type: Type): T? {
      return gson.fromJson(json, type)
   }

   override fun <T> toJson(obj: T, type: Type): String? {
      return gson.toJson(obj, type)
   }

}