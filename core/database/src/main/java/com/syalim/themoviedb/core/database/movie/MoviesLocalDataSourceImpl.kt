package com.syalim.themoviedb.core.database.movie

import com.syalim.themoviedb.core.database.movie.dao.MoviesDao
import com.syalim.themoviedb.core.database.movie.entity.MovieEntity


/**
 * Created by Rahmat Syalim on 2022/09/02
 * rahmatsyalim@gmail.com
 */

internal class MoviesLocalDataSourceImpl(
   private val dao: MoviesDao
) : MoviesLocalDataSource {
   override suspend fun getMovies(category: String): List<MovieEntity> {
      return dao.getMovies(category)
   }

   override suspend fun deleteMovies(category: String) {
      return dao.deleteMovies(category)
   }

   override suspend fun insertMovies(movies: List<MovieEntity>) {
      return dao.insertMovies(movies)
   }

   override suspend fun isMovieBookmarked(id: Int): Boolean {
      return dao.isMovieBookmarked(id)
   }

}