package com.syalim.themoviedb.data.remote.di

import com.syalim.themoviedb.data.remote.MoviesRemoteDataSource
import com.syalim.themoviedb.data.remote.MoviesRemoteDataSourceImpl
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
interface RemoteDataSourceModule {

   @Binds
   @Singleton
   fun provideMoviesRemoteDataSource(impl: MoviesRemoteDataSourceImpl): MoviesRemoteDataSource

}