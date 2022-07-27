package com.syalim.themoviedb.domain.model


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
data class MovieDetailEntity(
   val adult: Boolean,
   val backdropPath: String? = null,
   val genres: List<String>,
   val id: Int,
   val title: String,
   val overview: String? = "No description.",
   val posterPath: String? = null,
   val releaseDate: String,
   val voteAverage: Double? = 0.0,
   val runtime: Int? = null,
   val tagline: String? = null
)