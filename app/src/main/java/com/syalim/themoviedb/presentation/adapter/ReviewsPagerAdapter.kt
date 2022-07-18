package com.syalim.themoviedb.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.syalim.themoviedb.common.setImage
import com.syalim.themoviedb.common.setImageUrl
import com.syalim.themoviedb.databinding.ItemReviewBinding
import com.syalim.themoviedb.domain.model.ReviewItemEntity


/**
 * Created by Rahmat Syalim on 2022/07/19
 * rahmatsyalim@gmail.com
 */
class ReviewsPagerAdapter :
   PagingDataAdapter<ReviewItemEntity, ReviewsPagerAdapter.ReviewViewHolder>(ReviewsComparator) {

   inner class ReviewViewHolder(private val binding: ItemReviewBinding) :
      RecyclerView.ViewHolder(binding.root) {
      fun bind(item: ReviewItemEntity) {
         binding.apply {
            tvName.text = item.author
            tvComments.text = item.content
            ivPhoto.setImage(item.avatarPath?:"".setImageUrl())
         }
      }
   }

   override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
      holder.bind(getItem(position)!!)
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
      return ReviewViewHolder(
         ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
      )
   }

   object ReviewsComparator : DiffUtil.ItemCallback<ReviewItemEntity>() {
      override fun areItemsTheSame(oldItem: ReviewItemEntity, newItem: ReviewItemEntity): Boolean {
         return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(
         oldItem: ReviewItemEntity,
         newItem: ReviewItemEntity
      ): Boolean {
         return oldItem == newItem
      }
   }
}