package com.syalim.themoviedb.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.syalim.themoviedb.core.common.Constants.MOVIES_IN_THEATER
import com.syalim.themoviedb.core.common.Constants.MOVIES_POPULAR
import com.syalim.themoviedb.core.common.Constants.MOVIES_TOP_RATED
import com.syalim.themoviedb.core.common.Constants.MOVIES_UPCOMING
import com.syalim.themoviedb.core.common.IoDispatcher
import com.syalim.themoviedb.core.common.Result
import com.syalim.themoviedb.data.local.MoviesLocalDataSource
import com.syalim.themoviedb.data.remote.MoviesRemoteDataSource
import com.syalim.themoviedb.data.repository.mapper.asDomainModel
import com.syalim.themoviedb.data.repository.mapper.asLocalModel
import com.syalim.themoviedb.data.repository.paging.DiscoverMoviesPagingSource
import com.syalim.themoviedb.data.repository.paging.MovieReviewPagingSource
import com.syalim.themoviedb.data.repository.utils.cachedResourceStream
import com.syalim.themoviedb.data.repository.utils.resourceStream
import com.syalim.themoviedb.domain.movie.model.*
import com.syalim.themoviedb.domain.movie.repository.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */

class MoviesRepositoryImpl @Inject constructor(
   @IoDispatcher private val IoDispatcher: CoroutineDispatcher,
   private val remoteDataSource: MoviesRemoteDataSource,
   private val localDataSource: MoviesLocalDataSource
) : MoviesRepository {

   private companion object {
      const val PAGE_SIZE = 20
      const val PREFETCH_DISTANCE = 4
      const val INIT_LOAD_SIZE = 2 * PAGE_SIZE
      const val PLACE_HOLDER = false
   }

   override fun getHomeInTheaterMovies(): Flow<Result<Movies>> {
      val category = MOVIES_IN_THEATER
      return cachedResourceStream(
         fetchLocal = { localDataSource.getHomeMovies(category) },
         fetchRemote = { remoteDataSource.getInTheaterMovies(1) },
         cacheData = {
            localDataSource.deleteHomeMovies(category)
            localDataSource.insertHomeMovies(results.map { it.asLocalModel(category) })
         },
         mapToResult = { Movies(3, category, map { it.asDomainModel() }) },
         lastCachedTime = { first().updatedAt.time },
         isNoData = { isNullOrEmpty() }
      ).flowOn(IoDispatcher)
   }

   override fun getHomePopularMovies(): Flow<Result<Movies>> {
      val category = MOVIES_POPULAR
      return cachedResourceStream(
         fetchLocal = { localDataSource.getHomeMovies(category) },
         fetchRemote = { remoteDataSource.getPopularMovies(1) },
         cacheData = {
            localDataSource.deleteHomeMovies(category)
            localDataSource.insertHomeMovies(results.map { it.asLocalModel(category) })
         },
         mapToResult = { Movies(3, category, map { it.asDomainModel() }) },
         lastCachedTime = { first().updatedAt.time },
         isNoData = { isNullOrEmpty() }
      ).flowOn(IoDispatcher)
   }

   override fun getHomeTopRatedMovies(): Flow<Result<Movies>> {
      val category = MOVIES_TOP_RATED
      return cachedResourceStream(
         fetchLocal = { localDataSource.getHomeMovies(category) },
         fetchRemote = { remoteDataSource.getTopRatedMovies(1) },
         cacheData = {
            localDataSource.deleteHomeMovies(category)
            localDataSource.insertHomeMovies(results.map { it.asLocalModel(category) })
         },
         mapToResult = { Movies(3, category, map { it.asDomainModel() }) },
         lastCachedTime = { first().updatedAt.time },
         isNoData = { isNullOrEmpty() }
      ).flowOn(IoDispatcher)
   }

   override fun getHomeUpcomingMovies(): Flow<Result<Movies>> {
      val category = MOVIES_UPCOMING
      return cachedResourceStream(
         fetchLocal = { localDataSource.getHomeMovies(category) },
         fetchRemote = { remoteDataSource.getUpcomingMovies(1) },
         cacheData = {
            localDataSource.deleteHomeMovies(category)
            localDataSource.insertHomeMovies(results.map { it.asLocalModel(category) })
         },
         mapToResult = { Movies(3, category, map { it.asDomainModel() }) },
         lastCachedTime = { first().updatedAt.time },
         isNoData = { isNullOrEmpty() }
      ).flowOn(IoDispatcher)
   }

   override fun getDiscoverMovies(genre: String): Flow<PagingData<Movie>> =
      Pager(
         config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = PLACE_HOLDER,
            prefetchDistance = PREFETCH_DISTANCE,
            initialLoadSize = INIT_LOAD_SIZE
         ),
         pagingSourceFactory = { DiscoverMoviesPagingSource(remoteDataSource, localDataSource, IoDispatcher, genre) }
      ).flow

   override fun getMovieGenres(): Flow<Result<List<MovieGenre>>> =
      resourceStream {
         remoteDataSource.getMovieGenres().genres.map { it.asDomainModel() }
            .let { Result.Success(data = it) }
      }.flowOn(IoDispatcher)

   override fun getMovieDetail(id: String): Flow<Result<MovieDetail>> =
      resourceStream {
         val result = remoteDataSource.getMovieDetail(id).asDomainModel()
         Result.Success(result)
      }.flowOn(IoDispatcher)

   override fun getMovieReviews(id: String): Flow<PagingData<MovieReview>> =
      Pager(
         config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = PLACE_HOLDER,
            prefetchDistance = PREFETCH_DISTANCE,
            initialLoadSize = INIT_LOAD_SIZE
         ),
         pagingSourceFactory = { MovieReviewPagingSource(remoteDataSource, IoDispatcher, id) }
      ).flow

   override fun isMovieBookmarked(id: Int): Flow<Boolean> {
      return flow { emit(localDataSource.isMovieBookmarked(id)) }
   }
}