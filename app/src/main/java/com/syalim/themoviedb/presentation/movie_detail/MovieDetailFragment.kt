package com.syalim.themoviedb.presentation.movie_detail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.syalim.themoviedb.R
import com.syalim.themoviedb.databinding.FragmentMovieDetailBinding
import com.syalim.themoviedb.domain.model.Movie
import com.syalim.themoviedb.domain.model.MovieDetail
import com.syalim.themoviedb.domain.model.MovieReview
import com.syalim.themoviedb.domain.model.MovieTrailer
import com.syalim.themoviedb.presentation.MoviesAdapter
import com.syalim.themoviedb.presentation.PagingLoadStateAdapter
import com.syalim.themoviedb.presentation.common.CoilImageLoader
import com.syalim.themoviedb.presentation.common.CoilImageLoader.Companion.BACKDROP
import com.syalim.themoviedb.presentation.common.CoilImageLoader.Companion.POSTER_DETAIL
import com.syalim.themoviedb.presentation.common.ContentState
import com.syalim.themoviedb.presentation.common.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

   private val binding: FragmentMovieDetailBinding by viewBinding(FragmentMovieDetailBinding::bind)

   private val viewModel: MovieDetailViewModel by viewModels()

   private val arg by navArgs<MovieDetailFragmentArgs>()

   @Inject
   lateinit var reviewsAdapter: ReviewsPagerAdapter

   @Inject
   lateinit var recommendationMoviesAdapter: MoviesAdapter

   @Inject
   lateinit var coilImageLoader: CoilImageLoader

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      setTopBar()

      setRecommendationRecyclerView()

      setReviewsRecyclerView()

      collectDetailState()

   }

   private fun setTopBar() {
      binding.topBar.setNavigationOnClickListener {
         findNavController().navigateUp()
      }
   }

   private var shimmerJob: Job? = null

   private fun collectDetailState() {
      viewLifecycleOwner.lifecycleScope.launchWhenStarted {
         viewModel.movieDetailState.collect { states ->
            when (states) {
               is MovieDetailUiState.Default -> viewModel.updateUiEvents(
                  MovieDetailEvent.LoadContents(arg.id, false)
               )
               is MovieDetailUiState.Contents -> {
                  states.detailData.renderDetail()
                  states.trailerData.renderTrailer()
                  states.recommendationData.renderRecommendations()
                  launch { states.reviewsData.renderReviews() }
                  if (states.isLoading) startShimmerLoading()
                  if (!states.isLoading && states.error == null) launch { stopShimmerLoading() }
                  states.error?.let {
                     binding.root.showSnackBar(it.getThrownMessage(requireContext()))
                  }
               }
            }
         }
      }
   }

   private suspend fun stopShimmerLoading() {
      binding.shimmerDetail.apply {
         if (isVisible) {
            delay(1000L)
            stopShimmer()
            hideView()
            binding.viewDetail.showView()
         } else {
            binding.viewDetail.showView()
         }
      }
   }

   private fun startShimmerLoading() {
      binding.shimmerDetail.apply {
         if (!isVisible) {
            showView()
            startShimmer()
         }
      }
   }

   // region Detail
   private fun ContentState<MovieDetail>.renderDetail() {
      onSuccess {
         binding.topBar.title = extras.titleWithYear
         binding.tvTitle.text = title
         binding.tvReleaseGenreDuration.text = extras.releaseDateGenresRuntime
         binding.tvTagline.apply { extras.taglineWithQuote?.let { text = it } ?: hideView() }
         binding.progressBarVoteAverage.progress = extras.voteAveragePercent
         binding.tvVoteAverage.text = extras.voteAveragePercent.toString()
         binding.tvDesc.text = overview ?: getString(R.string.no_data_overview)
         coilImageLoader
            .imageUrl(posterPath, POSTER_DETAIL)
            .loadInto(binding.ivPoster)
         coilImageLoader
            .imageUrl(backdropPath, BACKDROP)
            .loadInto(binding.ivBackground)
         binding.fabBookmark.apply {
            if (extras.isBookmarked) {
               icon = requireContext().getDrawableFrom(R.drawable.ic_bookmark_active)
               iconTint = requireContext().getColorStateListFrom(R.color.brand_aqua)
               text = getString(R.string.bookmarked)
               setOnClickListener {
                  // TODO: remove from bookmark
                  icon = requireContext().getDrawableFrom(R.drawable.ic_bookmark_default)
                  iconTint = requireContext().getColorStateListFrom(R.color.brand_white)
                  text = getString(R.string.bookmark)
               }
            } else {
               setOnClickListener {
                  // TODO: bookmark
                  icon = requireContext().getDrawableFrom(R.drawable.ic_bookmark_active)
                  iconTint = requireContext().getColorStateListFrom(R.color.brand_aqua)
                  text = getString(R.string.bookmarked)
               }
            }
         }
      }
   }
   // endregion

   // region Trailer
   private fun ContentState<MovieTrailer>.renderTrailer() {
      onSuccess {
         binding.fabTrailer.showView()
         binding.webViewYoutube.showView()
         val youtubePlayer = YoutubePlayer.Builder(requireActivity())
            .webView(binding.webViewYoutube)
            .videoId(key)
            .onLoading { binding.webViewProgress.isVisible = it }
         var webViewLoaded = false
         binding.fabTrailer.setOnFabTrailerClickListener(
            onShow = {
               binding.viewTrailer.showView()
               if (!webViewLoaded) {
                  youtubePlayer.load()
                  webViewLoaded = true
               }
            },
            onHide = { binding.viewTrailer.hideView() }
         )
      }
   }

   private fun ExtendedFloatingActionButton.setOnFabTrailerClickListener(
      onShow: () -> Unit, onHide: () -> Unit
   ) {
      setOnClickListener {
         if (text == getString(R.string.show_trailer)) {
            text = getString(R.string.hide_trailer)
            icon = requireContext().getDrawableFrom(R.drawable.ic_hide_trailer)
            onShow.invoke()
         } else {
            text = getString(R.string.show_trailer)
            icon = requireContext().getDrawableFrom(R.drawable.ic_play_trailer)
            onHide.invoke()
         }
      }
   }
   // endregion

   // region Recommendations
   private fun setRecommendationRecyclerView() {
      binding.rvRecommendationMovies.apply {
         layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
         adapter = recommendationMoviesAdapter.apply {
            onItemClickListener = {
               val bundle = Bundle().apply {
                  putString("id", it.id.toString())
               }
               findNavController().navigate(R.id.action_movie_detail_fragment_self, bundle)
            }
         }
      }
   }

   private fun ContentState<List<Movie>>.renderRecommendations() {
      onSuccess {
         recommendationMoviesAdapter.submitData(this)
         binding.viewRecommendationMovies.showView()
      }
   }
   // endregion

   // region Reviews
   private fun setReviewsRecyclerView() {
      binding.rvReviews.adapter = reviewsAdapter.apply {
         addLoadStateListener { loadState ->
            loadState.setListener(
               itemCount,
               onEmpty = {
                  binding.tvInfoReviews.apply {
                     text = getString(R.string.no_data_movie_reviews)
                     showView()
                  }
                  binding.viewReviews.showView()
               },
               onExist = {
                  binding.viewReviews.showView()
               }
            )
         }
      }.withLoadStateFooter(PagingLoadStateAdapter(reviewsAdapter::retry))
   }

   private suspend fun PagingData<MovieReview>.renderReviews() {
      reviewsAdapter.submitData(this)
   }
   // endregion

}