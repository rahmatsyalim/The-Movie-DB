package com.syalim.themoviedb.feature.tvshows.data.mapper

import com.syalim.themoviedb.core.common.DateFormatter
import com.syalim.themoviedb.core.common.NumberFormatter
import com.syalim.themoviedb.core.database.tvshow.entity.TvShowEntity
import com.syalim.themoviedb.feature.tvshows.domain.model.TvShow


/**
 * Created by Rahmat Syalim on 2022/09/06
 * rahmatsyalim@gmail.com
 */

internal fun TvShowEntity.asDomainModel(): TvShow {
   val year = DateFormatter.stringDateYear(firstAirDate)
   return TvShow(
      id = id,
      name = name,
      posterPath = posterPath,
      backdropPath = backdropPath,
      firstAirDate = DateFormatter.stringDateToStringDisplay(firstAirDate),
      rating = NumberFormatter.toFloatRateOf5(voteAverage),
      nameWithYear = "$name ($year)",
      simpleVoteCount = NumberFormatter.toStringSimpleCount(voteCount)
   )
}