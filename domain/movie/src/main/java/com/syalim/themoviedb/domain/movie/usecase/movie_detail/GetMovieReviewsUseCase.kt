package com.syalim.themoviedb.domain.movie.usecase.movie_detail

import androidx.paging.PagingData
import com.syalim.themoviedb.domain.movie.model.MovieReview
import com.syalim.themoviedb.domain.movie.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/08/31
 * rahmatsyalim@gmail.com
 */

class GetMovieReviewsUseCase @Inject constructor(
   private val repository: MoviesRepository
) {
   operator fun invoke(id: String): Flow<PagingData<MovieReview>> {
      return repository.getMovieReviews(id)
   }
}