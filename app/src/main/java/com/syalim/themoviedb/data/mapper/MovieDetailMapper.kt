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
         genres = dto.genres?.map { it?.name ?: "Unknown" },
         id = dto.id,
         originalTitle = dto.originalTitle,
         overview = dto.overview,
         posterPath = dto.posterPath,
         productionCountries = dto.productionCountries?.map { it?.name ?: "Unknown" },
         releaseDate = dto.releaseDate,
         status = dto.status,
         voteAverage = dto.voteAverage
      )
   }
}