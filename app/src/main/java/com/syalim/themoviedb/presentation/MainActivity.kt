package com.syalim.themoviedb.presentation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.syalim.themoviedb.R
import com.syalim.themoviedb.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

   private val viewModel: MainViewModel by viewModels()

   private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

   private val navController by lazy {
      (supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment)
         .findNavController()
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(binding.root)

      setAppBar()

   }

   private fun setAppBar(){
      val topBarLayout = binding.topBarLayout
      val bottomBar = binding.bottomNavigation

      bottomBar.setupWithNavController(navController)

      val topLevelDest = setOf(
         R.id.home_fragment,
         R.id.genre_fragment
      )

      navController.addOnDestinationChangedListener { _, destination, _ ->
         topLevelDest.apply {
            if (this.contains(destination.id)) {
               topBarLayout.showWithAnimation()
               bottomBar.showWithAnimation()
            } else {
               topBarLayout.hideWithAnimation(-topBarLayout.height.toFloat())
               bottomBar.hideWithAnimation(bottomBar.height.toFloat())
            }
         }
      }

   }

   private fun View.showWithAnimation() {
      this.isVisible = true
      this.animate()
         .setDuration(300L)
         .translationY(0f)
         .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
               super.onAnimationEnd(animation)
               this@showWithAnimation.clearAnimation()
            }
         })
   }

   private fun View.hideWithAnimation(translation: Float) {
      this.animate()
         .setDuration(300L)
         .translationY(translation)
         .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
               super.onAnimationEnd(animation)
               this@hideWithAnimation.clearAnimation()
               this@hideWithAnimation.isVisible = false
            }
         })
   }

}