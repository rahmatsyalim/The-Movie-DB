package com.syalim.themoviedb.domain.use_case.home

import com.syalim.themoviedb.common.Resource
import com.syalim.themoviedb.domain.model.MovieCarousel
import com.syalim.themoviedb.domain.repository.MoviesRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
@ViewModelScoped
class GetHomeInTheaterMoviesUseCase @Inject constructor(
   private val repository: MoviesRepository
) {
   operator fun invoke(): Flow<Resource<List<MovieCarousel>>> {
      return repository.getHomeInTheaterMovies()
   }
}