package com.syalim.themoviedb.common


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
object Constants {

   // Remote constants
   const val BASE_URL = "https://api.themoviedb.org"
   const val IMAGE_URL = "https://image.tmdb.org/t/p/w500/"

   // Error Message
   const val HTTP_ERROR_401 = "Invalid API key"
   const val HTTP_ERROR_404 = "Requested resource not found"
   const val HTTP_ERROR_500 = "Something went wrong on the server"
   const val HTTP_ERROR = "Unexpected network error"

   // Paging
   const val START_PAGE_INDEX = 1

}