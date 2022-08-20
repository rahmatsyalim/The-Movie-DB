package com.syalim.themoviedb.presentation.discover

import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.syalim.themoviedb.R
import com.syalim.themoviedb.databinding.ItemFilterBinding
import com.syalim.themoviedb.domain.model.MovieGenre
import com.syalim.themoviedb.presentation.common.BaseAdapter
import com.syalim.themoviedb.presentation.common.utils.viewBinding


/**
 * Created by Rahmat Syalim on 2022/07/18
 * rahmatsyalim@gmail.com
 */
class GenreFilterAdapter : BaseAdapter<MovieGenre, GenreFilterAdapter.GenreFilterViewHolder>(GenreComparator) {

   var selectedGenre: List<String>? = null

   var onItemClickListener: ((MovieGenre, Boolean) -> Unit)? = null

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreFilterViewHolder {
      return GenreFilterViewHolder(parent.viewBinding(ItemFilterBinding::inflate))
   }

   override fun onBindViewHolder(holder: GenreFilterViewHolder, position: Int) {
      holder.bind(differ.currentList[position])
   }

   inner class GenreFilterViewHolder(private val binding: ItemFilterBinding) :
      RecyclerView.ViewHolder(binding.root) {


      fun bind(item: MovieGenre) {
         binding.ctGenre.apply {
            text = item.name

            selectedGenre?.let {
               if (it.contains(item.id.toString())) {
                  isChecked = true
                  setTextColor(ContextCompat.getColor(context, R.color.brand_aqua))
               }
            }

            setOnClickListener {
               if (isChecked) {
                  isChecked = false
                  setTextColor(ContextCompat.getColor(context, R.color.brand_white))
               } else {
                  isChecked = true
                  setTextColor(ContextCompat.getColor(context, R.color.brand_aqua))
               }
               onItemClickListener?.invoke(item, isChecked)
            }
         }
      }
   }

   object GenreComparator : DiffUtil.ItemCallback<MovieGenre>() {
      override fun areItemsTheSame(oldItem: MovieGenre, newItem: MovieGenre): Boolean {
         return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(oldItem: MovieGenre, newItem: MovieGenre): Boolean {
         return oldItem == newItem
      }
   }

}