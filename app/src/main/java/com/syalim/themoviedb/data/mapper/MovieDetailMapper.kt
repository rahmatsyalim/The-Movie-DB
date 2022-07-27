package com.syalim.themoviedb.data.mapper

import com.syalim.themoviedb.data.remote.dto.movie_detail.MovieDetailDto
import com.syalim.themoviedb.domain.model.MovieDetailEntity


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
object MovieDetailMapper : BaseMapper<MovieDetailDto, MovieDetailEntity>() {
   override fun convert(dto: MovieDetailDto): MovieDetailEntity {
      return MovieDetailEntity(
         adult = dto.adult,
         backdropPath = dto.backdropPath,
         genres = dto.genres.map { it.name },
         id = dto.id,
         title = dto.title,
         overview = dto.overview,
         posterPath = dto.posterPath,
         releaseDate = dto.releaseDate,
         voteAverage = dto.voteAverage,
         runtime = dto.runtime,
         tagline = dto.tagline
      )
   }
}