package com.syalim.themoviedb.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.syalim.themoviedb.common.Constants.START_PAGE_INDEX
import com.syalim.themoviedb.data.common.utils.getException
import com.syalim.themoviedb.data.mapper.asDomainMovie
import com.syalim.themoviedb.data.source.local.MoviesLocalDataSource
import com.syalim.themoviedb.data.source.remote.MoviesRemoteDataSource
import com.syalim.themoviedb.domain.model.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */
class DiscoverMoviesPagingSource(
   private val moviesRemoteDataSource: MoviesRemoteDataSource,
   private val localDataSource: MoviesLocalDataSource,
   private val dispatcher: CoroutineDispatcher,
   private val genre: String
) : PagingSource<Int, Movie>() {

   override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
      val currentKey = params.key ?: START_PAGE_INDEX
      return withContext(dispatcher) {
         try {
            val result = moviesRemoteDataSource
               .getDiscoverMovies(page = currentKey, genre = genre)
               .results.map { it.asDomainMovie() }
            LoadResult.Page(
               data = result,
               prevKey = if (currentKey == START_PAGE_INDEX) null else currentKey - 1,
               nextKey = if (result.isEmpty()) null else currentKey + 1
            )
         } catch (e: Exception) {
            LoadResult.Error(e.getException)
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