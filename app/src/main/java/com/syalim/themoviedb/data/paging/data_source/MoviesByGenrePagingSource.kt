package com.syalim.themoviedb.data.paging.data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.syalim.themoviedb.common.Constants.START_PAGE_INDEX
import com.syalim.themoviedb.data.mapper.MovieListMapper
import com.syalim.themoviedb.data.remote.network.MovieApi
import com.syalim.themoviedb.domain.model.MovieItemEntity
import retrofit2.HttpException
import java.io.IOException


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */
class MoviesByGenrePagingSource(
   private val api: MovieApi,
   private val genre: String?
) : PagingSource<Int, MovieItemEntity>() {

   override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItemEntity> {
      val currentKey = params.key ?: START_PAGE_INDEX
      return try {
         val response = api.getMoviesByGenre(page = currentKey, genres = genre)
         val result = MovieListMapper.convert(response).results
         LoadResult.Page(
            data = result!!,
            prevKey = if (currentKey == START_PAGE_INDEX) null else currentKey - 1,
            nextKey = if (result.isEmpty()) null else currentKey + 1
         )
      } catch (e: IOException) {
         LoadResult.Error(e)
      } catch (e: HttpException) {
         LoadResult.Error(e)
      }
   }

   override fun getRefreshKey(state: PagingState<Int, MovieItemEntity>): Int? {
      return state.anchorPosition?.let { anchorPosition ->
         state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
            ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
      }
   }

}