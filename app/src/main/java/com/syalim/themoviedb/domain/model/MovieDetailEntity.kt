package com.syalim.themoviedb.domain.model


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
data class MovieDetailEntity(
   val adult: Boolean,
   val backdropPath: String?,
   val genres: List<String>,
   val id: Int,
   val title: String?,
   val overview: String?,
   val posterPath: String?,
   val releaseDate: String?,
   val voteAverage: Double?,
   val runtime: Int?,
   val tagline: String?
)