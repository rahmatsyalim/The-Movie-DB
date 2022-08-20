package com.syalim.themoviedb.domain.use_case.movie_detail

import androidx.paging.PagingData
import com.syalim.themoviedb.domain.model.MovieReview
import com.syalim.themoviedb.domain.repository.MoviesRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */
@ViewModelScoped
class GetMovieReviewsUseCase @Inject constructor(
   private val repository: MoviesRepository
) {
   operator fun invoke(id: String): Flow<PagingData<MovieReview>> {
      return repository.getMovieReviews(id)
   }
}