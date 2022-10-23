package com.syalim.themoviedb.feature.movies.data.mapper

import com.syalim.themoviedb.core.common.Constants.IMAGE_URL
import com.syalim.themoviedb.core.common.Constants.IMG_SIZE_BACKDROP
import com.syalim.themoviedb.core.common.Constants.IMG_SIZE_POSTER_FULL
import com.syalim.themoviedb.core.common.Constants.IMG_SIZE_POSTER_THUMBNAIL
import com.syalim.themoviedb.feature.movies.domain.utils.MovieCategory
import com.syalim.themoviedb.core.database.movie.entity.MovieEntity
import com.syalim.themoviedb.core.database.movie.entity.MoviesBookmarkedEntity
import com.syalim.themoviedb.core.network.movie.dto.MovieDto


/**
 * Created by Rahmat Syalim on 2022/09/02
 * rahmatsyalim@gmail.com
 */

internal fun MovieDto.asLocalModel(category: String): MovieEntity {
   return MovieEntity(
      id = id,
      title = title,
      posterPath = posterPath?.let {
         IMAGE_URL + if (category == MovieCategory.IN_THEATER.param) {
            IMG_SIZE_POSTER_FULL
         } else {
            IMG_SIZE_POSTER_THUMBNAIL
         } + it
      },
      backdropPath = backdropPath?.let { IMAGE_URL + IMG_SIZE_BACKDROP + it },
      releaseDate = releaseDate,
      voteAverage = voteAverage,
      voteCount = voteCount,
      category = category
   )
}

internal fun MovieDto.asLocalModel(): MoviesBookmarkedEntity {
   return MoviesBookmarkedEntity(
      id = id,
      title = title,
      posterPath = posterPath?.let { IMAGE_URL + IMG_SIZE_POSTER_THUMBNAIL + it },
      backdropPath = backdropPath?.let { IMAGE_URL + IMG_SIZE_BACKDROP + it },
      releaseDate = releaseDate,
      voteAverage = voteAverage,
      voteCount = voteCount
   )
}