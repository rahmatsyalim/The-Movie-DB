package com.syalim.themoviedb.feature.movies.ui.movies_by_category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.syalim.themoviedb.feature.movies.domain.utils.MovieCategory
import com.syalim.themoviedb.feature.movies.domain.usecase.movies_by_category.GetMoviesPaged
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/09/05
 * rahmatsyalim@gmail.com
 */

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class MoviesByCategoryViewModel @Inject constructor(
   private val getMoviesPaged: GetMoviesPaged
) : ViewModel() {

   private val categoryLivedata = MutableLiveData<MovieCategory>()

   val moviesByCategoryState = categoryLivedata.asFlow().flatMapLatest {
      getMoviesPaged.invoke(it)
   }.cachedIn(viewModelScope)

   fun fetchMoviesByCategory(category: MovieCategory) {
      categoryLivedata.postValue(category)
   }

   fun isStateDefault(): Boolean {
      return categoryLivedata.value == null
   }
}