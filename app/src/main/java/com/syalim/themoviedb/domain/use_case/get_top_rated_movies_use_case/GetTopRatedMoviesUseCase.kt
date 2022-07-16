package com.syalim.themoviedb.domain.use_case.get_top_rated_movies_use_case

import com.syalim.themoviedb.common.Resource
import com.syalim.themoviedb.domain.model.MovieItemEntity
import com.syalim.themoviedb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
class GetTopRatedMoviesUseCase @Inject constructor(
   private val repository: MovieRepository
) {
   operator fun invoke(): Flow<Resource<List<MovieItemEntity>>> {
      return repository.getHomeTopRatedMovies()
   }
}