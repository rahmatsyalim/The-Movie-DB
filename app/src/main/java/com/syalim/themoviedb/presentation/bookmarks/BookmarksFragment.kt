package com.syalim.themoviedb.presentation.bookmarks

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.syalim.themoviedb.R
import com.syalim.themoviedb.databinding.FragmentBookmarksBinding
import com.syalim.themoviedb.presentation.MainViewModel
import com.syalim.themoviedb.presentation.common.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


/**
 * Created by Rahmat Syalim on 2022/07/29
 * rahmatsyalim@gmail.com
 */

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class BookmarksFragment : Fragment(R.layout.fragment_bookmarks) {

   private val binding by viewBinding(FragmentBookmarksBinding::bind)

   private val viewModel: MainViewModel by activityViewModels()

}