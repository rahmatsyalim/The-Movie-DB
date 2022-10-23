package com.syalim.themoviedb.feature.tvshows.ui.adapter

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.syalim.themoviedb.core.navigation.*
import com.syalim.themoviedb.core.ui.NestedViewHolder
import com.syalim.themoviedb.core.ui.databinding.ItemMovieTvshowCarouselListBinding
import com.syalim.themoviedb.core.ui.databinding.ItemMovieTvshowListBinding
import com.syalim.themoviedb.core.ui.imageloader.CustomImageLoader
import com.syalim.themoviedb.feature.tvshows.domain.model.TvShowsCategorized
import com.syalim.themoviedb.feature.tvshows.ui.utils.getTvShowCategoryTitle


/**
 * Created by Rahmat Syalim on 2022/08/21
 * rahmatsyalim@gmail.com
 */


sealed class TvShowsAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
   class TvShowCarouselViewHolder(
      private val binding: ItemMovieTvshowCarouselListBinding,
      customImageLoader: CustomImageLoader,
      private var setupCarousel: (ViewPager2.() -> Unit)? = null,
   ) : TvShowsAdapterViewHolder(binding.root) {

      private val carouselAdapter = TvShowCarouselAdapter(customImageLoader)

      fun onBind(item: TvShowsCategorized) {
         binding.apply {
            tvTitle.text = item.category.getTvShowCategoryTitle(itemView.context)
//            tvMore.setOnClickListener {
//               AppNavigator.navigateTo(
//                  AppDestination.TvShowsToTvShowsByCategory(
//                     Bundle().apply { putEnum(MainDestArg.TV_SHOWS_BY_CATEGORY.key, item.category) }
//                  )
//               )
//            }
            viewPager.adapter = carouselAdapter
            carouselAdapter.submitData(item.tvShow)
            viewPager.apply {
               setupCarousel?.invoke(this)
               TabLayoutMediator(binding.tabLayout, this) { _, _ -> }.attach()
            }
         }
      }
   }

   class TvShowViewHolder(
      private val binding: ItemMovieTvshowListBinding,
      customImageLoader: CustomImageLoader
   ) : TvShowsAdapterViewHolder(binding.root),
      NestedViewHolder {

      private val moviesAdapter = TvShowAdapter(customImageLoader)

      private val layoutManager = LinearLayoutManager(
         itemView.context, LinearLayoutManager.HORIZONTAL, false
      )

      private lateinit var item: TvShowsCategorized

      fun onBind(item: TvShowsCategorized) {
         this.item = item
         binding.apply {
            tvTitle.text = item.category.getTvShowCategoryTitle(itemView.context)
            btnMore.setOnClickListener {
               AppNavigator.navigateTo(
                  NavParam(
                     destination = AppDestination.TV_SHOW_BY_CATEGORY,
                     args = buildArgs(NavArgsKey.TV_SHOWS_CATEGORY to item.category)
                  )
               )
            }
            recyclerView.apply {
               layoutManager = this@TvShowViewHolder.layoutManager
               adapter = moviesAdapter
            }
         }
         moviesAdapter.submitData(item.tvShow)
      }

      override fun getId(): String = item.category.ordinal.toString()

      override fun getLayoutManager(): RecyclerView.LayoutManager? = binding.recyclerView.layoutManager
   }
}