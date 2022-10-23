package com.syalim.themoviedb.feature.movies.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.syalim.themoviedb.core.navigation.AppNavigator
import com.syalim.themoviedb.core.navigation.getElementTransitionName
import com.syalim.themoviedb.core.ui.getTransformTransition
import com.syalim.themoviedb.core.ui.utils.viewBinding
import com.syalim.themoviedb.feature.movies.ui.R
import com.syalim.themoviedb.feature.movies.ui.databinding.FragmentMovieSearchBinding
import dagger.hilt.android.AndroidEntryPoint


/**
 * Created by Rahmat Syalim on 2022/09/04
 * rahmatsyalim@gmail.com
 */

@AndroidEntryPoint
class MovieSearchFragment : Fragment(R.layout.fragment_movie_search) {

   private val binding: FragmentMovieSearchBinding by viewBinding()

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      setupTransition()
      initUi()
   }

   private fun setupTransition() {
      arguments.getElementTransitionName()?.let {
         sharedElementEnterTransition = requireContext().getTransformTransition(AppNavigator.navHostId)
         binding.etSearch.transitionName = it
      }
   }

   private fun initUi() {
      setupAppBar()
      binding.etSearch.requestFocus()
   }

   private fun setupAppBar() {
      binding.toolbar.setNavigationOnClickListener {
         findNavController().navigateUp()
      }
   }

}