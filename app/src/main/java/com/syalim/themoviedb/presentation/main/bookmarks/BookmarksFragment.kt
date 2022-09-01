package com.syalim.themoviedb.presentation.main.bookmarks

import androidx.fragment.app.Fragment
import com.syalim.themoviedb.R
import com.syalim.themoviedb.databinding.FragmentBookmarksBinding
import com.syalim.themoviedb.presentation.utils.ext.viewBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * Created by Rahmat Syalim on 2022/07/29
 * rahmatsyalim@gmail.com
 */

@AndroidEntryPoint
class BookmarksFragment : Fragment(R.layout.fragment_bookmarks) {

   private val binding by viewBinding(FragmentBookmarksBinding::bind)

}