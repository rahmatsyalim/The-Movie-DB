package com.syalim.themoviedb.core.database.di

import com.syalim.themoviedb.core.database.movie.MoviesLocalDataSource
import com.syalim.themoviedb.core.database.movie.MoviesLocalDataSourceImpl
import com.syalim.themoviedb.core.database.movie.dao.MoviesDao
import com.syalim.themoviedb.core.database.tvshow.TvShowsLocalDataSource
import com.syalim.themoviedb.core.database.tvshow.TvShowsLocalDataSourceImpl
import com.syalim.themoviedb.core.database.tvshow.dao.TvShowsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Created by Rahmat Syalim on 2022/09/03
 * rahmatsyalim@gmail.com
 */

@Module
@InstallIn(SingletonComponent::class)
internal object LocalDataSourceModule {
   @Provides
   @Singleton
   fun provideMoviesLocalDataSource(dao: MoviesDao): MoviesLocalDataSource {
      return MoviesLocalDataSourceImpl(dao)
   }

   @Provides
   @Singleton
   fun provideTvShowsLocalDataSource(dao: TvShowsDao): TvShowsLocalDataSource {
      return TvShowsLocalDataSourceImpl(dao)
   }
}