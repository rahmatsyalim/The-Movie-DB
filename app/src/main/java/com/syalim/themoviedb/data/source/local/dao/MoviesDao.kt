package com.syalim.themoviedb.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syalim.themoviedb.data.source.local.entity.MovieEntity
import com.syalim.themoviedb.data.source.local.entity.MoviesBookmarkedEntity


/**
 * Created by Rahmat Syalim on 2022/08/17
 * rahmatsyalim@gmail.com
 */

@Dao
interface MoviesDao {

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertMovies(data: List<MovieEntity>)

   @Query("SELECT * FROM movies WHERE category =:category")
   suspend fun getMovies(category: String): List<MovieEntity>

   @Query("DELETE FROM movies WHERE category=:category")
   suspend fun deleteMoviesByCategory(category: String)

   @Query(value = "SELECT EXISTS(SELECT 1 FROM movies_bookmarked WHERE id=:id)")
   suspend fun isMovieBookmarked(id: Int): Boolean

   @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun insertBookmarkedMovie(data: MoviesBookmarkedEntity): Long

   @Query(value = "DELETE FROM movies_bookmarked WHERE id=:id")
   suspend fun deleteBookmarkedMovieById(id: Int): Int

   @Query(value = "SELECT * FROM movies_bookmarked")
   suspend fun getBookmarkedMovies(): List<MoviesBookmarkedEntity>
}