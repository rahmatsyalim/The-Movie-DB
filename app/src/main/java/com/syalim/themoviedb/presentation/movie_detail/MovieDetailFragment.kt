package com.syalim.themoviedb.presentation.movie_detail

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.syalim.themoviedb.presentation.PagingLoadStateHandler
import com.syalim.themoviedb.common.setImage
import com.syalim.themoviedb.common.setImageUrl
import com.syalim.themoviedb.common.showToast
import com.syalim.themoviedb.databinding.FragmentMovieDetailBinding
import com.syalim.themoviedb.presentation.MainActivity
import com.syalim.themoviedb.presentation.State
import com.syalim.themoviedb.presentation.adapter.ReviewsPagerAdapter
import com.syalim.themoviedb.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */
@AndroidEntryPoint
class MovieDetailFragment :
   BaseFragment<FragmentMovieDetailBinding>(FragmentMovieDetailBinding::inflate) {

   private val viewModel: MovieDetailViewModel by viewModels()

   private val arg by navArgs<MovieDetailFragmentArgs>()

   private val progressBar by lazy { (requireActivity() as MainActivity).progrssBar }

   private val reviewsAdapter by lazy { ReviewsPagerAdapter() }

   override fun init() {

      binding.topBar.setNavigationOnClickListener {
         findNavController().navigateUp()
      }

      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.loadDetails(id = arg.id)
      }

      setRecyclerViewReviews()

      collectDetailState()

      collectState()

      collectTrailerState()

      reviewsLoadStateListener()

      initYoutubePlayer()

   }

   private fun collectState() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.detailState.collectLatest { state ->
            State.Handle(state)(
               onLoading = {
                  progressBar.isVisible = it
                  binding.viewContainer.isVisible = !it
               },
               onError = {

               },
               onEmpty = {

               },
               onSuccess = {

               }
            )
         }
      }
   }

   private fun reviewsLoadStateListener() {
      PagingLoadStateHandler(reviewsAdapter)(
         onFirstLoading = {
            binding.progressBar.isVisible = it
         },
         onLoading = {
            binding.progressBar.isVisible = it
         },
         onError = {
            it?.let { requireContext().showToast(it) }
         },
         onEmpty = {
            binding.tvInfoReviews.isVisible = it
            binding.tvInfoReviews.text = "No reviews yet"
         }
      )
   }

   private fun setRecyclerViewReviews() {
      binding.rvReviews.adapter = reviewsAdapter
      reviewsAdapter.collectReviews()
   }

   private fun ReviewsPagerAdapter.collectReviews() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.getMovieReview(id = arg.id).collectLatest { pagingData ->
            this@collectReviews.submitData(pagingData)
         }
      }
   }

   private fun collectDetailState() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.movieDetailState.collectLatest { state ->
            State.Handle(state)(
               onLoading = {

               },
               onError = { message ->
                  binding.tvInfoDetail.isVisible = message != null
                  message?.let { binding.tvInfoDetail.text = it }
               },
               onSuccess = { data ->
                  binding.tvTitle.text = data.originalTitle
                  binding.tvGenre.text = data.genres?.joinToString(", ")
                  binding.tvDesc.text = data.overview
                  data.posterPath?.let {
                     binding.ivPoster.setImage(it.setImageUrl())
                  }
                  data.backdropPath?.let {
                     binding.ivBackdrop.setImage(it.setImageUrl())
                  }
               }
            )
         }
      }
   }

   private fun collectTrailerState() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.movieTrailerState.collectLatest { state ->
            State.Handle(state)(
               onLoading = {
                  binding.youtubePlayerView.isVisible = !it
               },
               onError = { message ->

               },
               onEmpty = {

               },
               onSuccess = { data ->
                  data.key?.let {
                     binding.youtubePlayerView.addYouTubePlayerListener(object :
                        AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                           youTubePlayer.cueVideo(it, 0f)
                        }
                     })
                  }
               }
            )
         }
      }
   }

   private fun initYoutubePlayer() {
      viewLifecycleOwner.lifecycle.addObserver(binding.youtubePlayerView)
   }
}