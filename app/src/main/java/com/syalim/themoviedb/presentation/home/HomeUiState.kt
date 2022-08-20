package com.syalim.themoviedb.presentation.home

import com.syalim.themoviedb.domain.model.Movie
import com.syalim.themoviedb.domain.model.MovieCarousel
import com.syalim.themoviedb.presentation.common.ContentState


/**
 * Created by Rahmat Syalim on 2022/08/08
 * rahmatsyalim@gmail.com
 */


sealed interface HomeUiState {
   object Default : HomeUiState
   data class Contents(
      val isRefreshing: Boolean,
      val inTheaterContentState: ContentState<List<MovieCarousel>>,
      val popularContentState: ContentState<List<Movie>>,
      val topRatedContentState: ContentState<List<Movie>>,
      val upcomingContentState: ContentState<List<Movie>>
   ) : HomeUiState

   sealed interface Events {
      data class LoadContents(val refresh: Boolean = false) : Events
   }
}
