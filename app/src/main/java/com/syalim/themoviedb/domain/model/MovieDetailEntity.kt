package com.syalim.themoviedb.domain.model


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
data class MovieDetailEntity(
   val adult: Boolean? = false,
   val backdropPath: String? = "",
   val genres: List<String>? = listOf("Unknown"),
   val id: Int,
   val originalTitle: String? = "Unknown",
   val overview: String? = "No description",
   val posterPath: String? = "",
   val productionCountries: List<String>? = listOf("Unknown"),
   val releaseDate: String? = "TBA",
   val status: String? = "Unknown",
   val voteAverage: Double? = 0.0
)