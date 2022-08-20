package com.syalim.themoviedb.di

import com.syalim.themoviedb.data.repository.ConnectivityRepositoryImpl
import com.syalim.themoviedb.data.repository.MoviesRepositoryImpl
import com.syalim.themoviedb.domain.repository.ConnectivityRepository
import com.syalim.themoviedb.domain.repository.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Created by Rahmat Syalim on 2022/08/09
 * rahmatsyalim@gmail.com
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

   @Binds
   @Singleton
   abstract fun bindMoviesRepository(repositoryImpl: MoviesRepositoryImpl): MoviesRepository

   @Binds
   @Singleton
   abstract fun bindConnectivityRepository(repositoryImpl: ConnectivityRepositoryImpl): ConnectivityRepository

}