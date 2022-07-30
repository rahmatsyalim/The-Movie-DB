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
   const val ERROR_NO_CONNECTION = "Couldn't reach server, please check your internet connection"
   const val HTTP_ERROR_400 = "Validation failed."
   const val HTTP_ERROR_401 = "Authentication failed: You do not have permissions to access the service."
   const val HTTP_ERROR_404 = "The resource you requested could not be found."
   const val HTTP_ERROR_405 = "This request method is not supported for this resource."
   const val HTTP_ERROR_406 = "Invalid accept header."
   const val HTTP_ERROR_422 = "Invalid parameters: Your request parameters are incorrect."
   const val HTTP_ERROR_500 = "Internal error: Something went wrong, contact TMDB."
   const val HTTP_ERROR_501 = "Invalid service: this service does not exist."
   const val HTTP_ERROR_502 = "Couldn't connect to the backend server."
   const val HTTP_ERROR_503 = "Service offline: This service is temporarily offline, try again later."
   const val HTTP_ERROR_504 = "Your request to the backend server timed out. Try again."
   const val HTTP_ERROR_UNEXPECTED = "Unexpected network error"

   // Paging
   const val START_PAGE_INDEX = 1

}