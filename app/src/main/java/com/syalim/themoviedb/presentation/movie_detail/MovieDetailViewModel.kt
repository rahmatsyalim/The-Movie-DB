package com.syalim.themoviedb.presentation.movie_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.syalim.themoviedb.domain.use_case.movie_detail.*
import com.syalim.themoviedb.presentation.common.utils.asContentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */

@ExperimentalCoroutinesApi
@HiltViewModel
class MovieDetailViewModel @Inject constructor(
   private val getMovieDetailUseCase: GetMovieDetailUseCase,
   private val getMovieReviewsUseCase: GetMovieReviewsUseCase,
   private val getMovieTrailerUseCase: GetMovieTrailerUseCase,
   private val getRecommendationMoviesUseCase: GetRecommendationMoviesUseCase
) : ViewModel() {

   private val movieDetailEventLoadContents = MutableLiveData<MovieDetailEvent.LoadContents>()

   fun updateUiEvents(event: MovieDetailEvent) {
      when (event) {
         is MovieDetailEvent.LoadContents -> movieDetailEventLoadContents.postValue(event)
         is MovieDetailEvent.Bookmark -> {

         }
      }
   }

   val movieDetailState = movieDetailEventLoadContents.asFlow().flatMapLatest { event ->
      combine(
         getMovieDetailUseCase.invoke(event.id),
         getMovieTrailerUseCase.invoke(event.id),
         getRecommendationMoviesUseCase.invoke(event.id),
         getMovieReviewsUseCase.invoke(event.id).cachedIn(viewModelScope),
      ) { detail, trailer, recommendations, reviews ->
         MovieDetailUiState.Contents(
            detail.asContentState(),
            trailer.asContentState(),
            recommendations.asContentState(),
            reviews
         )
      }
   }.stateIn(
      viewModelScope,
      SharingStarted.Lazily,
      MovieDetailUiState.Default
   )

   private fun refresh() {
      movieDetailEventLoadContents.value?.let { params ->
         movieDetailEventLoadContents.postValue(MovieDetailEvent.LoadContents(params.id, true))
      }
   }

}