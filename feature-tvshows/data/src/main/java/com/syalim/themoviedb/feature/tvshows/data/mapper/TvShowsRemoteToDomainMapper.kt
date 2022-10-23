package com.syalim.themoviedb.feature.tvshows.data.mapper

import com.syalim.themoviedb.core.common.Constants.IMAGE_URL
import com.syalim.themoviedb.core.common.Constants.IMG_SIZE_BACKDROP
import com.syalim.themoviedb.core.common.Constants.IMG_SIZE_POSTER_THUMBNAIL
import com.syalim.themoviedb.core.common.DateFormatter
import com.syalim.themoviedb.core.common.NumberFormatter
import com.syalim.themoviedb.core.network.tvshow.dto.TvShowDto
import com.syalim.themoviedb.feature.tvshows.domain.model.TvShow


/**
 * Created by Rahmat Syalim on 2022/09/06
 * rahmatsyalim@gmail.com
 */

internal fun TvShowDto.asDomainModel(): TvShow {
   val year = DateFormatter.stringDateYear(firstAirDate)
   return TvShow(
      id = id,
      name = name,
      posterPath = posterPath?.let { IMAGE_URL + IMG_SIZE_POSTER_THUMBNAIL + it },
      backdropPath = backdropPath?.let { IMAGE_URL + IMG_SIZE_BACKDROP + it },
      firstAirDate = DateFormatter.stringDateToStringDisplay(firstAirDate),
      rating = NumberFormatter.toFloatRateOf5(voteAverage),
      nameWithYear = "$name ($year)",
      simpleVoteCount = NumberFormatter.toStringSimpleCount(voteCount)
   )
}