package com.syalim.themoviedb.feature.movies.domain.model


/**
 * Created by Rahmat Syalim on 2022/08/26
 * rahmatsyalim@gmail.com
 */

data class Movie(
   val id: Int,
   val title: String,
   val posterPath: String?,
   val backdropPath: String?,
   val releaseDate: String?,
   val rating: Float,
   val titleWithYear: String,
   val simpleVoteCount: String
)
