package com.syalim.themoviedb.presentation.movie_detail

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.syalim.themoviedb.databinding.ItemReviewBinding
import com.syalim.themoviedb.domain.model.MovieReview
import com.syalim.themoviedb.presentation.common.CoilImageLoader
import com.syalim.themoviedb.presentation.common.CoilImageLoader.Companion.AVATAR_THUMBNAIL
import com.syalim.themoviedb.presentation.common.utils.viewBinding
import javax.inject.Inject


/**
 * Created by Rahmat Syalim on 2022/07/19
 * rahmatsyalim@gmail.com
 */
class ReviewsPagerAdapter @Inject constructor(
   private val coilImageLoader: CoilImageLoader
) : PagingDataAdapter<MovieReview, ReviewsPagerAdapter.ReviewViewHolder>(Comparator) {

   override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
      holder.bind(getItem(position)!!)
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
      return ReviewViewHolder(parent.viewBinding(ItemReviewBinding::inflate))
   }

   private object Comparator : DiffUtil.ItemCallback<MovieReview>() {
      override fun areItemsTheSame(oldItem: MovieReview, newItem: MovieReview): Boolean {
         return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(oldItem: MovieReview, newItem: MovieReview): Boolean {
         return oldItem == newItem
      }
   }

   inner class ReviewViewHolder(private val binding: ItemReviewBinding) :
      RecyclerView.ViewHolder(binding.root) {
      fun bind(item: MovieReview) {
         binding.apply {
            tvName.text = item.author
            tvComments.text = item.content

            coilImageLoader
               .apply {
                  item.avatarPath?.let {
                     if (it.contains("gravatar")) imageUrl(it)
                     else imageUrl(it, AVATAR_THUMBNAIL)
                  } ?: imageUrl(null)
               }
               .loadInto(ivPhoto)
         }
      }
   }
}