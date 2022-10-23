package com.syalim.themoviedb.core.database.tvshow

import com.syalim.themoviedb.core.database.tvshow.entity.TvShowEntity


/**
 * Created by Rahmat Syalim on 2022/09/06
 * rahmatsyalim@gmail.com
 */
interface TvShowsLocalDataSource {
   suspend fun getTvShows(category: String): List<TvShowEntity>

   suspend fun deleteTvShows(category: String)

   suspend fun insertTvShows(tvShows: List<TvShowEntity>)
}