package com.syalim.themoviedb.data.mapper

import com.syalim.themoviedb.data.remote.dto.movie_trailer.MovieTrailerListDto
import com.syalim.themoviedb.domain.model.MovieTrailerEntity


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
object MovieTrailerMapper : BaseMapper<MovieTrailerListDto, MovieTrailerEntity>() {
   override fun convert(dto: MovieTrailerListDto): MovieTrailerEntity {
      val item = dto.results?.first {
         it.type.equals("Trailer") && it.site.equals("YouTube")
      }
      return MovieTrailerEntity(
         key = item?.key,
         id = item?.id
      )
   }
}