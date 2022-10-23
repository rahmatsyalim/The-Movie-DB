package com.syalim.themoviedb.feature.movies.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.syalim.themoviedb.core.common.AppException
import com.syalim.themoviedb.core.network.R
import com.syalim.themoviedb.core.network.movie.MoviesRemoteDataSource
import com.syalim.themoviedb.core.network.utils.getException
import com.syalim.themoviedb.feature.movies.data.mapper.asDomainModel
import com.syalim.themoviedb.feature.movies.domain.model.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */
internal class DiscoverMoviesPagingSource(
   private val moviesRemoteDataSource: MoviesRemoteDataSource,
   private val dispatcher: CoroutineDispatcher,
   private val genre: String
) : PagingSource<Int, Movie>() {

   private companion object {
      const val START_PAGE_INDEX = 1
   }

   override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
      val currentKey = params.key ?: START_PAGE_INDEX
      return withContext(dispatcher) {
         try {
            val result = moviesRemoteDataSource
               .getDiscoverMovies(page = currentKey, genre = genre).results
               .map { it.asDomainModel() }
            LoadResult.Page(
               data = result,
               prevKey = if (currentKey == START_PAGE_INDEX) null else currentKey - 1,
               nextKey = if (result.isEmpty()) null else currentKey + 1
            )
         } catch (e: HttpException) {
            LoadResult.Error(e.getException())
         } catch (e: IOException) {
            LoadResult.Error(AppException.StringResMsg(R.string.could_not_reach_server))
         }
      }
   }

   override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
      return state.anchorPosition?.let { anchorPosition ->
         state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
            ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
      }
   }

}