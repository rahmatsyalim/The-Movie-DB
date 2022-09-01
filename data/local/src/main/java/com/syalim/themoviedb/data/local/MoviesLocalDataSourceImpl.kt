package com.syalim.themoviedb.data.local

import com.syalim.themoviedb.data.local.dao.MoviesDao
import com.syalim.themoviedb.data.local.entity.MovieHomeEntity
import com.syalim.themoviedb.data.local.entity.MoviesBookmarkedEntity
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/08/17
 * rahmatsyalim@gmail.com
 */

class MoviesLocalDataSourceImpl @Inject constructor(
   private val dao: MoviesDao
) : MoviesLocalDataSource {
   override suspend fun getHomeMovies(category: String): List<MovieHomeEntity> = dao.getHomeMovies(category)
   override suspend fun insertHomeMovies(data: List<MovieHomeEntity>) = dao.insertHomeMovies(data)
   override suspend fun deleteHomeMovies(category: String) = dao.deleteHomeMovies(category)
   override suspend fun getBookmarkedMovies(): List<MoviesBookmarkedEntity> = dao.getBookmarkedMovies()
   override suspend fun insertBookmarkedMovie(data: MoviesBookmarkedEntity): Long = dao.insertBookmarkedMovie(data)
   override suspend fun deleteBookmarkedMovie(id: Int): Int = dao.deleteBookmarkedMovie(id)
   override suspend fun isMovieBookmarked(id: Int): Boolean = dao.isMovieBookmarked(id)
}