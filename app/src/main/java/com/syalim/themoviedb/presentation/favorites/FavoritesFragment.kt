package com.syalim.themoviedb.presentation.favorites

import androidx.fragment.app.activityViewModels
import com.syalim.themoviedb.databinding.FragmentFavoritesBinding
import com.syalim.themoviedb.presentation.MainViewModel
import com.syalim.themoviedb.presentation.base.BaseFragment


/**
 * Created by Rahmat Syalim on 2022/07/29
 * rahmatsyalim@gmail.com
 */
class FavoritesFragment: BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

   private val viewModel: MainViewModel by activityViewModels()

   override fun init() {

   }

}