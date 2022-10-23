package com.syalim.themoviedb.feature.movies.data.mapper

import com.syalim.themoviedb.core.common.Constants.IMAGE_URL
import com.syalim.themoviedb.core.common.Constants.IMG_SIZE_AVATAR_THUMBNAIL
import com.syalim.themoviedb.core.common.Constants.IMG_SIZE_BACKDROP
import com.syalim.themoviedb.core.common.Constants.IMG_SIZE_POSTER_DETAIL
import com.syalim.themoviedb.core.common.Constants.IMG_SIZE_POSTER_FULL
import com.syalim.themoviedb.core.common.Constants.IMG_SIZE_POSTER_THUMBNAIL
import com.syalim.themoviedb.core.common.DateFormatter
import com.syalim.themoviedb.core.common.NumberFormatter
import com.syalim.themoviedb.core.network.movie.dto.*
import com.syalim.themoviedb.feature.movies.domain.model.Movie
import com.syalim.themoviedb.feature.movies.domain.model.MovieDetails
import com.syalim.themoviedb.feature.movies.domain.model.MovieGenre
import com.syalim.themoviedb.feature.movies.domain.model.MovieReview


/**
 * Created by Rahmat Syalim on 2022/09/05
 * rahmatsyalim@gmail.com
 */

internal fun MovieDto.asDomainModel(): Movie {
   val year = DateFormatter.stringDateYear(releaseDate)
   return Movie(
      id = id,
      title = title,
      posterPath = posterPath?.let { IMAGE_URL + IMG_SIZE_POSTER_THUMBNAIL + it },
      backdropPath = backdropPath?.let { IMAGE_URL + IMG_SIZE_BACKDROP + it },
      releaseDate = DateFormatter.stringDateToStringDisplay(releaseDate),
      rating = NumberFormatter.toFloatRateOf5(voteAverage),
      titleWithYear = "$title ($year)",
      simpleVoteCount = NumberFormatter.toStringSimpleCount(voteCount)
   )
}

internal fun MovieGenreDto.asDomainModel(): MovieGenre {
   return MovieGenre(
      id = id,
      name = name
   )
}

internal fun MovieDetailsDto.asDomainModel(): MovieDetails {
   val displayDate = DateFormatter.stringDateToStringDisplay(releaseDate)
   val duration = NumberFormatter.toStringDuration(runtime)
   return MovieDetails(
      id = id,
      adult = adult,
      backdropPath = backdropPath?.let { IMAGE_URL + IMG_SIZE_BACKDROP + it },
      genres = genres.map { it.name },
      overview = overview,
      posterPath = posterPath?.let { IMAGE_URL + IMG_SIZE_POSTER_DETAIL + it },
      posterFullPath = posterPath?.let { IMAGE_URL + IMG_SIZE_POSTER_FULL + it },
      tagline = if (!tagline.isNullOrBlank()) "\"${tagline}\"" else null,
      title = title,
      voteAverage = NumberFormatter.toDoubleOneDecimal(voteAverage),
      voteCount = buildString { append(NumberFormatter.toStringNominal(voteCount)).append(" votes") },
      trailerKey = videos.results.asDomainModel(),
      recommendations = recommendations.results.map { it.asDomainModel() },
      reviews = reviews.results.randomOrNull()?.asDomainModel(),
      statusReleaseDateDuration = buildString {
         append(status)
         displayDate?.let { append(" · ").append(it) }
         duration?.let { append(" · ").append(it) }
      },
      voteAveragePercent = NumberFormatter.toIntPercent(voteAverage)
   )
}

internal fun MovieReviewDto.asDomainModel(): MovieReview {
   val avatarPath = authorDetails.avatarPath?.let {
      if (it.contains("gravatar")) it.drop(1).plus("?=32")
      else IMAGE_URL + IMG_SIZE_AVATAR_THUMBNAIL + it
   }
   return MovieReview(
      author = author,
      avatarPath = avatarPath,
      rating = authorDetails.rating,
      content = content,
      id = id,
      updatedAt = updatedAt
   )
}

internal fun List<MovieTrailerDto>.asDomainModel(): String? {
   val trailer = filter {
      it.type == "Trailer" && it.official
         || it.type == "Teaser" && it.official
         || it.official
   }
   return trailer.lastOrNull()?.key
}