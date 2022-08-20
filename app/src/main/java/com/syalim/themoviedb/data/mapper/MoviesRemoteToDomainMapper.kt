package com.syalim.themoviedb.data.mapper

import com.syalim.themoviedb.data.common.utils.DateFormatter
import com.syalim.themoviedb.data.common.utils.NumberFormatter
import com.syalim.themoviedb.data.source.remote.dto.*
import com.syalim.themoviedb.domain.model.*


/**
 * Created by Rahmat Syalim on 2022/08/15
 * rahmatsyalim@gmail.com
 */

fun MovieDto.asDomainMovie(): Movie {
   val releaseDate = DateFormatter.stringDateToDate(releaseDate)
   return Movie(
      id = id,
      title = title,
      posterPath = posterPath,
      backdropPath = backdropPath,
      releaseDate = releaseDate,
      voteAverage = voteAverage,
      voteCount = voteCount,
      extras = MovieExtras(
         titleWithYear = "$title (${DateFormatter.dateYear(releaseDate)})",
         voteAverageRating = NumberFormatter.toRateOf5(voteAverage),
         simpleVoteCount = NumberFormatter.toSimpleCount(voteCount)
      )
   )
}

fun MovieDetailDto.asDomainMovieDetail(): MovieDetail {
   val date = DateFormatter.stringDateToDate(releaseDate)
   val year = DateFormatter.dateYear(date)
   val genres = genres.map { it.name }
   val genresString = genres.joinToString(", ")
   val duration = NumberFormatter.toMovieDuration(runtime)
   return MovieDetail(
      id = id,
      adult = adult,
      backdropPath = backdropPath,
      genres = genres,
      overview = overview,
      posterPath = posterPath,
      releaseDate = date,
      runtime = runtime,
      status = status,
      tagline = tagline,
      title = title,
      voteAverage = voteAverage,
      voteCount = voteCount,
      extras = MovieDetailExtras(
         titleWithYear = "$title ($year)",
         releaseDateGenresRuntime = listOf(
            DateFormatter.stringDateToStringDateDisplay(releaseDate),
            genresString,
            duration
         ).joinToString(" · "),
         voteAveragePercent = NumberFormatter.toPercent(voteAverage),
         taglineWithQuote = if (!tagline.isNullOrBlank()) "\"${tagline}\"" else null,
         isBookmarked = false
      )
   )
}

fun MovieGenreDto.asDomainMovieGenre(): MovieGenre {
   return MovieGenre(
      id = id,
      name = name
   )
}


fun MovieReviewDto.asDomainMovieReview(): MovieReview {
   val avatarPath = authorDetails.avatarPath?.let {
      if (it.contains("gravatar")) it.drop(1).plus("?=32")
      else it
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

fun List<MovieTrailerDto>.asDomainMovieTrailer(): MovieTrailer {
   return try {
      val trailer = first {
         (it.site == "YouTube" && it.official && (it.type == "Trailer" || it.type == "Teaser"))
            || (it.site == "YouTube" && it.official)
      }
      MovieTrailer(
         key = trailer.key,
         id = trailer.id
      )
   } catch (e: Exception) {
      MovieTrailer(
         key = null,
         id = null
      )
   }
}