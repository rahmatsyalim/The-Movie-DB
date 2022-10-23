package com.syalim.themoviedb.feature.tvshows.ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.syalim.themoviedb.feature.tvshows.domain.usecase.discover.GetDiscoverTvShows
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/09/06
 * rahmatsyalim@gmail.com
 */

@HiltViewModel
class DiscoverTvShowsViewModel @Inject constructor(
   private val getDiscoverTvShows: GetDiscoverTvShows
) : ViewModel() {

   val discoverTvShowsState = getDiscoverTvShows.invoke().cachedIn(viewModelScope)
}