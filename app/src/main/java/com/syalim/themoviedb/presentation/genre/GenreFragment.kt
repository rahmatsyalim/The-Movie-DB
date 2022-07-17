package com.syalim.themoviedb.presentation.genre

import androidx.fragment.app.activityViewModels
import com.syalim.themoviedb.databinding.FragmentGenreBinding
import com.syalim.themoviedb.presentation.MainViewModel
import com.syalim.themoviedb.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


/**
 * Created by Rahmat Syalim on 2022/07/17
 * rahmatsyalim@gmail.com
 */

@AndroidEntryPoint
class GenreFragment: BaseFragment<FragmentGenreBinding>(FragmentGenreBinding::inflate) {

   private val viewModel: MainViewModel by activityViewModels()

   override fun init() {

   }
}