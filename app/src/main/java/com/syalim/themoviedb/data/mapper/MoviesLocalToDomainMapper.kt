package com.syalim.themoviedb.data.mapper

import com.syalim.themoviedb.data.common.utils.DateFormatter
import com.syalim.themoviedb.data.common.utils.NumberFormatter
import com.syalim.themoviedb.data.source.local.entity.MovieEntity
import com.syalim.themoviedb.domain.model.Movie
import com.syalim.themoviedb.domain.model.MovieCarousel
import com.syalim.themoviedb.domain.model.MovieExtras


/**
 * Created by Rahmat Syalim on 2022/08/18
 * rahmatsyalim@gmail.com
 */

fun MovieEntity.asDomainMovie(): Movie {
   val year = DateFormatter.dateYear(releaseDate)
   return Movie(
      id = id,
      title = title,
      posterPath = posterPath,
      backdropPath = backdropPath,
      releaseDate = releaseDate,
      voteAverage = voteAverage,
      voteCount = voteCount,
      extras = MovieExtras(
         titleWithYear = "$title ($year)",
         voteAverageRating = NumberFormatter.toRateOf5(voteAverage),
         simpleVoteCount = NumberFormatter.toSimpleCount(voteCount)
      )
   )
}

fun MovieEntity.asDomainMovieCarousel(): MovieCarousel {
   return MovieCarousel(
      id = id,
      title = title,
      backdropPath = backdropPath,
      releaseDate = DateFormatter.dateToStringDateDisplay(releaseDate)
   )
}