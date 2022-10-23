package com.syalim.themoviedb.feature.movies.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.syalim.themoviedb.feature.movies.ui.databinding.FragmentMovieTrailerBinding


/**
 * Created by Rahmat Syalim on 2022/09/08
 * rahmatsyalim@gmail.com
 */

class MovieTrailerDialogFragment : DialogFragment() {

   private lateinit var binding: FragmentMovieTrailerBinding
   var videoId: String? = null

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
      binding = FragmentMovieTrailerBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.ytPlayerView
         .Builder(requireActivity())
         .ytVideoId(videoId)
         .build()
   }

   override fun onStart() {
      super.onStart()
      dialog?.let {
         val width = ViewGroup.LayoutParams.MATCH_PARENT
         val height = ViewGroup.LayoutParams.WRAP_CONTENT
         it.window?.setLayout(width, height)
      }
   }
}