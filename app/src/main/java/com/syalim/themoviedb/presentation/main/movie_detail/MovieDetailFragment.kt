package com.syalim.themoviedb.presentation.main.movie_detail

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.syalim.themoviedb.R
import com.syalim.themoviedb.databinding.FragmentMovieDetailBinding
import com.syalim.themoviedb.presentation.common.State
import com.syalim.themoviedb.presentation.common.base.BaseFragment
import com.syalim.themoviedb.presentation.common.extensions.*
import com.syalim.themoviedb.presentation.main.MoviesAdapter
import com.syalim.themoviedb.utils.Constants.IMAGE_POSTER_DETAIL_SIZE
import com.syalim.themoviedb.utils.Utils
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

   private lateinit var recommendationMoviesAdapter: MoviesAdapter

   override fun init() {

      binding.topBar.setNavigationOnClickListener {
         findNavController().navigateUp()
      }

      if (viewModel.detailScreenState.value is State.Idle) {
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

   }

   private fun collectDetailScreenState() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.detailScreenState.collectLatest { state ->
            state.handle(
               onError = {
                  binding.root.showSnackBar(it)
               },
               onLoaded = {

               }
            )
         }
      }
   }

   @SuppressLint("SetTextI18n")
   private fun ReviewsPagerAdapter.setLoadStateListener() {
      Utils.handlePagingLoadState(
         this,
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
      recommendationMoviesAdapter = MoviesAdapter()
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
            state.handle(
               onError = {
                  binding.root.showSnackBar(it)
               },
               onLoaded = {
                  recommendationMoviesAdapter.data.submitList(it)
                  binding.viewRecommendationMovies.isVisible = true
               }
            )
         }
      }
   }

   @SuppressLint("SetTextI18n")
   private fun collectDetailState() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.movieDetailState.collectLatest { state ->
            state.handle(
               onError = {
                  binding.root.showSnackBar(it)
               },
               onLoaded = { data ->
                  binding.fabTrailer.setOnClickListener {
                     binding.viewTrailer.isVisible = !binding.viewTrailer.isVisible
                     with(binding.fabTrailer) {
                        if (text == "Show Trailer") {
                           text = "Hide Trailer"
                           icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_circle_up)

                        } else {
                           text = "Show Trailer"
                           icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_circle_down)
                        }
                     }
                  }
                  binding.fabFavorite.setOnClickListener {
                     // TODO: save to db
                     binding.fabFavorite.apply {
                        iconTint = ColorStateList.valueOf(
                           ContextCompat.getColor(requireContext(), R.color.red)
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
                     binding.ivPoster.loadImage(data.posterPath, IMAGE_POSTER_DETAIL_SIZE)
                     binding.ivBackground.loadBackgroundImage(data.backdropPath)
                     binding.shimmerMovieDetail.apply {
                        stopShimmer()
                        isVisible = false
                     }
                     binding.fabFavorite.show()
                     binding.fabTrailer.show()
                  }
               }
            )
         }
      }
   }

   private fun collectTrailerState() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.movieTrailerState.collectLatest { state ->
            state.handle(
               onFirstLoading = {
                  binding.progressBarTrailer.isVisible = it
               },
               onLoading = {
                  binding.progressBarTrailer.isVisible = it
               },
               onError = {
                  Utils.setYoutubePlayerWebView(binding.webViewEmbedYoutube, "")
               },
               onEmpty = {
                  Utils.setYoutubePlayerWebView(binding.webViewEmbedYoutube, "")
               },
               onLoaded = { data ->
                  binding.progressBarTrailer.isVisible = false
                  data.key?.let {
                     Utils.setYoutubePlayerWebView(binding.webViewEmbedYoutube, it)
                  }
               }
            )
         }
      }
   }

}