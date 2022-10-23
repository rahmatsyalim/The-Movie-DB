package com.syalim.themoviedb.core.database.tvshow

import com.syalim.themoviedb.core.database.tvshow.dao.TvShowsDao
import com.syalim.themoviedb.core.database.tvshow.entity.TvShowEntity


/**
 * Created by Rahmat Syalim on 2022/09/06
 * rahmatsyalim@gmail.com
 */

internal class TvShowsLocalDataSourceImpl(
   private val dao: TvShowsDao
) : TvShowsLocalDataSource {
   override suspend fun getTvShows(category: String): List<TvShowEntity> {
      return dao.getTvShows(category)
   }

   override suspend fun deleteTvShows(category: String) {
      return dao.deleteTvShows(category)
   }

   override suspend fun insertTvShows(tvShows: List<TvShowEntity>) {
      return dao.insertTvShows(tvShows)
   }

}