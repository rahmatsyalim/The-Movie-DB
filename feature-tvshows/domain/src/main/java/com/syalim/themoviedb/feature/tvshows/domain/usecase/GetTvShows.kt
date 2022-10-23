package com.syalim.themoviedb.feature.tvshows.domain.usecase

import com.syalim.themoviedb.core.common.Result
import com.syalim.themoviedb.feature.tvshows.domain.utils.TvShowCategory
import com.syalim.themoviedb.feature.tvshows.domain.model.TvShowsCategorized
import com.syalim.themoviedb.feature.tvshows.domain.repository.TvShowsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/09/06
 * rahmatsyalim@gmail.com
 */

class GetTvShows @Inject constructor(
   private val repository: TvShowsRepository
) {
   operator fun invoke(category: TvShowCategory): Flow<Result<TvShowsCategorized>> {
      return repository.getTvShows(category)
   }
}