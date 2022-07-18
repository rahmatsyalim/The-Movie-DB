package com.syalim.themoviedb.data.paging.data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.syalim.themoviedb.common.Constants
import com.syalim.themoviedb.data.mapper.ReviewListMapper
import com.syalim.themoviedb.data.remote.network.MovieApi
import com.syalim.themoviedb.domain.model.ReviewItemEntity
import retrofit2.HttpException
import java.io.IOException


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */
class MovieReviewPagingSource(
   private val movieApi: MovieApi,
   private val id: String
): PagingSource<Int,ReviewItemEntity>() {

   override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReviewItemEntity> {
      val currentKey = params.key ?: Constants.START_PAGE_INDEX
      return try {
         val response = movieApi.getMovieReviews(page = currentKey, id = id)
         val result = ReviewListMapper.convert(response).results
         LoadResult.Page(
            data = result!!,
            prevKey = if (currentKey == Constants.START_PAGE_INDEX) null else currentKey - 1,
            nextKey = if (result.isEmpty()) null else currentKey + 1
         )
      } catch (e: IOException) {
         LoadResult.Error(e)
      } catch (e: HttpException) {
         LoadResult.Error(e)
      }
   }

   override fun getRefreshKey(state: PagingState<Int, ReviewItemEntity>): Int? {
      return state.anchorPosition?.let { anchorPosition ->
         state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
            ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
      }
   }
}