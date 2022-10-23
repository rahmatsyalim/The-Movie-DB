package com.syalim.themoviedb.core.network.di

import com.syalim.themoviedb.core.network.movie.MoviesRemoteDataSource
import com.syalim.themoviedb.core.network.movie.MoviesRemoteDataSourceImpl
import com.syalim.themoviedb.core.network.movie.api.MovieApi
import com.syalim.themoviedb.core.network.tvshow.TvShowsRemoteDataSource
import com.syalim.themoviedb.core.network.tvshow.TvShowsRemoteDataSourceImpl
import com.syalim.themoviedb.core.network.tvshow.api.TvShowsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Created by Rahmat Syalim on 2022/09/02
 * rahmatsyalim@gmail.com
 */

@Module
@InstallIn(SingletonComponent::class)
internal object RemoteDataSourceModule {
   @Provides
   @Singleton
   fun provideMoviesRemoteDataSource(api: MovieApi): MoviesRemoteDataSource {
      return MoviesRemoteDataSourceImpl(api)
   }

   @Provides
   @Singleton
   fun provideTvShowsRemoteDataSource(api: TvShowsApi): TvShowsRemoteDataSource {
      return TvShowsRemoteDataSourceImpl(api)
   }

}