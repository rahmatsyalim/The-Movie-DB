package com.syalim.themoviedb.presentation.movie_detail

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.syalim.themoviedb.common.setImage
import com.syalim.themoviedb.common.setImageUrl
import com.syalim.themoviedb.common.showToast
import com.syalim.themoviedb.databinding.FragmentMovieDetailBinding
import com.syalim.themoviedb.presentation.MainActivity
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
         viewModel.loadDetails(id = arg.id, isReloading = false)
      }

      setRecyclerViewReviews()

      collectDetailState()

      collectState()

      reviewsLoadStateListener()

   }

   private fun collectState() {
      viewLifecycleOwner.lifecycleScope.launch {
         viewModel.detailState.collectLatest { state ->
            progressBar.isVisible = state.isLoading
            binding.viewContainer.isVisible = !state.isLoading
         }
      }
   }

   private fun reviewsLoadStateListener() {
      reviewsAdapter.addLoadStateListener { loadState ->

         with(loadState) {

            binding.progressBar.isVisible = refresh is LoadState.Loading

            val errorState = when {
               append is LoadState.Error -> append as LoadState.Error
               prepend is LoadState.Error -> prepend as LoadState.Error
               refresh is LoadState.Error -> refresh as LoadState.Error
               else -> null
            }
            errorState?.error?.message?.let {
               requireContext().showToast(it)
            }

            if (source.append is LoadState.NotLoading
               && source.append.endOfPaginationReached
               && reviewsAdapter.itemCount == 0
            ) {
               binding.tvInfoReviews.isVisible = true
               binding.tvInfoReviews.text = "No reviews yet"
            } else {
               binding.tvInfoReviews.isVisible = false
            }
         }

      }
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
            state.data?.let { data ->
               binding.tvTitle.text = data.originalTitle
               binding.tvGenre.text = data.genres?.joinToString(", ")
               binding.tvDesc.text = data.overview
               binding.ivPoster.setImage(data.posterPath!!.setImageUrl())
               binding.ivBackdrop.setImage(data.backdropPath!!.setImageUrl())
            }
         }
      }
   }
}