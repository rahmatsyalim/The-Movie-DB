package com.syalim.themoviedb.presentation.movie_detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.syalim.themoviedb.R
import com.syalim.themoviedb.databinding.FragmentMovieDetailBinding
import com.syalim.themoviedb.presentation.utils.CoilImageLoader
import com.syalim.themoviedb.presentation.utils.ext.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */

@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

   private val binding: FragmentMovieDetailBinding by viewBinding(FragmentMovieDetailBinding::bind)

   private val viewModel: MovieDetailViewModel by viewModels()

   private val arg by navArgs<MovieDetailFragmentArgs>()

   @Inject
   lateinit var reviewsAdapter: MovieReviewsAdapter

   @Inject
   lateinit var recommendationMoviesAdapter: MovieDetailRecommendationsAdapter

   @Inject
   lateinit var coilImageLoader: CoilImageLoader

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      initUi()

   }

   private fun MovieDetailUiState.updateState() {
      onInitial {
         viewModel.onFetchMovieDetail(arg.id)
         binding.shimmerDetail.start()
         binding.shimmerRecommendations.start()
      }
      onFinish(
         onSuccess = { updateDetail(isBookmarked) },
         onFailure = {
            binding.root.showSnackBar(getThrownMessage(requireContext()))
            viewModel.onErrorShown()
         }
      )
   }

   private fun initUi() {
      setupTopBar()
      setupRecommendations()
      setupReviews()
      viewLifecycleOwner.lifecycleScope.launch {
         viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
               viewModel.movieDetailUiState.collect { it.updateState() }
            }
         }
      }
   }

   private fun setupTopBar() {
      binding.topBar.setNavigationOnClickListener {
         findNavController().navigateUp()
      }
   }

   // region Detail
   private fun com.syalim.themoviedb.domain.movie.model.MovieDetail.updateDetail(isBookmarked: Boolean) {
      binding.topBar.title = title
      binding.tvTitle.text = title
      binding.tvStatusReleaseDateDuration.text = statusReleaseDateDuration
      binding.tvTagline.apply { tagline?.let { text = it } ?: isGone() }
      binding.progressBarVoteAverage.progress = voteAveragePercent
      binding.tvVoteAverage.text = voteAverage.toString()
      binding.tvVoteCount.text = voteCount
      coilImageLoader
         .imageUrl(posterPath)
         .loadPosterInto(binding.ivPoster)
      coilImageLoader
         .imageUrl(backdropPath)
         .loadBackgroundInto(binding.ivBackground)
      binding.chipGroupGenre.apply {
         if (childCount == 0) {
            genres.forEach {
               addStaticChip(requireContext(), it)
            }
         }
      }
      binding.fabBookmark.apply {
         if (isBookmarked) {
            icon = requireContext().getDrawableFrom(R.drawable.ic_bookmark_active)
            text = getString(R.string.bookmarked)
            setOnClickListener {
               viewModel.onBookmark(arg.id)
               icon = requireContext().getDrawableFrom(R.drawable.ic_bookmark_default)
               text = getString(R.string.bookmark)
            }
         } else {
            setOnClickListener {
               viewModel.onBookmark(arg.id)
               icon = requireContext().getDrawableFrom(R.drawable.ic_bookmark_active)
               text = getString(R.string.bookmarked)
            }
         }
      }

      recommendations.updateRecommendations()

      binding.shimmerDetail.delayedStop(viewLifecycleOwner.lifecycleScope) {
         binding.detailContainer.isVisible()
         reviews.updateReviews()
      }
   }
   //endregion

   // region Trailer
   private fun updateTrailer(key: String?) {
      val youtubePlayer = YoutubePlayer.Builder(requireActivity())
//         .webView(binding.webViewYoutube)
//         .videoId(key)
//         .onLoading { binding.webViewProgress.isVisible = it }
      var webViewLoaded = false
//      binding.fabTrailer.apply {
//         setOnClickListener {
//            if (text == getString(R.string.show_trailer)) {
//               text = getString(R.string.hide_trailer)
//               icon = requireContext().getDrawableFrom(R.drawable.ic_hide_trailer)
//               binding.viewTrailer.showView()
//               if (!webViewLoaded) {
//                  youtubePlayer.load()
//                  webViewLoaded = true
//               }
//            } else {
//               text = getString(R.string.show_trailer)
//               icon = requireContext().getDrawableFrom(R.drawable.ic_play_trailer)
//               binding.viewTrailer.hideView()
//            }
//         }
//      }
//      binding.fabTrailer.showView()
//      binding.webViewYoutube.showView()
   }
   // endregion

   // region Recommendations
   private fun setupRecommendations() {
      binding.rvRecommendationMovies.apply {
         layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
         adapter = recommendationMoviesAdapter.apply {
            onItemClickListener = {
               findNavController().navigate(
                  R.id.action_movie_detail_fragment_self,
                  Bundle().apply { putString("id", it.id.toString()) }
               )
            }
         }
      }
   }

   private fun List<com.syalim.themoviedb.domain.movie.model.Movie>.updateRecommendations() {
      if (isEmpty()) {
         binding.shimmerRecommendations.delayedStop(viewLifecycleOwner.lifecycleScope)
      } else {
         recommendationMoviesAdapter.submitData(this)
         binding.shimmerRecommendations.delayedStop(viewLifecycleOwner.lifecycleScope) {
            binding.recommendationsContainer.isVisible()
         }
      }
   }
   // endregion

   // region Reviews
   private fun setupReviews() {
      binding.rvReviews.adapter = reviewsAdapter
   }

   private fun List<com.syalim.themoviedb.domain.movie.model.MovieReview>.updateReviews() {
      if (isEmpty()) {
         binding.tvInfoReviews.apply {
            text = getString(R.string.no_reviews)
            isVisible()
         }
      } else {
         reviewsAdapter.submitData(this)
         binding.rvReviews.isVisible()
      }
      binding.viewReviews.isVisible()
   }
   // endregion

}