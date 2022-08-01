package com.syalim.themoviedb.presentation.main.movie_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.syalim.themoviedb.databinding.ItemReviewBinding
import com.syalim.themoviedb.domain.model.ReviewItemEntity
import com.syalim.themoviedb.presentation.common.extensions.loadProfileImage
import com.syalim.themoviedb.utils.Constants.IMAGE_PROFILE_THUMBNAIL_SIZE


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
            ivPhoto.loadProfileImage(item.avatarPath, IMAGE_PROFILE_THUMBNAIL_SIZE)
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