package com.syalim.themoviedb.data.local.di

import com.syalim.themoviedb.data.local.MoviesLocalDataSource
import com.syalim.themoviedb.data.local.MoviesLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Created by Rahmat Syalim on 2022/09/01
 * rahmatsyalim@gmail.com
 */

@Module
@InstallIn(SingletonComponent::class)
interface LocalDataSourceModule {
   @Binds
   @Singleton
   fun provideMoviesLocalDataSource(impl: MoviesLocalDataSourceImpl): MoviesLocalDataSource
}