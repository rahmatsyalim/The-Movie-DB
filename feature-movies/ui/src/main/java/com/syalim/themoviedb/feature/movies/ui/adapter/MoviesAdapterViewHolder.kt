package com.syalim.themoviedb.feature.movies.ui.adapter

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
import com.syalim.themoviedb.feature.movies.domain.model.MoviesCategorized
import com.syalim.themoviedb.feature.movies.ui.utils.getMovieCategoryTitle


/**
 * Created by Rahmat Syalim on 2022/08/21
 * rahmatsyalim@gmail.com
 */


sealed class MoviesAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
   class MoviesCarouselViewHolder(
      private val binding: ItemMovieTvshowCarouselListBinding,
      customImageLoader: CustomImageLoader,
      private val setupCarousel: (ViewPager2.() -> Unit)? = null
   ) : MoviesAdapterViewHolder(binding.root) {

      private val carouselAdapter = MovieCarouselAdapter(customImageLoader)

      fun onBind(item: MoviesCategorized) {
         binding.apply {
            tvTitle.text = item.category.getMovieCategoryTitle(itemView.context)
//            tvMore.setOnClickListener {
//               AppNavigator.navigateTo(
//                  AppDestination.MoviesToMoviesByCategory(
//                     Bundle().apply { putEnum(MainDestArg.MOVIES_BY_CATEGORY.key, item.category) }
//                  )
//               )
//            }
            carouselAdapter.submitData(item.movie)
            viewPager.adapter = carouselAdapter
            viewPager.apply {
               setupCarousel?.invoke(this)
               TabLayoutMediator(binding.tabLayout, this) { _, _ -> }.attach()
            }
         }
      }
   }

   class MoviesViewHolder(
      private val binding: ItemMovieTvshowListBinding,
      customImageLoader: CustomImageLoader
   ) : MoviesAdapterViewHolder(binding.root),
      NestedViewHolder {

      private val moviesAdapter = MovieAdapter(customImageLoader)

      private val layoutManager = LinearLayoutManager(
         itemView.context, LinearLayoutManager.HORIZONTAL, false
      )

      private lateinit var item: MoviesCategorized

      fun onBind(item: MoviesCategorized) {
         this.item = item
         binding.apply {
            tvTitle.text = item.category.getMovieCategoryTitle(itemView.context)
            btnMore.setOnClickListener {
               AppNavigator.navigateTo(
                  NavParam(
                     destination = AppDestination.MOVIES_BY_CATEGORY,
                     args = buildArgs(NavArgsKey.MOVIES_CATEGORY to item.category)
                  )
               )
            }
            recyclerView.apply {
               layoutManager = this@MoviesViewHolder.layoutManager
               adapter = moviesAdapter
            }
         }
         moviesAdapter.submitData(item.movie)
      }

      override fun getId(): String = item.category.ordinal.toString()

      override fun getLayoutManager(): RecyclerView.LayoutManager? = binding.recyclerView.layoutManager
   }
}