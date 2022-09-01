package com.syalim.themoviedb.data.repository.mapper

import com.syalim.themoviedb.core.common.Constants.IMAGE_URL
import com.syalim.themoviedb.core.common.Constants.IMG_SIZE_AVATAR_THUMBNAIL
import com.syalim.themoviedb.core.common.Constants.IMG_SIZE_BACKDROP
import com.syalim.themoviedb.core.common.Constants.IMG_SIZE_POSTER_DETAIL
import com.syalim.themoviedb.core.common.Constants.IMG_SIZE_POSTER_FULL
import com.syalim.themoviedb.core.common.Constants.IMG_SIZE_POSTER_THUMBNAIL
import com.syalim.themoviedb.core.common.DateFormatter
import com.syalim.themoviedb.core.common.NumberFormatter
import com.syalim.themoviedb.data.remote.dto.*
import com.syalim.themoviedb.domain.movie.model.Movie
import com.syalim.themoviedb.domain.movie.model.MovieDetail
import com.syalim.themoviedb.domain.movie.model.MovieGenre
import com.syalim.themoviedb.domain.movie.model.MovieReview


/**
 * Created by Rahmat Syalim on 2022/08/15
 * rahmatsyalim@gmail.com
 */

fun MovieDto.asDomainModel(): Movie {
   val year = DateFormatter.stringDateYear(releaseDate)
   return Movie(
      id = id,
      title = title,
      posterPath = posterPath?.let { IMAGE_URL + IMG_SIZE_POSTER_THUMBNAIL + it },
      backdropPath = backdropPath?.let { IMAGE_URL + IMG_SIZE_BACKDROP + it },
      releaseDate = DateFormatter.stringDateToDisplayShortMonth(releaseDate),
      rating = NumberFormatter.toRateOf5(voteAverage),
      titleWithYear = "$title ($year)",
      simpleVoteCount = NumberFormatter.toSimpleCount(voteCount)
   )
}

fun MovieDetailDto.asDomainModel(): MovieDetail {
   val displayDate = DateFormatter.stringDateToDisplayShortMonth(releaseDate)
   val duration = NumberFormatter.toMovieDuration(runtime)
   return MovieDetail(
      id = id,
      adult = adult,
      backdropPath = backdropPath?.let { IMAGE_URL + IMG_SIZE_BACKDROP + it },
      genres = genres.map { it.name },
      overview = overview,
      posterPath = posterPath?.let { IMAGE_URL + IMG_SIZE_POSTER_DETAIL + it },
      posterFullPath = posterPath?.let { IMAGE_URL + IMG_SIZE_POSTER_FULL + it },
      tagline = if (!tagline.isNullOrBlank()) "\"${tagline}\"" else null,
      title = title,
      voteAverage = NumberFormatter.toOneDecimal(voteAverage),
      voteCount = NumberFormatter.toNominalFormat(voteCount),
      trailerKey = videos.results.asDomainModel(title),
      recommendations = recommendations.results.map { it.asDomainModel() },
      reviews = reviews.results.map { it.asDomainModel() },
      statusReleaseDateDuration = buildString {
         append(status)
         displayDate?.let { append(" · ").append(it) }
         duration?.let { append(" · ").append(it) }
      },
      voteAveragePercent = NumberFormatter.toPercent(voteAverage)
   )
}

fun MovieGenreDto.asDomainModel(): MovieGenre {
   return MovieGenre(
      id = id,
      name = name
   )
}


fun MovieReviewDto.asDomainModel(): MovieReview {
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

fun List<MovieTrailerDto>.asDomainModel(title: String): String? {
   return try {
      val trailer = first {
         it.site == "YouTube" && it.name.contains(title)
            || it.site == "YouTube" && it.type == "Trailer"
            || it.site == "YouTube"
      }
      trailer.key
   } catch (e: Exception) {
      null
   }
}