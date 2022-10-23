package com.syalim.themoviedb.feature.tvshows.domain.repository

import androidx.paging.PagingData
import com.syalim.themoviedb.core.common.Result
import com.syalim.themoviedb.feature.tvshows.domain.utils.TvShowCategory
import com.syalim.themoviedb.feature.tvshows.domain.model.TvShow
import com.syalim.themoviedb.feature.tvshows.domain.model.TvShowsCategorized
import kotlinx.coroutines.flow.Flow


/**
 * Created by Rahmat Syalim on 2022/09/06
 * rahmatsyalim@gmail.com
 */

interface TvShowsRepository {
   fun getTvShows(category: TvShowCategory): Flow<Result<TvShowsCategorized>>

   fun getTvShowsPaged(category: TvShowCategory): Flow<PagingData<TvShow>>

   fun getDiscoverTvShows(): Flow<PagingData<TvShow>>
}