package com.syalim.themoviedb.presentation.main.discover

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syalim.themoviedb.databinding.PagingFooterBinding
import com.syalim.themoviedb.presentation.utils.ext.viewBinding


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */
class DiscoverLoadStateAdapter(
   private val retry: () -> Unit
) : LoadStateAdapter<DiscoverLoadStateAdapter.DiscoverMoviesLoadStateViewHolder>() {

   inner class DiscoverMoviesLoadStateViewHolder(
      private val binding: PagingFooterBinding
   ) : RecyclerView.ViewHolder(binding.root) {

      fun bind(loadState: LoadState) {
         with(binding) {
            progressBar.isVisible = loadState is LoadState.Loading && !loadState.endOfPaginationReached
            btnRetry.isVisible = loadState is LoadState.Error
            btnRetry.setOnClickListener {
               retry.invoke()
            }
         }
      }
   }

   override fun onBindViewHolder(holder: DiscoverMoviesLoadStateViewHolder, loadState: LoadState) {
      holder.bind(loadState)
   }

   override fun onCreateViewHolder(
      parent: ViewGroup,
      loadState: LoadState
   ): DiscoverMoviesLoadStateViewHolder {
      return DiscoverMoviesLoadStateViewHolder(parent.viewBinding(PagingFooterBinding::inflate))
   }
}