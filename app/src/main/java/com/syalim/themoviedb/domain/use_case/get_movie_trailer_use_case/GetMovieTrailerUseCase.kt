package com.syalim.themoviedb.domain.use_case.get_movie_trailer_use_case

import com.syalim.themoviedb.common.Resource
import com.syalim.themoviedb.domain.model.MovieTrailerEntity
import com.syalim.themoviedb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */
class GetMovieTrailerUseCase @Inject constructor(
   private val repository: MovieRepository
) {
   operator fun invoke(id: String): Flow<Resource<MovieTrailerEntity>> {
      return repository.getMovieTrailer(id)
   }
}