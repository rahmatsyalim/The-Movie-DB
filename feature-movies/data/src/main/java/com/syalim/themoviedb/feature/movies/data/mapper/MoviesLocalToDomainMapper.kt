package com.syalim.themoviedb.feature.movies.data.mapper

import com.syalim.themoviedb.core.common.DateFormatter
import com.syalim.themoviedb.core.common.NumberFormatter
import com.syalim.themoviedb.core.database.movie.entity.MovieEntity
import com.syalim.themoviedb.feature.movies.domain.model.Movie


/**
 * Created by Rahmat Syalim on 2022/09/02
 * rahmatsyalim@gmail.com
 */

internal fun MovieEntity.asDomainModel(): Movie {
   val year = DateFormatter.stringDateYear(releaseDate)
   return Movie(
      id = id,
      title = title,
      posterPath = posterPath,
      backdropPath = backdropPath,
      releaseDate = DateFormatter.stringDateToStringDisplay(releaseDate),
      rating = NumberFormatter.toFloatRateOf5(voteAverage),
      titleWithYear = "$title ($year)",
      simpleVoteCount = NumberFormatter.toStringSimpleCount(voteCount)
   )
}