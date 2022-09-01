package com.syalim.themoviedb.data.repository.mapper

import com.syalim.themoviedb.core.common.Constants.IMAGE_URL
import com.syalim.themoviedb.core.common.Constants.IMG_SIZE_BACKDROP
import com.syalim.themoviedb.core.common.Constants.IMG_SIZE_POSTER_THUMBNAIL
import com.syalim.themoviedb.data.local.entity.MovieHomeEntity
import com.syalim.themoviedb.data.remote.dto.MovieDto


/**
 * Created by Rahmat Syalim on 2022/08/18
 * rahmatsyalim@gmail.com
 */

fun MovieDto.asLocalModel(category: String): MovieHomeEntity {
   return MovieHomeEntity(
      id = id,
      title = title,
      posterPath = posterPath?.let { IMAGE_URL + IMG_SIZE_POSTER_THUMBNAIL + it },
      backdropPath = backdropPath?.let { IMAGE_URL + IMG_SIZE_BACKDROP + it },
      releaseDate = releaseDate,
      voteAverage = voteAverage,
      voteCount = voteCount,
      category = category
   )
}