package com.syalim.themoviedb.utils


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
object Constants {

   // Remote constants
   const val BASE_URL = "https://api.themoviedb.org"
   const val IMAGE_URL = "https://image.tmdb.org/t/p"
   const val IMAGE_POSTER_THUMBNAIL_SIZE = "/w342"
   const val IMAGE_POSTER_DETAIL_SIZE = "/w500"
   const val IMAGE_POSTER_ORIGINAL_SIZE = "/original"
   const val IMAGE_BACKDROP_THUMBNAIL_SIZE = "/w780"
   const val IMAGE_BACKDROP_BIG_SIZE = "/w1280"
   const val IMAGE_PROFILE_THUMBNAIL_SIZE = "/w185"

   // Error Message
   const val NETWORK_ERROR_IO_EXCEPTION = "Couldn't reach server."
   const val NETWORK_ERROR_UNEXPECTED = "Unexpected network error."

   // Paging
   const val START_PAGE_INDEX = 1

}