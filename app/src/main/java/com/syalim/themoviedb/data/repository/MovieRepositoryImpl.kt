package com.syalim.themoviedb.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.syalim.themoviedb.common.Resource
import com.syalim.themoviedb.common.getErrorMessage
import com.syalim.themoviedb.data.mapper.*
import com.syalim.themoviedb.data.paging.data_source.MovieReviewPagingSource
import com.syalim.themoviedb.data.paging.data_source.MoviesByGenrePagingSource
import com.syalim.themoviedb.data.remote.network.MovieApi
import com.syalim.themoviedb.domain.model.*
import com.syalim.themoviedb.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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
      const val PAGE_SIZE = 20
      const val PREFETCH_DISTANCE = 4
      const val INIT_LOAD_SIZE = 1 * PAGE_SIZE
      const val PLACE_HOLDER = false
   }

   override fun getHomeUpcomingMovies(): Flow<Resource<List<MovieItemEntity>>> {
      return flow {
         emit(Resource.Loading())
         try {
            val response = movieApi.getUpcomingMovies(page = 1)
            val result = MovieListMapper.convert(response).results
            emit(Resource.Success(result))
         } catch (e: HttpException) {
            emit(Resource.Error(e.getErrorMessage()))
         } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server"))
         }
      }.flowOn(Dispatchers.IO)
   }

   override fun getHomePopularMovies(): Flow<Resource<List<MovieItemEntity>>> {
      return flow {
         emit(Resource.Loading())
         try {
            val response = movieApi.getPopularMovies(page = 1)
            val result = MovieListMapper.convert(response).results
            emit(Resource.Success(result))
         } catch (e: HttpException) {
            emit(Resource.Error(e.getErrorMessage()))
         } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server"))
         }
      }.flowOn(Dispatchers.IO)
   }

   override fun getHomeNowPlayingMovies(): Flow<Resource<List<MovieItemEntity>>> {
      return flow {
         emit(Resource.Loading())
         try {
            val response = movieApi.getNowPlayingMovies(page = 1)
            val result = MovieListMapper.convert(response).results
            emit(Resource.Success(result))
         } catch (e: HttpException) {
            emit(Resource.Error(e.getErrorMessage()))
         } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server"))
         }
      }.flowOn(Dispatchers.IO)
   }

   override fun getHomeTopRatedMovies(): Flow<Resource<List<MovieItemEntity>>> {
      return flow {
         emit(Resource.Loading())
         try {
            val response = movieApi.getTopRatedMovies(page = 1)
            val result = MovieListMapper.convert(response).results
            emit(Resource.Success(result))
         } catch (e: HttpException) {
            emit(Resource.Error(e.getErrorMessage()))
         } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server"))
         }
      }.flowOn(Dispatchers.IO)
   }

   override fun getMoviesByGenre(genre: String?): Flow<PagingData<MovieItemEntity>> {
      return Pager(
         config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = PLACE_HOLDER,
            prefetchDistance = PREFETCH_DISTANCE,
            initialLoadSize = INIT_LOAD_SIZE
         ),
         pagingSourceFactory = { MoviesByGenrePagingSource(api = movieApi, genre = genre) }
      ).flow
   }

   override fun getMovieGenres(): Flow<Resource<List<GenreItemEntity>>> {
      return flow {
         emit(Resource.Loading())
         try {
            val response = movieApi.getMovieGenres()
            val result = GenreListMapper.convert(response).genres
            emit(Resource.Success(result))
         } catch (e: HttpException) {
            emit(Resource.Error(e.getErrorMessage()))
         } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server"))
         }
      }.flowOn(Dispatchers.IO)
   }

   override fun getMovieDetail(id: String): Flow<Resource<MovieDetailEntity>> {
      return flow {
         emit(Resource.Loading())
         try {
            val response = movieApi.getMovieDetails(id = id)
            val result = MovieDetailMapper.convert(response)
            emit(Resource.Success(result))
         } catch (e: HttpException) {
            emit(Resource.Error(e.getErrorMessage()))
         } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server"))
         }
      }.flowOn(Dispatchers.IO)
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
      return flow {
         emit(Resource.Loading())
         try {
            val response = movieApi.getMovieTrailer(id = id)
            val result = MovieTrailerMapper.convert(response)
            emit(Resource.Success(result))
         } catch (e: HttpException) {
            emit(Resource.Error(e.getErrorMessage()))
         } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server"))
         }
      }.flowOn(Dispatchers.IO)
   }

}