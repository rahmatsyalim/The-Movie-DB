package com.syalim.themoviedb.data.local.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.syalim.themoviedb.data.local.BuildConfig
import com.syalim.themoviedb.data.local.converter.RoomConverters
import com.syalim.themoviedb.data.local.dao.MoviesDao
import com.syalim.themoviedb.data.local.db.TheMoviesDatabase
import com.syalim.themoviedb.data.local.utils.GsonParser
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
object DataBaseModule {
   @Provides
   @Singleton
   fun provideDatabase(@ApplicationContext context: Context): TheMoviesDatabase =
      Room.databaseBuilder(context, TheMoviesDatabase::class.java, BuildConfig.DB_NAME)
         .addTypeConverter(RoomConverters(GsonParser(Gson())))
         .build()

   @Provides
   @Singleton
   fun provideMoviesDao(database: TheMoviesDatabase): MoviesDao = database.moviesDao()
}