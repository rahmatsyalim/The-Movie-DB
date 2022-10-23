package com.syalim.themoviedb.core.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.syalim.themoviedb.core.database.movie.dao.MoviesDao
import com.syalim.themoviedb.core.database.movie.entity.MovieEntity
import com.syalim.themoviedb.core.database.movie.entity.MoviesBookmarkedEntity
import com.syalim.themoviedb.core.database.tvshow.dao.TvShowsDao
import com.syalim.themoviedb.core.database.tvshow.entity.TvShowEntity
import com.syalim.themoviedb.core.database.utils.RoomConverters


/**
 * Created by Rahmat Syalim on 2022/08/17
 * rahmatsyalim@gmail.com
 */

@Database(
   entities = [
      MovieEntity::class,
      MoviesBookmarkedEntity::class,
      TvShowEntity::class
   ],
   version = 1,
   exportSchema = false
)
@TypeConverters(RoomConverters::class)
abstract class TheMoviesDatabase : RoomDatabase() {

   abstract fun moviesDao(): MoviesDao

   abstract fun tvShowsDao(): TvShowsDao

}