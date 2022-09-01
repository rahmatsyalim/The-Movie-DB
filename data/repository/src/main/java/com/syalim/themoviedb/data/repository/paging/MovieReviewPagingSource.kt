package com.syalim.themoviedb.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.syalim.themoviedb.core.common.AppException
import com.syalim.themoviedb.core.common.R
import com.syalim.themoviedb.data.remote.MoviesRemoteDataSource
import com.syalim.themoviedb.data.repository.mapper.asDomainModel
import com.syalim.themoviedb.data.repository.utils.getException
import com.syalim.themoviedb.domain.movie.model.MovieReview
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */
class MovieReviewPagingSource(
   private val moviesRemoteDataSource: MoviesRemoteDataSource,
   private val dispatcher: CoroutineDispatcher,
   private val id: String
) : PagingSource<Int, MovieReview>() {

   private companion object {
      const val START_PAGE_INDEX = 1
   }

   override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieReview> {
      val currentKey = params.key ?: START_PAGE_INDEX
      return withContext(dispatcher) {
         try {
            val result = moviesRemoteDataSource
               .getMovieReviews(page = currentKey, id = id)
               .results.map { it.asDomainModel() }
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

   override fun getRefreshKey(state: PagingState<Int, MovieReview>): Int? {
      return state.anchorPosition?.let { anchorPosition ->
         state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
            ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
      }
   }
}