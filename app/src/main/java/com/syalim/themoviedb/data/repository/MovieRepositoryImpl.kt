package com.syalim.themoviedb.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.syalim.themoviedb.data.mapper.mapToEntity
import com.syalim.themoviedb.data.paging.data_source.DiscoverMoviesPagingSource
import com.syalim.themoviedb.data.paging.data_source.MovieReviewPagingSource
import com.syalim.themoviedb.data.remote.api.MovieApi
import com.syalim.themoviedb.domain.model.*
import com.syalim.themoviedb.domain.repository.MovieRepository
import com.syalim.themoviedb.utils.Constants.NETWORK_ERROR_IO_EXCEPTION
import com.syalim.themoviedb.utils.Resource
import com.syalim.themoviedb.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
class MovieRepositoryImpl @Inject constructor(
   private val movieApi: MovieApi
) : MovieRepository {

   companion object {
      // Paging config
      const val PAGE_SIZE = 20
      const val PREFETCH_DISTANCE = 4
      const val INIT_LOAD_SIZE = 1 * PAGE_SIZE
      const val PLACE_HOLDER = false
   }

   override fun getHomeUpcomingMovies(): Flow<Resource<List<MovieItemEntity>>> {
      return handleHttpRequest { movieApi.getUpcomingMovies(page = 1).mapToEntity() }
   }

   override fun getHomePopularMovies(): Flow<Resource<List<MovieItemEntity>>> {
      return handleHttpRequest { movieApi.getPopularMovies(page = 1).mapToEntity() }
   }

   override fun getHomeNowPlayingMovies(): Flow<Resource<List<MovieItemEntity>>> {
      return handleHttpRequest { movieApi.getNowPlayingMovies(page = 1).mapToEntity() }
   }

   override fun getHomeTopRatedMovies(): Flow<Resource<List<MovieItemEntity>>> {
      return handleHttpRequest { movieApi.getTopRatedMovies(page = 1).mapToEntity() }
   }

   override fun getDiscoverMovies(genre: String?): Flow<PagingData<MovieItemEntity>> {
      return Pager(
         config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = PLACE_HOLDER,
            prefetchDistance = PREFETCH_DISTANCE,
            initialLoadSize = INIT_LOAD_SIZE
         ),
         pagingSourceFactory = { DiscoverMoviesPagingSource(movieApi = movieApi, genre = genre) }
      ).flow
   }

   override fun getMovieGenres(): Flow<Resource<List<GenreItemEntity>>> {
      return handleHttpRequest { movieApi.getMovieGenres().mapToEntity() }
   }

   override fun getMovieDetail(id: String): Flow<Resource<MovieDetailEntity>> {
      return handleHttpRequest { movieApi.getMovieDetails(id = id).mapToEntity() }
   }

   override fun getMovieReviews(id: String): Flow<PagingData<ReviewItemEntity>> {
      return Pager(
         config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = PLACE_HOLDER,
            prefetchDistance = PREFETCH_DISTANCE,
            initialLoadSize = INIT_LOAD_SIZE
         ),
         pagingSourceFactory = { MovieReviewPagingSource(movieApi = movieApi, id = id) }
      ).flow
   }

   override fun getMovieTrailer(id: String): Flow<Resource<MovieTrailerEntity>> {
      return handleHttpRequest { movieApi.getMovieTrailer(id = id).mapToEntity() }
   }

   override fun getRecommendationMovies(id: String): Flow<Resource<List<MovieItemEntity>>> {
      return handleHttpRequest { movieApi.getRecommendationMovies(id = id, page = 1).mapToEntity() }
   }

   private fun <T> handleHttpRequest(request: suspend () -> T?): Flow<Resource<T>> {
      return flow {
         try {
            val result = request()
            emit(Resource.Success(result))
         } catch (e: HttpException) {
            emit(Resource.Error(Utils.getHttpErrorMessage(e)))
         } catch (e: IOException) {
            emit(Resource.Error(NETWORK_ERROR_IO_EXCEPTION))
         }
      }.onStart { emit(Resource.Loading()) }.flowOn(Dispatchers.IO)
   }

}