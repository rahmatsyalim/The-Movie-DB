package com.syalim.themoviedb.feature.tvshows.domain.usecase.discover

import androidx.paging.PagingData
import com.syalim.themoviedb.feature.tvshows.domain.model.TvShow
import com.syalim.themoviedb.feature.tvshows.domain.repository.TvShowsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/09/06
 * rahmatsyalim@gmail.com
 */

class GetDiscoverTvShows @Inject constructor(
   private val repository: TvShowsRepository
) {
   operator fun invoke(): Flow<PagingData<TvShow>> {
      return repository.getDiscoverTvShows()
   }
}