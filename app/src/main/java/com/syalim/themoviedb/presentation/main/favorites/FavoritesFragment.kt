package com.syalim.themoviedb.presentation.main.favorites

import androidx.fragment.app.activityViewModels
import com.syalim.themoviedb.databinding.FragmentFavoritesBinding
import com.syalim.themoviedb.presentation.common.base.BaseFragment
import com.syalim.themoviedb.presentation.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


/**
 * Created by Rahmat Syalim on 2022/07/29
 * rahmatsyalim@gmail.com
 */

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FavoritesFragment: BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

   private val viewModel: MainViewModel by activityViewModels()

   override fun init() {

   }

}