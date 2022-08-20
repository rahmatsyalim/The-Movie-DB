package com.syalim.themoviedb.domain.model


/**
 * Created by Rahmat Syalim on 2022/08/19
 * rahmatsyalim@gmail.com
 */
data class MovieCarousel(
   val id: Int,
   val title: String,
   val backdropPath: String?,
   val releaseDate: String?
)
