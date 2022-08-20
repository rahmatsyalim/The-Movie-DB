package com.syalim.themoviedb.domain.model

import java.util.*


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */

data class Movie(
   val id: Int,
   val title: String,
   val posterPath: String?,
   val backdropPath: String?,
   val releaseDate: Date?,
   val voteAverage: Double,
   val voteCount: Int,
   val extras: MovieExtras
)

data class MovieExtras(
   val titleWithYear: String,
   val voteAverageRating: Float,
   val simpleVoteCount: String
)