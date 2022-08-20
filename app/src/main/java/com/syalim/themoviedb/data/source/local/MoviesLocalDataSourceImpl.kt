package com.syalim.themoviedb.data.source.local

import com.syalim.themoviedb.data.source.local.dao.MoviesDao
import com.syalim.themoviedb.data.source.local.entity.MovieEntity
import com.syalim.themoviedb.data.source.local.entity.MoviesBookmarkedEntity
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/08/17
 * rahmatsyalim@gmail.com
 */

class MoviesLocalDataSourceImpl @Inject constructor(
   private val dao: MoviesDao
) : MoviesLocalDataSource {
   override suspend fun insertMovies(data: List<MovieEntity>) = dao.insertMovies(data)
   override suspend fun deleteMoviesByCategory(category: String) = dao.deleteMoviesByCategory(category)
   override suspend fun getMovies(category: String): List<MovieEntity> = dao.getMovies(category)
   override suspend fun isMovieBookmarked(id: Int): Boolean = dao.isMovieBookmarked(id)
   override suspend fun getBookmarkedMovies(): List<MoviesBookmarkedEntity> = dao.getBookmarkedMovies()
   override suspend fun deleteBookmarkedMovieById(id: Int): Int = dao.deleteBookmarkedMovieById(id)
}