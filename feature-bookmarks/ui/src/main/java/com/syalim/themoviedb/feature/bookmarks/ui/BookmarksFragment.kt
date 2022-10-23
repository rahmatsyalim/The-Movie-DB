package com.syalim.themoviedb.feature.bookmarks.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.syalim.themoviedb.core.ui.getAxisXTransition
import com.syalim.themoviedb.core.ui.utils.viewBinding
import com.syalim.themoviedb.feature.bookmarks.ui.databinding.FragmentBookmarksBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * Created by Rahmat Syalim on 2022/07/29
 * rahmatsyalim@gmail.com
 */

@AndroidEntryPoint
class BookmarksFragment : Fragment(R.layout.fragment_bookmarks) {

   private val binding: FragmentBookmarksBinding by viewBinding()

   private val viewModel: BookmarksViewModel by viewModels()

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      setupTransition()
   }

   private fun setupTransition() {
      enterTransition = requireContext().getAxisXTransition(true)
      returnTransition = requireContext().getAxisXTransition(false)
      exitTransition = requireContext().getAxisXTransition(false)
      reenterTransition = requireContext().getAxisXTransition(true)
   }

}