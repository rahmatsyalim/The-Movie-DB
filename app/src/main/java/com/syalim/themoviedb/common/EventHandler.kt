package com.syalim.themoviedb.common


/**
 * Created by Rahmat Syalim on 2022/07/17
 * rahmatsyalim@gmail.com
 */
open class EventHandler<out T>(private val content: T) {
   var hasBeenHandled = false
      private set // Allow external read but not write
   fun getContentIfNotHandled(): T? {
      return if (hasBeenHandled) {
         null
      } else {
         hasBeenHandled = true
         content
      }
   }
   fun getContent(): T = content
}