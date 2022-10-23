package com.syalim.themoviedb.core.network.tvshow

import com.syalim.themoviedb.core.network.tvshow.api.TvShowsApi
import com.syalim.themoviedb.core.network.tvshow.dto.TvShowsDto


/**
 * Created by Rahmat Syalim on 2022/09/06
 * rahmatsyalim@gmail.com
 */

internal class TvShowsRemoteDataSourceImpl(
   private val api: TvShowsApi
) : TvShowsRemoteDataSource {
   override suspend fun getTvShows(category: String): TvShowsDto {
      return api.getTvShows(category, 1)
   }

   override suspend fun getTvShowsPaged(category: String, page: Int): TvShowsDto {
      return api.getTvShows(category, page)
   }

   override suspend fun getDiscoverTvShows(page: Int): TvShowsDto {
      return api.getDiscoverTvShows(page)
   }
}