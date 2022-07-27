package com.syalim.themoviedb.domain.model


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
data class ReviewItemEntity(
   val author: String,
   val avatarPath: String? = null,
   val rating: Double? = null,
   val content: String,
   val id: String,
   val updatedAt: String,
)
