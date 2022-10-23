package com.syalim.themoviedb.feature.tvshows.ui.adapter

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syalim.themoviedb.core.ui.databinding.PagingFooterBinding
import com.syalim.themoviedb.core.ui.utils.viewBinding


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */
class TvShowLoadStateAdapter(
   private val retry: () -> Unit
) : LoadStateAdapter<TvShowLoadStateAdapter.TvShowLoadStateViewHolder>() {

   inner class TvShowLoadStateViewHolder(
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

   override fun onBindViewHolder(holder: TvShowLoadStateViewHolder, loadState: LoadState) {
      holder.bind(loadState)
   }

   override fun onCreateViewHolder(
      parent: ViewGroup,
      loadState: LoadState
   ): TvShowLoadStateViewHolder {
      return TvShowLoadStateViewHolder(parent.viewBinding(PagingFooterBinding::inflate))
   }
}