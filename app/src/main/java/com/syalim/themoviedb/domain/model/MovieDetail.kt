package com.syalim.themoviedb.domain.model

import java.util.*

/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */

data class MovieDetail(
   val id: Int,
   val adult: Boolean,
   val backdropPath: String?,
   val genres: List<String>,
   val overview: String?,
   val posterPath: String?,
   val releaseDate: Date?,
   val runtime: Int?,
   val status: String,
   val tagline: String?,
   val title: String,
   val voteAverage: Double,
   val voteCount: Int,
   val extras: MovieDetailExtras
)

data class MovieDetailExtras(
   val titleWithYear: String?,
   val releaseDateGenresRuntime:String,
   val voteAveragePercent: Int,
   val taglineWithQuote: String?,
   val isBookmarked: Boolean
)