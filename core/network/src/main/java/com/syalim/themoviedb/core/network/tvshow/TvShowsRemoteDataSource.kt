package com.syalim.themoviedb.core.network.tvshow

import com.syalim.themoviedb.core.network.tvshow.dto.TvShowsDto


/**
 * Created by Rahmat Syalim on 2022/09/06
 * rahmatsyalim@gmail.com
 */

interface TvShowsRemoteDataSource {
   suspend fun getTvShows(category: String): TvShowsDto

   suspend fun getTvShowsPaged(category: String, page: Int): TvShowsDto

   suspend fun getDiscoverTvShows(page: Int): TvShowsDto
}