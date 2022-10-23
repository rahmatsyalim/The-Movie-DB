package com.syalim.themoviedb.feature.movies.ui.discover

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.syalim.themoviedb.core.common.Result
import com.syalim.themoviedb.feature.movies.domain.usecase.discover.GetDiscoverMovies
import com.syalim.themoviedb.feature.movies.domain.usecase.discover.GetMovieGenres
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/09/05
 * rahmatsyalim@gmail.com
 */

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class DiscoverMoviesViewModel @Inject constructor(
   private val getDiscoverMovies: GetDiscoverMovies,
   getMovieGenres: GetMovieGenres
) : ViewModel() {

   val filterGenreState =
      getMovieGenres.invoke().map {
         when (it) {
            is Result.Success -> it.data
            is Result.Failure -> it.data
         }
      }.stateIn(
         viewModelScope,
         SharingStarted.Lazily,
         null
      )

   private val selectedGenreFilter = MutableLiveData(listOf<Int>())

   val discoverMoviesState = selectedGenreFilter.asFlow().flatMapLatest {
      getDiscoverMovies(it.joinToString(","))
   }.cachedIn(viewModelScope)

   fun getSelectedGenre() = selectedGenreFilter.value!!

   fun fetchDiscoverMovies(genreList: List<Int>) {
      selectedGenreFilter.postValue(genreList)
   }
}