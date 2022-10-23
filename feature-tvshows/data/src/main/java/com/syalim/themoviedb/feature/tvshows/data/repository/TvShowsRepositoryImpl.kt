package com.syalim.themoviedb.feature.tvshows.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.syalim.themoviedb.core.common.Result
import com.syalim.themoviedb.feature.tvshows.domain.utils.TvShowCategory
import com.syalim.themoviedb.core.database.tvshow.TvShowsLocalDataSource
import com.syalim.themoviedb.core.network.tvshow.TvShowsRemoteDataSource
import com.syalim.themoviedb.core.network.utils.cachedResourceStream
import com.syalim.themoviedb.feature.tvshows.data.mapper.asDomainModel
import com.syalim.themoviedb.feature.tvshows.data.mapper.asLocalModel
import com.syalim.themoviedb.feature.tvshows.data.paging.DiscoverTvShowsPagingSource
import com.syalim.themoviedb.feature.tvshows.data.paging.TvShowsPagingSource
import com.syalim.themoviedb.feature.tvshows.domain.model.TvShow
import com.syalim.themoviedb.feature.tvshows.domain.model.TvShowsCategorized
import com.syalim.themoviedb.feature.tvshows.domain.repository.TvShowsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow


/**
 * Created by Rahmat Syalim on 2022/09/06
 * rahmatsyalim@gmail.com
 */

class TvShowsRepositoryImpl(
   private val remoteDataSource: TvShowsRemoteDataSource,
   private val localDataSource: TvShowsLocalDataSource,
   private val ioDispatcher: CoroutineDispatcher
) : TvShowsRepository {

   private companion object {
      const val PAGE_SIZE = 20
      const val PREFETCH_DISTANCE = 4
      const val INIT_LOAD_SIZE = 2 * PAGE_SIZE
      const val PLACE_HOLDER = false
   }

   override fun getTvShows(category: TvShowCategory): Flow<Result<TvShowsCategorized>> {
      return cachedResourceStream(
         fetchLocal = { localDataSource.getTvShows(category.param) },
         fetchRemote = { remoteDataSource.getTvShows(category.param) },
         cacheData = {
            localDataSource.deleteTvShows(category.param)
            localDataSource.insertTvShows(results.map { it.asLocalModel(category.param) })
         },
         isNoData = { isNullOrEmpty() },
         lastCachedTime = { first().updatedAt.time },
         mapToResult = { TvShowsCategorized(category, map { it.asDomainModel() }) }
      )
   }

   override fun getTvShowsPaged(category: TvShowCategory): Flow<PagingData<TvShow>> {
      return Pager(
         config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = PLACE_HOLDER,
            prefetchDistance = PREFETCH_DISTANCE,
            initialLoadSize = INIT_LOAD_SIZE
         ),
         pagingSourceFactory = {
            TvShowsPagingSource(
               remoteDataSource,
               ioDispatcher,
               category.param
            )
         }
      ).flow
   }

   override fun getDiscoverTvShows(): Flow<PagingData<TvShow>> {
      return Pager(
         config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = PLACE_HOLDER,
            prefetchDistance = PREFETCH_DISTANCE,
            initialLoadSize = INIT_LOAD_SIZE
         ),
         pagingSourceFactory = {
            DiscoverTvShowsPagingSource(
               remoteDataSource,
               ioDispatcher
            )
         }
      ).flow
   }
}