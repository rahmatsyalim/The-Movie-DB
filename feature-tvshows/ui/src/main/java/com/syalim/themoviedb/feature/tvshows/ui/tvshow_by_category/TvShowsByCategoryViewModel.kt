package com.syalim.themoviedb.feature.tvshows.ui.tvshow_by_category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.syalim.themoviedb.feature.tvshows.domain.utils.TvShowCategory
import com.syalim.themoviedb.feature.tvshows.domain.usecase.tv_shows_by_category.GetTvShowsPaged
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/09/06
 * rahmatsyalim@gmail.com
 */

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TvShowsByCategoryViewModel @Inject constructor(
   private val getTvShowsPaged: GetTvShowsPaged
) : ViewModel() {

   private val categoryLivedata = MutableLiveData<TvShowCategory>()

   val tvShowByCategoryState = categoryLivedata.asFlow().flatMapLatest {
      getTvShowsPaged.invoke(it)
   }.cachedIn(viewModelScope)

   fun fetchTvShowsByCategory(categories: TvShowCategory) {
      categoryLivedata.postValue(categories)
   }

   fun isStateDefault(): Boolean {
      return categoryLivedata.value == null
   }
}