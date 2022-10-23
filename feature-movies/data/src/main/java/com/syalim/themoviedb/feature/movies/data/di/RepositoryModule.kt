package com.syalim.themoviedb.feature.movies.data.di

import com.syalim.themoviedb.core.common.IoDispatcher
import com.syalim.themoviedb.core.database.movie.MoviesLocalDataSource
import com.syalim.themoviedb.core.network.movie.MoviesRemoteDataSource
import com.syalim.themoviedb.feature.movies.data.repository.MoviesRepositoryImpl
import com.syalim.themoviedb.feature.movies.domain.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton


/**
 * Created by Rahmat Syalim on 2022/09/02
 * rahmatsyalim@gmail.com
 */

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {

   @Provides
   @Singleton
   fun provideMoviesRepository(
      remoteDataSource: MoviesRemoteDataSource,
      localDataSource: MoviesLocalDataSource,
      @IoDispatcher ioDispatcher: CoroutineDispatcher
   ): MoviesRepository {
      return MoviesRepositoryImpl(remoteDataSource, localDataSource, ioDispatcher)
   }
}