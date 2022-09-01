package com.syalim.themoviedb.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.syalim.themoviedb.databinding.ActivityMovieBinding
import com.syalim.themoviedb.presentation.utils.ext.isGone
import com.syalim.themoviedb.presentation.utils.ext.isVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieActivity : AppCompatActivity() {

   private val binding by lazy { ActivityMovieBinding.inflate(layoutInflater) }
   private val viewModel: MovieViewModel by viewModels()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(binding.root)

      observeConnectivityState()
   }

   private fun observeConnectivityState() {
      lifecycleScope.launch {
         viewModel.connectivityState.collectLatest { status ->
            status.message?.let {
               binding.tvNetworkState.apply {
                  text = getString(it)
                  isVisible()
               }
            } ?: binding.tvNetworkState.isGone()
         }
      }
   }
}