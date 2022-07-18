package com.syalim.themoviedb.domain.use_case.get_movies_by_genre_use_case

import androidx.paging.PagingData
import com.syalim.themoviedb.domain.model.MovieItemEntity
import com.syalim.themoviedb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */
class GetMoviesByGenreUseCase @Inject constructor(
   private val repository: MovieRepository
) {
   operator fun invoke(genre: String?): Flow<PagingData<MovieItemEntity>> {
      return repository.getMoviesByGenre(genre = genre)
   }
}