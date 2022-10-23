package com.syalim.themoviedb.feature.bookmarks.domain.repository


/**
 * Created by Rahmat Syalim on 2022/09/03
 * rahmatsyalim@gmail.com
 */

interface MoviesRepository {
   fun getBookmarkedMovies()

   fun removeMovieBookmark()
}