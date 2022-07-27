package com.syalim.themoviedb.data.mapper

import com.syalim.themoviedb.data.remote.dto.movie_list.MovieListDto
import com.syalim.themoviedb.domain.model.MovieItemEntity
import com.syalim.themoviedb.domain.model.MovieListEntity


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
object MovieListMapper : BaseMapper<MovieListDto, MovieListEntity>() {
   override fun convert(dto: MovieListDto): MovieListEntity {
      return MovieListEntity(
         results = dto.results?.map {
            MovieItemEntity(
               id = it.id,
               title = it.title,
               posterPath = it.posterPath,
               backdropPath = it.backdropPath,
               releaseDate = it.releaseDate,
            )
         }
      )
   }
}