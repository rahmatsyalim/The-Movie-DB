package com.syalim.themoviedb.di

import com.syalim.themoviedb.data.source.connectivity.ConnectivityDataSource
import com.syalim.themoviedb.data.source.connectivity.ConnectivityDataSourceImpl
import com.syalim.themoviedb.data.source.local.MoviesLocalDataSource
import com.syalim.themoviedb.data.source.local.MoviesLocalDataSourceImpl
import com.syalim.themoviedb.data.source.remote.MoviesRemoteDataSource
import com.syalim.themoviedb.data.source.remote.MoviesRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Created by Rahmat Syalim on 2022/08/10
 * rahmatsyalim@gmail.com
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

   @Binds
   @Singleton
   abstract fun provideMoviesRemoteDataSource(impl: MoviesRemoteDataSourceImpl): MoviesRemoteDataSource

   @Binds
   @Singleton
   abstract fun provideMoviesLocalDaraSource(impl: MoviesLocalDataSourceImpl): MoviesLocalDataSource

   @Binds
   @Singleton
   abstract fun provideConnectivityDataSource(impl: ConnectivityDataSourceImpl): ConnectivityDataSource

}