package com.syalim.themoviedb.data.repository

import com.syalim.themoviedb.common.Resource
import com.syalim.themoviedb.common.getErrorMessage
import com.syalim.themoviedb.data.mapper.MovieListMapper
import com.syalim.themoviedb.data.remote.network.MovieApi
import com.syalim.themoviedb.domain.model.MovieItemEntity
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

}