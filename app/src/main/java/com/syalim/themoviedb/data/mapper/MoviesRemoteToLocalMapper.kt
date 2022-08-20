package com.syalim.themoviedb.data.mapper

import com.syalim.themoviedb.data.common.utils.DateFormatter
import com.syalim.themoviedb.data.source.local.entity.MovieEntity
import com.syalim.themoviedb.data.source.remote.dto.MovieDto


/**
 * Created by Rahmat Syalim on 2022/08/18
 * rahmatsyalim@gmail.com
 */

fun MovieDto.asLocalMovie(category: String): MovieEntity {
   return MovieEntity(
      id = id,
      title = title,
      posterPath = posterPath,
      backdropPath = backdropPath,
      releaseDate = DateFormatter.stringDateToDate(releaseDate),
      voteAverage = voteAverage,
      voteCount = voteCount,
      category = category
   )
}