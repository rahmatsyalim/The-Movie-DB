package com.syalim.themoviedb.core.database.movie.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syalim.themoviedb.core.database.movie.entity.MovieEntity
import com.syalim.themoviedb.core.database.movie.entity.MoviesBookmarkedEntity


/**
 * Created by Rahmat Syalim on 2022/08/17
 * rahmatsyalim@gmail.com
 */

@Dao
interface MoviesDao {
   @Query("SELECT * FROM movies WHERE category =:category")
   suspend fun getMovies(category: String): List<MovieEntity>

   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertMovies(data: List<MovieEntity>)

   @Query("DELETE FROM movies WHERE category=:category")
   suspend fun deleteMovies(category: String)

   @Query(value = "SELECT * FROM movies_bookmarked")
   suspend fun getBookmarkedMovies(): List<MoviesBookmarkedEntity>

   @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun insertBookmarkedMovie(data: MoviesBookmarkedEntity): Long

   @Query(value = "DELETE FROM movies_bookmarked WHERE id=:id")
   suspend fun deleteBookmarkedMovie(id: Int): Int

   @Query(value = "SELECT EXISTS(SELECT 1 FROM movies_bookmarked WHERE id=:id)")
   suspend fun isMovieBookmarked(id: Int): Boolean

}