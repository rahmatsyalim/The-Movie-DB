package com.syalim.themoviedb.data.mapper

import com.syalim.themoviedb.data.remote.dto.movie_review.ReviewListDto
import com.syalim.themoviedb.domain.model.ReviewItemEntity
import com.syalim.themoviedb.domain.model.ReviewListEntity


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
object ReviewListMapper : BaseMapper<ReviewListDto, ReviewListEntity>() {
   override fun convert(dto: ReviewListDto): ReviewListEntity {
      return ReviewListEntity(
         results = dto.results?.map {
            ReviewItemEntity(
               author = it.author,
               avatarPath = it.authorDetails?.avatarPath,
               rating = it.authorDetails?.rating,
               content = it.content,
               id = it.id,
               updatedAt = it.updatedAt
            )
         }
      )
   }
}