package com.syalim.themoviedb.core.ui

import android.content.Context
import android.graphics.Color
import android.transition.Slide
import android.view.View
import androidx.annotation.GravityInt
import androidx.annotation.IdRes
import com.google.android.material.transition.platform.*
import com.syalim.themoviedb.core.ui.utils.getColorFrom


/**
 * Created by Rahmat Syalim on 2022/09/13
 * rahmatsyalim@gmail.com
 */

fun Context.getTransformTransition(@IdRes drawingViewId: Int?) =
   MaterialContainerTransform().apply {
      try {
         drawingViewId?.let { this.drawingViewId = it }
      } catch (e: IllegalArgumentException) {
         e.printStackTrace()
      }
      duration = resources.getInteger(R.integer.duration_normal).toLong()
      scrimColor = Color.TRANSPARENT
      setAllContainerColors(getColorFrom(R.color.theme_background))
   }

fun Context.getTransformTransition(startView: View, endView: View) =
   MaterialContainerTransform().apply {
      this.startView = startView
      this.endView = endView
      duration = resources.getInteger(R.integer.duration_normal).toLong()
      scrimColor = Color.TRANSPARENT
      setAllContainerColors(getColorFrom(R.color.theme_background))
   }

fun Context.getAxisYTransition(forward: Boolean, target: View? = null) =
   MaterialSharedAxis(MaterialSharedAxis.Y, forward).apply {
      duration = resources.getInteger(R.integer.duration_normal).toLong()
      target?.let { addTarget(it) }
   }

fun Context.getAxisXTransition(forward: Boolean, target: View? = null) =
   MaterialSharedAxis(MaterialSharedAxis.X, forward).apply {
      duration = resources.getInteger(R.integer.duration_normal).toLong()
      target?.let { addTarget(it) }
   }

fun Context.getAxisZTransition(forward: Boolean, target: View? = null) =
   MaterialSharedAxis(MaterialSharedAxis.Z, forward).apply {
      duration = resources.getInteger(R.integer.duration_normal).toLong()
      target?.let { addTarget(it) }
   }

fun Context.getFadeTransition(target: View? = null) =
   MaterialFade().apply {
      duration = resources.getInteger(R.integer.duration_medium).toLong()
      target?.let { addTarget(it) }
   }

fun Context.getFadeThroughTransition(target: View? = null) =
   MaterialFadeThrough().apply {
      duration = resources.getInteger(R.integer.duration_medium).toLong()
      target?.let { addTarget(it) }
   }

fun Context.getElevationScaleTransition(growing: Boolean, target: View? = null) =
   MaterialElevationScale(growing).apply {
      duration = resources.getInteger(R.integer.duration_normal).toLong()
      target?.let { addTarget(it) }
   }

fun Context.getSlideTransition(@GravityInt edge: Int, target: View? = null) =
   Slide().apply {
      slideEdge = edge
      duration = resources.getInteger(R.integer.duration_slow).toLong()
      target?.let { addTarget(it) }
   }