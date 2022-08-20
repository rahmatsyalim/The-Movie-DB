package com.syalim.themoviedb.domain.use_case.discover

import androidx.paging.PagingData
import com.syalim.themoviedb.domain.model.Movie
import com.syalim.themoviedb.domain.repository.MoviesRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */
@ViewModelScoped
class GetDiscoverMoviesUseCase @Inject constructor(
   private val repository: MoviesRepository
) {
   operator fun invoke(genre: String): Flow<PagingData<Movie>> {
      return repository.getDiscoverMovies(genre = genre)
   }
}