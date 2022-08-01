package com.syalim.themoviedb.data.mapper

import com.syalim.themoviedb.data.remote.dto.response.*
import com.syalim.themoviedb.domain.model.*


/**
 * Created by Rahmat Syalim on 2022/07/31
 * rahmatsyalim@gmail.com
 */

fun MovieListResponse.mapToEntity(): List<MovieItemEntity>? {
   return results?.map {
      MovieItemEntity(
         id = it.id,
         title = it.title,
         posterPath = it.posterPath,
         backdropPath = it.backdropPath,
         releaseDate = it.releaseDate,
      )
   }
}

fun MovieDetailResponse.mapToEntity(): MovieDetailEntity {
   return MovieDetailEntity(
      adult = adult,
      backdropPath = backdropPath,
      genres = genres.map { it.name },
      id = id,
      title = title,
      overview = overview,
      posterPath = posterPath,
      releaseDate = releaseDate,
      voteAverage = voteAverage,
      runtime = runtime,
      tagline = tagline
   )
}

fun GenreListResponse.mapToEntity(): List<GenreItemEntity>? {
   return genres?.map {
      GenreItemEntity(
         id = it.id,
         name = it.name
      )
   }
}

fun MovieTrailerListResponse.mapToEntity(): MovieTrailerEntity {
   val item = results?.first {
      it.type.equals("Trailer") && it.site.equals("YouTube")
   }
   return MovieTrailerEntity(
      key = item?.key,
      id = item?.id
   )
}

fun ReviewListResponse.mapToEntity(): List<ReviewItemEntity>? {
   return results?.map {
      ReviewItemEntity(
         author = it.author,
         avatarPath = it.authorDetails?.avatarPath,
         rating = it.authorDetails?.rating,
         content = it.content,
         id = it.id,
         updatedAt = it.updatedAt
      )
   }
}