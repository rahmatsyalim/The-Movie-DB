package com.syalim.themoviedb.presentation.main.discover

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.syalim.themoviedb.domain.movie.usecase.discover.GetDiscoverMoviesUseCase
import com.syalim.themoviedb.domain.movie.usecase.discover.GetMovieGenreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/08/28
 * rahmatsyalim@gmail.com
 */

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class DiscoverViewModel @Inject constructor(
   private val getDiscoverMoviesUseCase: GetDiscoverMoviesUseCase,
   getMovieGenreUseCase: GetMovieGenreUseCase,
) : ViewModel() {
   init {
      Timber.d("discover created")
   }

   val filterGenreState =
      getMovieGenreUseCase.invoke().map {
         when (it) {
            is com.syalim.themoviedb.core.common.Result.Success -> it.data
            is com.syalim.themoviedb.core.common.Result.Failure -> it.data
         }
      }
         .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            null
         )

   private val selectedGenreFilter = MutableLiveData(listOf<Int>())

   val discoverMoviesState = selectedGenreFilter.asFlow().flatMapLatest {
      getDiscoverMoviesUseCase(it.joinToString(","))
   }.cachedIn(viewModelScope)

   fun getSelectedGenre() = selectedGenreFilter.value!!

   fun fetchDiscoverMovies(genreList: List<Int>) {
      selectedGenreFilter.postValue(genreList)
   }
}