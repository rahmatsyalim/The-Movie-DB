package com.syalim.themoviedb.domain.repository

import androidx.paging.PagingData
import com.syalim.themoviedb.common.Resource
import com.syalim.themoviedb.domain.model.MovieItemEntity
import kotlinx.coroutines.flow.Flow


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
interface MovieRepository {

   fun getHomeUpcomingMovies(): Flow<Resource<List<MovieItemEntity>>>

   fun getHomePopularMovies(): Flow<Resource<List<MovieItemEntity>>>

   fun getHomeNowPlayingMovies(): Flow<Resource<List<MovieItemEntity>>>

   fun getHomeTopRatedMovies(): Flow<Resource<List<MovieItemEntity>>>

   fun getMoviesByGenre(genre: String?): Flow<PagingData<MovieItemEntity>>
}