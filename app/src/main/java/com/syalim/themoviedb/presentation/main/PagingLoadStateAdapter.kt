package com.syalim.themoviedb.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syalim.themoviedb.databinding.PagingFooterBinding


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */
class PagingLoadStateAdapter : LoadStateAdapter<PagingLoadStateAdapter.PagingFooterViewHolder>() {

   inner class PagingFooterViewHolder(
      private val binding: PagingFooterBinding
   ) : RecyclerView.ViewHolder(binding.root) {

      fun bind(loadState: LoadState) {
         binding.apply {
            progressBar.isVisible = loadState is LoadState.Loading
         }
      }
   }

   override fun onBindViewHolder(holder: PagingFooterViewHolder, loadState: LoadState) {
      holder.bind(loadState)
   }

   override fun onCreateViewHolder(
      parent: ViewGroup,
      loadState: LoadState
   ): PagingFooterViewHolder {
      return PagingFooterViewHolder(
         PagingFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      )
   }
}