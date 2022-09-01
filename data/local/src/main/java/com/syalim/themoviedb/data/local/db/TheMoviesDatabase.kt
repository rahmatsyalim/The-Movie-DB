package com.syalim.themoviedb.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.syalim.themoviedb.data.local.dao.MoviesDao
import com.syalim.themoviedb.data.local.entity.MovieGenresEntity
import com.syalim.themoviedb.data.local.entity.MovieHomeEntity
import com.syalim.themoviedb.data.local.entity.MoviesBookmarkedEntity


/**
 * Created by Rahmat Syalim on 2022/08/17
 * rahmatsyalim@gmail.com
 */

@Database(
   entities = [
      MovieHomeEntity::class,
      MoviesBookmarkedEntity::class,
      MovieGenresEntity::class
   ],
   version = 1,
   exportSchema = false
)
@TypeConverters(com.syalim.themoviedb.data.local.converter.RoomConverters::class)
abstract class TheMoviesDatabase : RoomDatabase() {

   abstract fun moviesDao(): MoviesDao

}