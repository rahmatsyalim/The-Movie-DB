package com.syalim.themoviedb.feature.tvshows.domain.usecase.tv_shows_by_category

import androidx.paging.PagingData
import com.syalim.themoviedb.feature.tvshows.domain.utils.TvShowCategory
import com.syalim.themoviedb.feature.tvshows.domain.model.TvShow
import com.syalim.themoviedb.feature.tvshows.domain.repository.TvShowsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/09/06
 * rahmatsyalim@gmail.com
 */

class GetTvShowsPaged @Inject constructor(
   private val repository: TvShowsRepository
) {
   operator fun invoke(category: TvShowCategory): Flow<PagingData<TvShow>> {
      return repository.getTvShowsPaged(category)
   }
}