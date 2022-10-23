package com.syalim.themoviedb.feature.tvshows.data.mapper

import com.syalim.themoviedb.core.common.Constants.IMAGE_URL
import com.syalim.themoviedb.core.common.Constants.IMG_SIZE_BACKDROP
import com.syalim.themoviedb.core.common.Constants.IMG_SIZE_POSTER_FULL
import com.syalim.themoviedb.core.common.Constants.IMG_SIZE_POSTER_THUMBNAIL
import com.syalim.themoviedb.feature.tvshows.domain.utils.TvShowCategory
import com.syalim.themoviedb.core.database.tvshow.entity.TvShowEntity
import com.syalim.themoviedb.core.network.tvshow.dto.TvShowDto


/**
 * Created by Rahmat Syalim on 2022/09/06
 * rahmatsyalim@gmail.com
 */

internal fun TvShowDto.asLocalModel(category: String): TvShowEntity {
   return TvShowEntity(
      id = id,
      name = name,
      posterPath = posterPath?.let {
         IMAGE_URL + if (category == TvShowCategory.ON_THE_AIR.param) {
            IMG_SIZE_POSTER_FULL
         } else {
            IMG_SIZE_POSTER_THUMBNAIL
         } + it
      },
      backdropPath = backdropPath?.let { IMAGE_URL + IMG_SIZE_BACKDROP + it },
      firstAirDate = firstAirDate,
      voteAverage = voteAverage,
      voteCount = voteCount,
      category = category
   )
}
