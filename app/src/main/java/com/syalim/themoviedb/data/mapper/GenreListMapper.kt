package com.syalim.themoviedb.data.mapper

import com.syalim.themoviedb.data.remote.dto.genre.GenreListDto
import com.syalim.themoviedb.domain.model.GenreItemEntity
import com.syalim.themoviedb.domain.model.GenreListEntity


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
object GenreListMapper : BaseMapper<GenreListDto, GenreListEntity>() {
   override fun convert(dto: GenreListDto): GenreListEntity {
      return GenreListEntity(
         genres = dto.genres?.map {
            GenreItemEntity(
               id = it.id,
               name = it.name
            )
         }
      )
   }
}