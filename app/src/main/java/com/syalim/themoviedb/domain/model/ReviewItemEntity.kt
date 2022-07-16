package com.syalim.themoviedb.domain.model


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
data class ReviewItemEntity(
   val author: String? = "Unknown",
   val avatarPath: String? = "",
   val rating: Double? = 0.0,
   val content: String? = "",
   val id: String,
   val updatedAt: String? = null,
)
