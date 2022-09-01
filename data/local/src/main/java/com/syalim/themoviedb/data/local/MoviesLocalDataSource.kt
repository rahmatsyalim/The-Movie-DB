package com.syalim.themoviedb.data.local

import com.syalim.themoviedb.data.local.entity.MovieHomeEntity
import com.syalim.themoviedb.data.local.entity.MoviesBookmarkedEntity


/**
 * Created by Rahmat Syalim on 2022/08/17
 * rahmatsyalim@gmail.com
 */
interface MoviesLocalDataSource {
   suspend fun getHomeMovies(category: String): List<MovieHomeEntity>
   suspend fun insertHomeMovies(data: List<MovieHomeEntity>)
   suspend fun deleteHomeMovies(category: String)
   suspend fun getBookmarkedMovies(): List<MoviesBookmarkedEntity>
   suspend fun insertBookmarkedMovie(data: MoviesBookmarkedEntity): Long
   suspend fun deleteBookmarkedMovie(id: Int): Int
   suspend fun isMovieBookmarked(id: Int): Boolean
}