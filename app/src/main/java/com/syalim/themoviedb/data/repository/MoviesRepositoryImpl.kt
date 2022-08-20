package com.syalim.themoviedb.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.syalim.themoviedb.common.Constants.MOVIES_IN_THEATER
import com.syalim.themoviedb.common.Constants.MOVIES_POPULAR
import com.syalim.themoviedb.common.Constants.MOVIES_TOP_RATED
import com.syalim.themoviedb.common.Constants.MOVIES_UPCOMING
import com.syalim.themoviedb.common.Resource
import com.syalim.themoviedb.data.common.utils.cachedResourceStream
import com.syalim.themoviedb.data.common.utils.resourceStream
import com.syalim.themoviedb.data.mapper.*
import com.syalim.themoviedb.data.paging.DiscoverMoviesPagingSource
import com.syalim.themoviedb.data.paging.MovieReviewPagingSource
import com.syalim.themoviedb.data.source.local.MoviesLocalDataSource
import com.syalim.themoviedb.data.source.remote.MoviesRemoteDataSource
import com.syalim.themoviedb.di.IoDispatcher
import com.syalim.themoviedb.domain.model.*
import com.syalim.themoviedb.domain.repository.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */

class MoviesRepositoryImpl @Inject constructor(
   @IoDispatcher private val dispatcher: CoroutineDispatcher,
   private val remoteDataSource: MoviesRemoteDataSource,
   private val localDataSource: MoviesLocalDataSource
) : MoviesRepository {

   private companion object {
      const val PAGE_SIZE = 20
      const val PREFETCH_DISTANCE = 4
      const val INIT_LOAD_SIZE = 2 * PAGE_SIZE
      const val PLACE_HOLDER = false
   }

   override fun getHomeInTheaterMovies(): Flow<Resource<List<MovieCarousel>>> {
      val category = MOVIES_IN_THEATER
      return cachedResourceStream(
         fetchLocal = { localDataSource.getMovies(category) },
         fetchRemote = { remoteDataSource.getInTheaterMovies(1) },
         clearLocal = { localDataSource.deleteMoviesByCategory(category) },
         insertLocal = {
            results.map { it.asLocalMovie(category) }.let { localDataSource.insertMovies(it) }
         },
         mapToResult = { map { it.asDomainMovieCarousel() } },
         lastCachedTime = { first().updatedAt.time },
         isEmpty = { isEmpty() }
      ).flowOn(dispatcher)
   }

   override fun getHomePopularMovies(): Flow<Resource<List<Movie>>> {
      val category = MOVIES_POPULAR
      return cachedResourceStream(
         fetchLocal = { localDataSource.getMovies(category) },
         fetchRemote = { remoteDataSource.getPopularMovies(1) },
         clearLocal = { localDataSource.deleteMoviesByCategory(category) },
         insertLocal = {
            results.map { it.asLocalMovie(category) }.let { localDataSource.insertMovies(it) }
         },
         mapToResult = { map { it.asDomainMovie() } },
         lastCachedTime = { first().updatedAt.time },
         isEmpty = { isEmpty() }
      ).flowOn(dispatcher)
   }

   override fun getHomeTopRatedMovies(): Flow<Resource<List<Movie>>> {
      val category = MOVIES_TOP_RATED
      return cachedResourceStream(
         fetchLocal = { localDataSource.getMovies(category) },
         fetchRemote = { remoteDataSource.getTopRatedMovies(1) },
         clearLocal = { localDataSource.deleteMoviesByCategory(category) },
         insertLocal = {
            results.map { it.asLocalMovie(category) }.let { localDataSource.insertMovies(it) }
         },
         mapToResult = { map { it.asDomainMovie() } },
         lastCachedTime = {
            Timber.d(first().updatedAt.time.toString())
            first().updatedAt.time
         },
         isEmpty = { isEmpty() }
      ).flowOn(dispatcher)
   }

   override fun getHomeUpcomingMovies(): Flow<Resource<List<Movie>>> {
      val category = MOVIES_UPCOMING
      return cachedResourceStream(
         fetchLocal = { localDataSource.getMovies(category) },
         fetchRemote = { remoteDataSource.getUpcomingMovies(1) },
         clearLocal = { localDataSource.deleteMoviesByCategory(category) },
         insertLocal = {
            results.map { it.asLocalMovie(category) }.let { localDataSource.insertMovies(it) }
         },
         mapToResult = { map { it.asDomainMovie() } },
         lastCachedTime = { first().updatedAt.time },
         isEmpty = { isEmpty() }
      ).flowOn(dispatcher)
   }

   override fun getDiscoverMovies(genre: String): Flow<PagingData<Movie>> =
      Pager(
         config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = PLACE_HOLDER,
            prefetchDistance = PREFETCH_DISTANCE,
            initialLoadSize = INIT_LOAD_SIZE
         ),
         pagingSourceFactory = { DiscoverMoviesPagingSource(remoteDataSource, localDataSource, dispatcher, genre) }
      ).flow

   override fun getMovieGenres(): Flow<Resource<List<MovieGenre>>> =
      resourceStream {
         remoteDataSource.getMovieGenres().genres.map { it.asDomainMovieGenre() }
            .let { Resource.Success(data = it, empty = it.isEmpty()) }
      }.flowOn(dispatcher)

   override fun getMovieDetail(id: String): Flow<Resource<MovieDetail>> =
      resourceStream {
         val result = remoteDataSource.getMovieDetail(id).asDomainMovieDetail()
         Resource.Success(result)
      }.flowOn(dispatcher)

   override fun getMovieReviews(id: String): Flow<PagingData<MovieReview>> =
      Pager(
         config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = PLACE_HOLDER,
            prefetchDistance = PREFETCH_DISTANCE,
            initialLoadSize = INIT_LOAD_SIZE
         ),
         pagingSourceFactory = { MovieReviewPagingSource(remoteDataSource, dispatcher, id) }
      ).flow

   override fun getMovieTrailer(id: String): Flow<Resource<MovieTrailer>> =
      resourceStream {
         remoteDataSource.getMovieTrailer(id).results
            .let { Resource.Success(data = it.asDomainMovieTrailer(), empty = it.isEmpty()) }
      }.flowOn(dispatcher)

   override fun getRecommendationMovies(id: String): Flow<Resource<List<Movie>>> =
      resourceStream {
         remoteDataSource.getRecommendationMovies(id).results.map { it.asDomainMovie() }
            .let { Resource.Success(data = it, empty = it.isEmpty()) }
      }.flowOn(dispatcher)

   override fun isMovieBookmarked(id: Int): Flow<Boolean> {
      return flow { emit(localDataSource.isMovieBookmarked(id)) }
   }
}