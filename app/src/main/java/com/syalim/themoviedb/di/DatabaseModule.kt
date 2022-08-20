package com.syalim.themoviedb.di

import android.content.Context
import androidx.room.Room
import com.syalim.themoviedb.common.Constants.DATABASE_NAME
import com.syalim.themoviedb.data.source.local.dao.MoviesDao
import com.syalim.themoviedb.data.source.local.db.TheMoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Created by Rahmat Syalim on 2022/08/17
 * rahmatsyalim@gmail.com
 */

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

   @Provides
   @Singleton
   fun provideDatabase(@ApplicationContext context: Context): TheMoviesDatabase {
      return Room.databaseBuilder(context, TheMoviesDatabase::class.java, DATABASE_NAME).build()
   }

   @Provides
   @Singleton
   fun provideMoviesDao(database: TheMoviesDatabase): MoviesDao = database.moviesDao()

}