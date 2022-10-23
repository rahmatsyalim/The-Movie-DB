package com.syalim.themoviedb.feature.bookmarks.ui

import androidx.lifecycle.ViewModel
import com.syalim.themoviedb.feature.bookmarks.domain.usecase.GetBookmarkedMovies
import com.syalim.themoviedb.feature.bookmarks.domain.usecase.RemoveMovieBookmark
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/09/03
 * rahmatsyalim@gmail.com
 */

@HiltViewModel
class BookmarksViewModel @Inject constructor(
   private val getBookmarkedMovies: GetBookmarkedMovies,
   private val removeMovieBookmark: RemoveMovieBookmark
) : ViewModel() {

}