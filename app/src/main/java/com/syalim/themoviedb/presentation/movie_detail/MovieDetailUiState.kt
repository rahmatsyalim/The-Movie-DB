package com.syalim.themoviedb.presentation.movie_detail

import androidx.paging.PagingData
import com.syalim.themoviedb.domain.model.Movie
import com.syalim.themoviedb.domain.model.MovieDetail
import com.syalim.themoviedb.domain.model.MovieReview
import com.syalim.themoviedb.domain.model.MovieTrailer
import com.syalim.themoviedb.presentation.common.ContentState


/**
 * Created by Rahmat Syalim on 2022/08/11
 * rahmatsyalim@gmail.com
 */

sealed interface MovieDetailUiState {
   object Default : MovieDetailUiState
   data class Contents(
      val detailData: ContentState<MovieDetail>,
      val trailerData: ContentState<MovieTrailer>,
      val recommendationData: ContentState<List<Movie>>,
      val reviewsData: PagingData<MovieReview>
   ) : MovieDetailUiState {
      val isLoading
         get() =
            setOf(detailData, trailerData, recommendationData).any { it is ContentState.Loading }
      val error: Throwable?
         get() = run {
            val allNotLoading =
               setOf(detailData, trailerData, recommendationData)
                  .none { it is ContentState.Loading }
            return when {
               detailData is ContentState.Error && allNotLoading -> detailData.cause
               trailerData is ContentState.Error && allNotLoading -> trailerData.cause
               recommendationData is ContentState.Error && allNotLoading -> recommendationData.cause
               else -> null
            }
         }
   }
}