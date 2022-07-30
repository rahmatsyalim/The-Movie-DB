package com.syalim.themoviedb.domain.use_case.get_movie_detail_use_case

import com.syalim.themoviedb.utils.Resource
import com.syalim.themoviedb.domain.model.MovieDetailEntity
import com.syalim.themoviedb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */
class GetMovieDetailUseCase @Inject constructor(
   private val repository: MovieRepository
) {
   operator fun invoke(id: String): Flow<Resource<MovieDetailEntity>> {
      return repository.getMovieDetail(id = id)
   }
}