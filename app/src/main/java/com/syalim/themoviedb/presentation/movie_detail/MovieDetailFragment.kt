package com.syalim.themoviedb.presentation.movie_detail

import android.content.res.ColorStateList
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.syalim.themoviedb.R
import com.syalim.themoviedb.common.convertToTimeDuration
import com.syalim.themoviedb.common.dateToViewDate
import com.syalim.themoviedb.common.setImage
import com.syalim.themoviedb.common.setImageUrl
import com.syalim.themoviedb.databinding.FragmentMovieDetailBinding
import com.syalim.themoviedb.presentation.MainActivity
import com.syalim.themoviedb.presentation.PagingLoadState
import com.syalim.themoviedb.presentation.State
import com.syalim.themoviedb.presentation.adapter.ReviewsPagerAdapter
import com.syalim.themoviedb.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
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

   private lateinit var reviewsAdapter: ReviewsPagerAdapter

   override fun init() {

      binding.topBar.setNavigationOnClickListener {
         findNavController().navigateUp()
      }

      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.loadDetails(id = arg.id)
      }

      collectState()

      collectDetailState()

      collectTrailerState()

      setRecyclerViewReviews()

      reviewsLoadStateListener()

      initYoutubePlayer()

   }

   private fun collectState() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.detailState.collect { state ->
            State.Handle(state)(
               onLoading = {
                  if (it) progressBar.isVisible = true
                  if (!it) collectReviews()
               }
            )
         }
      }
   }

   private fun reviewsLoadStateListener() {
      PagingLoadState(reviewsAdapter)(
         onError = {
            binding.tvInfoReviews.isVisible = true
            binding.tvInfoReviews.text = it
         },
         onEmpty = {
            binding.tvInfoReviews.isVisible = true
            binding.tvInfoReviews.text = "No reviews yet"
         },
         onSuccess = {
            binding.tvInfoReviews.isVisible = false
         }
      )
   }

   private fun setRecyclerViewReviews() {
      reviewsAdapter = ReviewsPagerAdapter()
      binding.rvReviews.adapter = reviewsAdapter
   }

   private fun collectReviews() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.movieReviews.collectLatest { pagingData ->
            reviewsAdapter.submitData(pagingData)
         }
      }
   }

   private fun collectDetailState() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.movieDetailState.collectLatest { state ->
            State.Handle(state)(
               onError = {
                  binding.tvInfoDetail.isVisible = true
                  progressBar.isVisible = false
                  binding.tvInfoDetail.text = it
               },
               onSuccess = { data ->
                  binding.tvInfoDetail.isVisible = false
                  binding.fabTrailer.setOnClickListener {
                     binding.viewTrailer.isVisible = !binding.viewTrailer.isVisible
                     with(binding.fabTrailer) {
                        if (text == "Show Trailer") {
                           text = "Hide Trailer"
                           icon = resources.getDrawable(R.drawable.ic_arrow_circle_up, requireContext().theme)
                        } else {
                           text = "Show Trailer"
                           icon = resources.getDrawable(R.drawable.ic_arrow_circle_down, requireContext().theme)
                        }
                     }
                  }
                  binding.fabFavorite.setOnClickListener {
                     // TODO: save to db
                     binding.fabFavorite.imageTintList = ColorStateList.valueOf(
                        resources.getColor(
                           R.color.red,
                           requireContext().theme
                        )
                     )
                  }
                  viewLifecycleOwner.lifecycleScope.launch {
                     binding.topBar.title = data.title
                     binding.tvTitle.text = data.title
                     binding.tvReleaseGenreDuration.text =
                        "${data.releaseDate.dateToViewDate()} · " +
                           "${data.genres.joinToString(", ")} · " +
                           data.runtime.convertToTimeDuration()
                     data.tagline?.let {
                        binding.tvTagline.text = "\"$it\""
                        binding.tvTagline.isVisible = true
                     }
                     binding.tvDesc.text = data.overview
                     data.posterPath?.let {
                        binding.ivPoster.setImage(it.setImageUrl())
                     }
                     data.backdropPath?.let {
                        binding.ivBackdrop.setImage(it.setImageUrl())
                     }
                     delay(300)
                     progressBar.isVisible = false
                     binding.fabFavorite.show()
                     binding.fabTrailer.show()
                     binding.viewContainer.isVisible = true
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
               onSuccess = { data ->
                  binding.youtubePlayerView.addYouTubePlayerListener(object :
                     AbstractYouTubePlayerListener() {
                     override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.cueVideo(data.key, 0f)
                     }

                     override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {
                        super.onVideoId(youTubePlayer, videoId)
                        binding.progressBarTrailer.isVisible = false
                     }

                     override fun onError(
                        youTubePlayer: YouTubePlayer,
                        error: PlayerConstants.PlayerError
                     ) {
                        super.onError(youTubePlayer, error)
                        binding.progressBarTrailer.isVisible = false
                     }
                  })
               }
            )
         }
      }
   }

   private fun initYoutubePlayer() {
      viewLifecycleOwner.lifecycle.addObserver(binding.youtubePlayerView)
   }
}