package com.syalim.themoviedb.data.source.local

import com.syalim.themoviedb.data.source.local.entity.MovieEntity
import com.syalim.themoviedb.data.source.local.entity.MoviesBookmarkedEntity


/**
 * Created by Rahmat Syalim on 2022/08/17
 * rahmatsyalim@gmail.com
 */
interface MoviesLocalDataSource {
   suspend fun insertMovies(data: List<MovieEntity>)
   suspend fun deleteMoviesByCategory(category: String)
   suspend fun getMovies(category: String): List<MovieEntity>
   suspend fun isMovieBookmarked(id: Int): Boolean
   suspend fun getBookmarkedMovies(): List<MoviesBookmarkedEntity>
   suspend fun deleteBookmarkedMovieById(id: Int): Int
}