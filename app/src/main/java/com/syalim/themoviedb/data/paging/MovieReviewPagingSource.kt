package com.syalim.themoviedb.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.syalim.themoviedb.common.Constants
import com.syalim.themoviedb.data.common.utils.getException
import com.syalim.themoviedb.data.mapper.asDomainMovieReview
import com.syalim.themoviedb.data.source.remote.MoviesRemoteDataSource
import com.syalim.themoviedb.domain.model.MovieReview
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */
class MovieReviewPagingSource(
   private val moviesRemoteDataSource: MoviesRemoteDataSource,
   private val dispatcher: CoroutineDispatcher,
   private val id: String
) : PagingSource<Int, MovieReview>() {

   override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieReview> {
      val currentKey = params.key ?: Constants.START_PAGE_INDEX
      return withContext(dispatcher) {
         try {
            val result = moviesRemoteDataSource
               .getMovieReviews(page = currentKey, id = id)
               .results.map { it.asDomainMovieReview() }
            LoadResult.Page(
               data = result,
               prevKey = if (currentKey == Constants.START_PAGE_INDEX) null else currentKey - 1,
               nextKey = if (result.isEmpty()) null else currentKey + 1
            )
         } catch (e: Exception) {
            LoadResult.Error(e.getException)
         }
      }
   }

   override fun getRefreshKey(state: PagingState<Int, MovieReview>): Int? {
      return state.anchorPosition?.let { anchorPosition ->
         state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
            ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
      }
   }
}