package com.syalim.themoviedb.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.syalim.themoviedb.data.common.utils.RoomTypeConverter
import com.syalim.themoviedb.data.source.local.dao.MoviesDao
import com.syalim.themoviedb.data.source.local.entity.MovieEntity
import com.syalim.themoviedb.data.source.local.entity.MovieGenresEntity
import com.syalim.themoviedb.data.source.local.entity.MoviesBookmarkedEntity


/**
 * Created by Rahmat Syalim on 2022/08/17
 * rahmatsyalim@gmail.com
 */

@Database(
   entities = [
      MovieEntity::class,
      MoviesBookmarkedEntity::class,
      MovieGenresEntity::class
   ],
   version = 1
)
@TypeConverters(RoomTypeConverter::class)
abstract class TheMoviesDatabase : RoomDatabase() {

   abstract fun moviesDao(): MoviesDao

}