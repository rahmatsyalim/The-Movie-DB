package com.syalim.themoviedb.common


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
object Constants {

   const val BASE_URL = "https://api.themoviedb.org"
   const val IMAGE_URL = "https://image.tmdb.org/t/p"
   const val DATABASE_NAME = "the_movie_db"

   const val START_PAGE_INDEX = 1

   const val MOVIES_UPCOMING = "upcoming"
   const val MOVIES_POPULAR = "popular"
   const val MOVIES_TOP_RATED = "top_rated"
   const val MOVIES_IN_THEATER = "in_theater"

   const val LOADING_DELAY = 1_000L
   const val CACHE_EXPIRED_TIME = 30L // minutes

}