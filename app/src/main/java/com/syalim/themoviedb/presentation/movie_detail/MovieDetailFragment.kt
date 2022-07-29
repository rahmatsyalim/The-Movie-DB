package com.syalim.themoviedb.presentation.movie_detail

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.syalim.themoviedb.R
import com.syalim.themoviedb.common.*
import com.syalim.themoviedb.common.Constants.IMAGE_POSTER_DETAIL_SIZE
import com.syalim.themoviedb.databinding.FragmentMovieDetailBinding
import com.syalim.themoviedb.presentation.PagingLoadState
import com.syalim.themoviedb.presentation.State
import com.syalim.themoviedb.presentation.adapter.HomeMoviesAdapter
import com.syalim.themoviedb.presentation.adapter.ReviewsPagerAdapter
import com.syalim.themoviedb.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieDetailFragment :
   BaseFragment<FragmentMovieDetailBinding>(FragmentMovieDetailBinding::inflate) {

   private val viewModel: MovieDetailViewModel by viewModels()

   private val arg by navArgs<MovieDetailFragmentArgs>()

   private lateinit var reviewsAdapter: ReviewsPagerAdapter

   private lateinit var recommendationMoviesAdapter: HomeMoviesAdapter

   override fun init() {

      binding.topBar.setNavigationOnClickListener {
         findNavController().navigateUp()
      }

      if (viewModel.detailScreenState.value is State.Default) {
         viewModel.loadDetails(id = arg.id, isFirstLoading = true)
         viewModel.setMovieId(arg.id)
      }

      setRecommendationMoviesRecyclerView()

      setReviewsRecyclerView()

      collectDetailScreenState()

      collectDetailState()

      collectTrailerState()

      collectRecommendationMovies()

      collectReviews()

      initYoutubePlayer()

   }

   private fun collectDetailScreenState() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.detailScreenState.collectLatest { state ->
            State.Handle(state)(
               onError = {
                  binding.root.showSnackBar(it, true)
               },
               onLoaded = {

               }
            )
         }
      }
   }

   private fun ReviewsPagerAdapter.setLoadStateListener() {
      PagingLoadState(this)(
         onError = {
            binding.tvInfoReviews.isVisible = true
            binding.tvInfoReviews.text = it
            binding.viewReviews.isVisible = true
         },
         onEmpty = {
            binding.tvInfoReviews.isVisible = true
            binding.tvInfoReviews.text = "No reviews."
            binding.viewReviews.isVisible = true
         },
         onLoaded = {
            binding.tvInfoReviews.isVisible = false
            binding.viewReviews.isVisible = true
         }
      )
   }

   private fun setReviewsRecyclerView() {
      reviewsAdapter = ReviewsPagerAdapter()
      binding.rvReviews.adapter = reviewsAdapter
      reviewsAdapter.setLoadStateListener()
   }

   private fun setRecommendationMoviesRecyclerView() {
      recommendationMoviesAdapter = HomeMoviesAdapter()
      with(recommendationMoviesAdapter) {
         binding.rvRecommendationMovies.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
         binding.rvRecommendationMovies.adapter = recommendationMoviesAdapter
         setOnItemClickListener {
            val bundle = Bundle().apply {
               putString("id", it.id.toString())
            }
            findNavController().navigate(R.id.action_movie_detail_fragment_self, bundle)
         }
      }
   }

   private fun collectReviews() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.movieReviews.collectLatest { pagingData ->
            reviewsAdapter.submitData(pagingData)
         }
      }
   }

   private fun collectRecommendationMovies() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.recommendationMoviesState.collectLatest { state ->
            State.Handle(state)(
               onError = {
                  binding.root.showSnackBar(it, true)
               },
               onLoaded = {
                  recommendationMoviesAdapter.data.submitList(it)
                  binding.viewRecommendationMovies.isVisible = true
               }
            )
         }
      }
   }

   private fun collectDetailState() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.movieDetailState.collectLatest { state ->
            State.Handle(state)(
               onError = {
                  binding.root.showSnackBar(it, true)
               },
               onLoaded = { data ->
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
                     binding.fabFavorite.apply {
                        iconTint = ColorStateList.valueOf(
                           resources.getColor(
                              R.color.red,
                              requireContext().theme
                           )
                        )
                        text = "Added to favorites"
                     }
                  }
                  viewLifecycleOwner.lifecycleScope.launch {
                     binding.topBar.title = data.title
                     binding.tvTitle.text = data.title
                     binding.tvReleaseGenreDuration.text =
                        "${data.releaseDate.dateToViewDate()} · " +
                           "${data.genres.joinToString(", ")} · " +
                           data.runtime.convertToTimeDuration()
                     if (!data.tagline.isNullOrBlank()) {
                        binding.tvTagline.apply {
                           text = "\"${data.tagline}\""
                           isVisible = true
                        }
                     } else {
                        binding.tvTagline.isVisible = false
                     }
                     binding.viewVote.isVisible = true
                     data.voteAverage?.let {
                        binding.progressBarVoteAverage.progress = it.toPercentInt()
                        binding.tvVoteAverage.text = it.roundOneDecimal().toString()
                     }
                     binding.tvDesc.text = data.overview
                     data.posterPath?.let {
                        binding.ivPoster.loadImage(it, IMAGE_POSTER_DETAIL_SIZE)
                     }
                     data.backdropPath?.let {
                        binding.ivBackground.loadBackgroundImage(it)
                     }
                     binding.shimmerMovieDetail.apply {
                        stopShimmer()
                        isVisible = false
                     }
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
               onError = {
                  binding.root.showSnackBar(it, true)
               },
               onLoaded = { data ->
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