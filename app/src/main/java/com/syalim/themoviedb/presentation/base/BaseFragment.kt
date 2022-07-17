package com.syalim.themoviedb.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


/**
 * Created by Rahmat Syalim on 2022/07/16
 * rahmatsyalim@gmail.com
 */
abstract class BaseFragment<T : ViewBinding>(
   private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> T
) : Fragment() {

   private var _binding: T? = null
   protected val binding get() = _binding!!

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      _binding = inflate.invoke(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      init()
   }

   override fun onDestroy() {
      super.onDestroy()
      _binding = null
   }

   abstract fun init()

}