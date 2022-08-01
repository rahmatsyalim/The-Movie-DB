package com.syalim.themoviedb.domain.model


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
data class MovieItemEntity(
   val id: Int,
   val title: String,
   val posterPath: String?,
   val backdropPath: String?,
   val releaseDate: String?,
)
