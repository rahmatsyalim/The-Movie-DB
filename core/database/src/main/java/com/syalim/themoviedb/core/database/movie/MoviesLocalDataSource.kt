package com.syalim.themoviedb.core.database.movie

import com.syalim.themoviedb.core.database.movie.entity.MovieEntity


/**
 * Created by Rahmat Syalim on 2022/09/02
 * rahmatsyalim@gmail.com
 */

interface MoviesLocalDataSource {
   suspend fun getMovies(category: String): List<MovieEntity>

   suspend fun deleteMovies(category: String)

   suspend fun insertMovies(movies: List<MovieEntity>)

   suspend fun isMovieBookmarked(id: Int): Boolean
}