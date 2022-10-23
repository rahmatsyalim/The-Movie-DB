package com.syalim.themoviedb.feature.movies.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.syalim.themoviedb.feature.movies.domain.utils.MovieCategory
import com.syalim.themoviedb.core.common.Result
import com.syalim.themoviedb.core.database.movie.MoviesLocalDataSource
import com.syalim.themoviedb.core.network.movie.MoviesRemoteDataSource
import com.syalim.themoviedb.core.network.utils.cachedResourceStream
import com.syalim.themoviedb.core.network.utils.resourceStream
import com.syalim.themoviedb.feature.movies.data.mapper.asDomainModel
import com.syalim.themoviedb.feature.movies.data.mapper.asLocalModel
import com.syalim.themoviedb.feature.movies.data.paging.DiscoverMoviesPagingSource
import com.syalim.themoviedb.feature.movies.data.paging.MoviesPagingSource
import com.syalim.themoviedb.feature.movies.domain.model.*
import com.syalim.themoviedb.feature.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn


/**
 * Created by Rahmat Syalim on 2022/09/02
 * rahmatsyalim@gmail.com
 */

internal class MoviesRepositoryImpl constructor(
   private val remoteDataSource: MoviesRemoteDataSource,
   private val localDataSource: MoviesLocalDataSource,
   private val ioDispatcher: CoroutineDispatcher
) : MoviesRepository {

   private companion object {
      const val PAGE_SIZE = 20
      const val PREFETCH_DISTANCE = 4
      const val INIT_LOAD_SIZE = 2 * PAGE_SIZE
      const val PLACE_HOLDER = false
   }

   override fun getMovies(category: MovieCategory): Flow<Result<MoviesCategorized>> {
      return cachedResourceStream(
         fetchLocal = { localDataSource.getMovies(category.param) },
         fetchRemote = { remoteDataSource.getMovies(category.param) },
         cacheData = {
            localDataSource.deleteMovies(category.param)
            localDataSource.insertMovies(results.map { it.asLocalModel(category.param) })
         },
         mapToResult = { MoviesCategorized(category, map { it.asDomainModel() }) },
         lastCachedTime = { first().updatedAt.time },
         isNoData = { isNullOrEmpty() }
      ).flowOn(ioDispatcher)
   }

   override fun getMoviesPaged(category: MovieCategory): Flow<PagingData<Movie>> {
      return Pager(
         config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = PLACE_HOLDER,
            prefetchDistance = PREFETCH_DISTANCE,
            initialLoadSize = INIT_LOAD_SIZE
         ),
         pagingSourceFactory = {
            MoviesPagingSource(
               remoteDataSource,
               ioDispatcher,
               category.param
            )
         }
      ).flow
   }

   override fun getDiscoverMovies(genre: String): Flow<PagingData<Movie>> {
      return Pager(
         config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = PLACE_HOLDER,
            prefetchDistance = PREFETCH_DISTANCE,
            initialLoadSize = INIT_LOAD_SIZE
         ),
         pagingSourceFactory = {
            DiscoverMoviesPagingSource(
               remoteDataSource,
               ioDispatcher,
               genre
            )
         }
      ).flow
   }

   override fun getMovieGenres(): Flow<Result<List<MovieGenre>>> {
      return resourceStream {
         remoteDataSource.getMovieGenres().genres.map { it.asDomainModel() }
            .let { Result.Success(data = it) }
      }.flowOn(ioDispatcher)
   }

   override fun getMovieDetails(id: String): Flow<Result<MovieDetails>> {
      return resourceStream {
         val result = remoteDataSource.getMovieDetails(id).asDomainModel()
         Result.Success(result)
      }.flowOn(ioDispatcher)
   }

   override fun getMovieReviews(id: String): Flow<PagingData<MovieReview>> {
      return Pager(
         config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = PLACE_HOLDER,
            prefetchDistance = PREFETCH_DISTANCE,
            initialLoadSize = INIT_LOAD_SIZE
         ),
         pagingSourceFactory = {
            com.syalim.themoviedb.feature.movies.data.paging.MovieReviewPagingSource(
               remoteDataSource,
               ioDispatcher,
               id
            )
         }
      ).flow
   }

   override suspend fun isMovieBookmarked(id: Int): Boolean {
      return localDataSource.isMovieBookmarked(id)
   }

}