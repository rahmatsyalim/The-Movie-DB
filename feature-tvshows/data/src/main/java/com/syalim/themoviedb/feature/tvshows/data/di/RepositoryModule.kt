package com.syalim.themoviedb.feature.tvshows.data.di

import com.syalim.themoviedb.core.common.IoDispatcher
import com.syalim.themoviedb.core.database.tvshow.TvShowsLocalDataSource
import com.syalim.themoviedb.core.network.tvshow.TvShowsRemoteDataSource
import com.syalim.themoviedb.feature.tvshows.data.repository.TvShowsRepositoryImpl
import com.syalim.themoviedb.feature.tvshows.domain.repository.TvShowsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton


/**
 * Created by Rahmat Syalim on 2022/09/06
 * rahmatsyalim@gmail.com
 */

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
   @Provides
   @Singleton
   fun provideTvShowsRepository(
      remoteDataSource: TvShowsRemoteDataSource,
      localDataSource: TvShowsLocalDataSource,
      @IoDispatcher ioDispatcher: CoroutineDispatcher
   ): TvShowsRepository {
      return TvShowsRepositoryImpl(remoteDataSource, localDataSource, ioDispatcher)
   }

}