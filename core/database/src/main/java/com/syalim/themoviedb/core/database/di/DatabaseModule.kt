package com.syalim.themoviedb.core.database.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.syalim.themoviedb.core.database.BuildConfig
import com.syalim.themoviedb.core.database.db.TheMoviesDatabase
import com.syalim.themoviedb.core.database.movie.dao.MoviesDao
import com.syalim.themoviedb.core.database.tvshow.dao.TvShowsDao
import com.syalim.themoviedb.core.database.utils.GsonParser
import com.syalim.themoviedb.core.database.utils.RoomConverters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Created by Rahmat Syalim on 2022/08/30
 * rahmatsyalim@gmail.com
 */

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
   @Provides
   @Singleton
   fun provideDatabase(@ApplicationContext context: Context): TheMoviesDatabase =
      Room.databaseBuilder(context, TheMoviesDatabase::class.java, BuildConfig.DB_NAME)
         .addTypeConverter(RoomConverters(GsonParser(Gson())))
         .build()

   @Provides
   @Singleton
   fun provideMoviesDao(database: TheMoviesDatabase): MoviesDao = database.moviesDao()

   @Provides
   @Singleton
   fun provideTvShowsDao(database: TheMoviesDatabase): TvShowsDao = database.tvShowsDao()

}