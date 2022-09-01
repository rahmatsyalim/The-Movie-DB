package com.syalim.themoviedb.domain.movie.model

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
   val posterFullPath: String?,
   val tagline: String?,
   val title: String,
   val voteAverage: Double,
   val voteCount: String,
   val trailerKey: String?,
   val recommendations: List<Movie>,
   val reviews: List<MovieReview>,
   val statusReleaseDateDuration: String,
   val voteAveragePercent: Int
)