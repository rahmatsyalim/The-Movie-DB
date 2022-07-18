package com.syalim.themoviedb.domain.use_case.get_movie_reviews_use_case

import androidx.paging.PagingData
import com.syalim.themoviedb.domain.model.ReviewItemEntity
import com.syalim.themoviedb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */
class GetMovieReviewsUseCase @Inject constructor(
   private val repository: MovieRepository
) {
   operator fun invoke(id: String): Flow<PagingData<ReviewItemEntity>>{
      return repository.getMovieReviews(id)
   }
}