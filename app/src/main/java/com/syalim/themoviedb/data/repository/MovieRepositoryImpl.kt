package com.syalim.themoviedb.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.syalim.themoviedb.common.Constants.ERROR_NO_CONNECTION
import com.syalim.themoviedb.common.Resource
import com.syalim.themoviedb.common.getErrorMessage
import com.syalim.themoviedb.data.mapper.GenreListMapper
import com.syalim.themoviedb.data.mapper.MovieDetailMapper
import com.syalim.themoviedb.data.mapper.MovieListMapper
import com.syalim.themoviedb.data.mapper.MovieTrailerMapper
import com.syalim.themoviedb.data.paging.data_source.MovieReviewPagingSource
import com.syalim.themoviedb.data.paging.data_source.MoviesByGenrePagingSource
import com.syalim.themoviedb.data.remote.network.MovieApi
import com.syalim.themoviedb.domain.model.*
import com.syalim.themoviedb.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
      return handleRequest { MovieListMapper.convert(movieApi.getUpcomingMovies(page = 1)).results }
   }

   override fun getHomePopularMovies(): Flow<Resource<List<MovieItemEntity>>> {
      return handleRequest { MovieListMapper.convert(movieApi.getPopularMovies(page = 1)).results }
   }

   override fun getHomeNowPlayingMovies(): Flow<Resource<List<MovieItemEntity>>> {
      return handleRequest { MovieListMapper.convert(movieApi.getNowPlayingMovies(page = 1)).results }
   }

   override fun getHomeTopRatedMovies(): Flow<Resource<List<MovieItemEntity>>> {
      return handleRequest { MovieListMapper.convert(movieApi.getTopRatedMovies(page = 1)).results }
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
      return handleRequest { GenreListMapper.convert(movieApi.getMovieGenres()).genres }
   }

   override fun getMovieDetail(id: String): Flow<Resource<MovieDetailEntity>> {
      return handleRequest { MovieDetailMapper.convert(movieApi.getMovieDetails(id = id)) }
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
      return handleRequest { MovieTrailerMapper.convert(movieApi.getMovieTrailer(id = id)) }
   }

   override fun getRecommendationMovies(id: String): Flow<Resource<List<MovieItemEntity>>> {
      return handleRequest { MovieListMapper.convert(movieApi.getRecommendationMovies(id = id, page = 1)).results }
   }

   private fun <T>handleRequest(request: suspend () -> T?): Flow<Resource<T>>{
      return flow {
         emit(Resource.Loading())
         try {
            val result = request()
            emit(Resource.Success(result))
         } catch (e: HttpException) {
            emit(Resource.Error(e.getErrorMessage()))
         } catch (e: IOException) {
            emit(Resource.Error(ERROR_NO_CONNECTION))
         }
      }
   }

}