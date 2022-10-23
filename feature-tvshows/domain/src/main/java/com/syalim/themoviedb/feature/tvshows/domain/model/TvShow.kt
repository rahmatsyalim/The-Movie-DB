package com.syalim.themoviedb.feature.tvshows.domain.model


/**
 * Created by Rahmat Syalim on 2022/09/06
 * rahmatsyalim@gmail.com
 */

data class TvShow(
   val id: Int,
   val name: String,
   val posterPath: String?,
   val backdropPath: String?,
   val firstAirDate: String?,
   val rating: Float,
   val nameWithYear: String,
   val simpleVoteCount: String
)
