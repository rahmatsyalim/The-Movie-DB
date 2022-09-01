package com.syalim.themoviedb.domain.movie.model


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
data class MovieReview(
   val author: String,
   val avatarPath: String?,
   val rating: Double?,
   val content: String,
   val id: String,
   val updatedAt: String,
)
