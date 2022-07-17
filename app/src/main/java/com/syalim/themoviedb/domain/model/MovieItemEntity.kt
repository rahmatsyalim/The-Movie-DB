package com.syalim.themoviedb.domain.model


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
data class MovieItemEntity(
   val adult: Boolean? = false,
   val id: Int,
   val originalTitle: String? = "Unknown",
   val posterPath: String? = null,
   val releaseDate: String? = "TBA",
   val voteAverage: Double? = 0.0,
   val overview: String? = "No description"
)
