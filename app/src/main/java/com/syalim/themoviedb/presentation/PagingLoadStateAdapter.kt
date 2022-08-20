package com.syalim.themoviedb.presentation

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syalim.themoviedb.databinding.PagingFooterBinding
import com.syalim.themoviedb.presentation.common.utils.viewBinding


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */
class PagingLoadStateAdapter(
   private val retry: () -> Unit
) : LoadStateAdapter<PagingLoadStateAdapter.PagingLoadStateViewHolder>() {

   inner class PagingLoadStateViewHolder(
      private val binding: PagingFooterBinding
   ) : RecyclerView.ViewHolder(binding.root) {

      fun bind(loadState: LoadState) {
         with(binding) {
            progressBar.isVisible = loadState is LoadState.Loading
            btnRetry.isVisible = loadState is LoadState.Error
            btnRetry.setOnClickListener {
               retry.invoke()
            }
         }
      }
   }

   override fun onBindViewHolder(holder: PagingLoadStateViewHolder, loadState: LoadState) {
      holder.bind(loadState)
   }

   override fun onCreateViewHolder(
      parent: ViewGroup,
      loadState: LoadState
   ): PagingLoadStateViewHolder {
      return PagingLoadStateViewHolder(parent.viewBinding(PagingFooterBinding::inflate))
   }
}