package com.syalim.themoviedb.feature.movies.domain.utils


/**
 * Created by Rahmat Syalim on 2022/09/02
 * rahmatsyalim@gmail.com
 */

enum class MovieCategory(val param: String) {
   IN_THEATER("now_playing"),
   POPULAR("popular"),
   TOP_RATED("top_rated"),
   UPCOMING("upcoming");
}