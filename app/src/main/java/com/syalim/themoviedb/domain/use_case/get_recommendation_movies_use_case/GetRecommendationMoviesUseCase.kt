package com.syalim.themoviedb.domain.use_case.get_recommendation_movies_use_case

import com.syalim.themoviedb.utils.Resource
import com.syalim.themoviedb.domain.model.MovieItemEntity
import com.syalim.themoviedb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/27
 * rahmatsyalim@gmail.com
 */
class GetRecommendationMoviesUseCase @Inject constructor(
   private val repository: MovieRepository
) {
   operator fun invoke(id: String): Flow<Resource<List<MovieItemEntity>>> {
      return repository.getRecommendationMovies(id = id)
   }
}