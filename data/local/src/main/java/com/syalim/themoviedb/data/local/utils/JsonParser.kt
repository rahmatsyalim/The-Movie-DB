package com.syalim.themoviedb.data.local.utils

import java.lang.reflect.Type


/**
 * Created by Rahmat Syalim on 2022/08/28
 * rahmatsyalim@gmail.com
 */

interface JsonParser {

   fun <T> fromJson(json: String, type: Type): T?

   fun <T> toJson(obj: T, type: Type): String?
}