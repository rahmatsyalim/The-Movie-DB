package com.syalim.themoviedb.presentation.home

import androidx.fragment.app.activityViewModels
import com.syalim.themoviedb.databinding.FragmentHomeBinding
import com.syalim.themoviedb.presentation.MainViewModel
import com.syalim.themoviedb.presentation.base.BaseFragment


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */

class HomeFragment: BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

   private val viewModel: MainViewModel by activityViewModels()

   override fun init() {

   }

}