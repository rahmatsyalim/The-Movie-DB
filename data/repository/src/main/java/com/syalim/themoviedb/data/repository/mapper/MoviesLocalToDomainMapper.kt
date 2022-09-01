package com.syalim.themoviedb.data.repository.mapper

import com.syalim.themoviedb.core.common.DateFormatter
import com.syalim.themoviedb.core.common.NumberFormatter
import com.syalim.themoviedb.data.local.entity.MovieHomeEntity
import com.syalim.themoviedb.data.local.entity.MoviesBookmarkedEntity
import com.syalim.themoviedb.domain.movie.model.Movie


/**
 * Created by Rahmat Syalim on 2022/08/18
 * rahmatsyalim@gmail.com
 */

fun MovieHomeEntity.asDomainModel(): Movie {
   val year = DateFormatter.stringDateYear(releaseDate)
   return Movie(
      id = id,
      title = title,
      posterPath = posterPath,
      backdropPath = backdropPath,
      releaseDate = DateFormatter.stringDateToDisplayShortMonth(releaseDate),
      rating = NumberFormatter.toRateOf5(voteAverage),
      titleWithYear = "$title ($year)",
      simpleVoteCount = NumberFormatter.toSimpleCount(voteCount)
   )
}

fun MoviesBookmarkedEntity.asDomainModel(): Movie {
   val year = DateFormatter.stringDateYear(releaseDate)
   return Movie(
      id = id,
      title = title,
      posterPath = posterPath,
      backdropPath = backdropPath,
      releaseDate = DateFormatter.stringDateToDisplayShortMonth(releaseDate),
      rating = NumberFormatter.toRateOf5(voteAverage),
      titleWithYear = "$title ($year)",
      simpleVoteCount = NumberFormatter.toSimpleCount(voteCount)
   )
}