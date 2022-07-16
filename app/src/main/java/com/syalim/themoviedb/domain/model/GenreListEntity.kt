package com.syalim.themoviedb.domain.model


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
data class GenreListEntity(
   val genres: List<GenreItemEntity>? = emptyList()
)
