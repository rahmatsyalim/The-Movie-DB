package com.syalim.themoviedb.feature.movies.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.syalim.themoviedb.core.navigation.AppNavigator
import com.syalim.themoviedb.core.navigation.NavArgsKey
import com.syalim.themoviedb.core.navigation.getElementTransitionName
import com.syalim.themoviedb.core.ui.getAxisXTransition
import com.syalim.themoviedb.core.ui.getTransformTransition
import com.syalim.themoviedb.core.ui.imageloader.CustomImageLoader
import com.syalim.themoviedb.core.ui.utils.*
import com.syalim.themoviedb.feature.movies.domain.model.Movie
import com.syalim.themoviedb.feature.movies.domain.model.MovieDetails
import com.syalim.themoviedb.feature.movies.domain.model.MovieReview
import com.syalim.themoviedb.feature.movies.ui.R
import com.syalim.themoviedb.feature.movies.ui.adapter.MovieRecommendationAdapter
import com.syalim.themoviedb.feature.movies.ui.databinding.FragmentMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */

@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

   private val binding: FragmentMovieDetailBinding by viewBinding()

   private val viewModel: MovieDetailViewModel by viewModels()

   private val movieId by lazy { arguments?.getString(NavArgsKey.MOVIE_ID)!! }

   @Inject
   lateinit var recommendationMoviesAdapter: MovieRecommendationAdapter

   @Inject
   lateinit var customImageLoader: CustomImageLoader

   private val movieTrailerDialogFragment by lazy { MovieTrailerDialogFragment() }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      setupTransition()
      initUi()
   }

   private fun setupTransition() {
      arguments.getElementTransitionName()?.let {
         sharedElementEnterTransition = requireContext().getTransformTransition(AppNavigator.navHostId)
         binding.root.transitionName = it
         returnTransition = requireContext().getAxisXTransition(false)
      }
   }

   private fun MovieDetailUiState.updateState() {
      if (isDefault) {
         viewModel.onFetchMovieDetail(movieId)
         binding.detailContainer.isGone()
         binding.shimmerDetail.start()
      }
      onFinish(
         onSuccess = {
            updateDetail(isBookmarked)
         },
         onFailure = {
            binding.root.showSnackBar(getThrownMessage(requireContext()))
            viewModel.onErrorShown()
         }
      )
   }

   private fun initUi() {
      setupTopBar()
      setupRecommendations()
      viewLifecycleOwner.lifecycleScope.launch {
         viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
               viewModel.movieDetailUiState.collect { it.updateState() }
            }
         }
      }
   }

   private fun setupTopBar() {
      binding.btnBack.setOnClickListener {
         findNavController().navigateUp()
      }
   }

   // region Detail
   private fun MovieDetails.updateDetail(isBookmarked: Boolean) {
      binding.toolbar.title = title
      binding.tvTitle.text = title
      binding.tvStatusReleaseDateDuration.text = statusReleaseDateDuration
      binding.progressBarVoteAverage.progress = voteAveragePercent
      binding.tvVoteAverage.text = voteAverage.toString()
      binding.tvVoteCount.text = voteCount
      binding.tvTagline.apply { tagline?.let { text = it } ?: isGone() }
      binding.tvOverview.text = overview ?: getString(R.string.no_overview)
      customImageLoader
         .imageUrl(backdropPath)
         .loadBackdropInto(binding.ivBackdrop)
      customImageLoader
         .imageUrl(posterPath)
         .loadImageInto(binding.ivDetailPoster)
      binding.chipGroupGenre.apply {
         if (childCount == 0) genres.forEach { addStaticChip(requireContext(), it) }
      }
      binding.btnTrailer.setOnClickListener {
         trailerKey?.let { videoId ->
            movieTrailerDialogFragment.let {
               it.videoId = videoId
               it.show(parentFragmentManager, it.javaClass.simpleName)
            }
         } ?: binding.root.showSnackBar(getString(R.string.no_trailer))
      }
      binding.btnBookmark.apply {
         if (isBookmarked) {
            icon = requireContext().getDrawableFrom(R.drawable.ic_bookmark_active)
            text = getString(R.string.bookmarked)
            setOnClickListener {
               viewModel.onRemoveBookmark(movieId)
               icon = requireContext().getDrawableFrom(R.drawable.ic_bookmark_default)
               text = getString(R.string.bookmark)
            }
         } else {
            setOnClickListener {
               viewModel.onBookmark(movieId)
               icon = requireContext().getDrawableFrom(R.drawable.ic_bookmark_active)
               text = getString(R.string.bookmarked)
            }
         }
      }
      reviews.updateReview()
      recommendations.updateRecommendations()
      binding.shimmerDetail.delayedStop(viewLifecycleOwner.lifecycleScope) {
         binding.detailContainer.isVisible()
      }
   }
   //endregion

   private fun MovieReview?.updateReview() {
      if (this == null) {
         binding.tvInfoReviews.text = getString(R.string.no_reviews)
         binding.tvInfoReviews.isVisible()
         binding.includedItemReview.itemMovieReview.isGone()
      } else {
         binding.includedItemReview.apply {
            tvName.text = author
            tvComments.text = content
            customImageLoader
               .imageUrl(avatarPath)
               .loadAvatarInto(ivPhoto)
         }
         binding.btnMoreReviews.setOnClickListener {
            requireContext().showShortToast("Reviews with movie id $movieId")
         }
      }
   }

   // region Recommendations
   private fun setupRecommendations() {
      binding.rvRecommendationMovies.apply {
         layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
         adapter = recommendationMoviesAdapter
      }
   }

   private fun List<Movie>.updateRecommendations() {
      if (!isEmpty()) {
         recommendationMoviesAdapter.submitData(this)
         binding.recommendationsContainer.isVisible()
      }
   }
   // endregion


}