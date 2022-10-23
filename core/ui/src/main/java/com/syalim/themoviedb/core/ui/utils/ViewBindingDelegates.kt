package com.syalim.themoviedb.core.ui.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.lang.reflect.Method
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


/**
 * Created by Rahmat Syalim on 2022/08/16
 * rahmatsyalim@gmail.com
 */

inline fun <reified T, reified K> getMethod(name: String): Method = T::class.java.getMethod(name, K::class.java)

inline fun <reified T : ViewBinding> AppCompatActivity.viewBinding()
   : ReadOnlyProperty<AppCompatActivity, T> =
   object : ReadOnlyProperty<AppCompatActivity, T> {
      private var binding: T? = null
      override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T =
         binding ?: inflateView().also {
            setContentView(it.root)
            binding = it
         }

      private fun inflateView() = getMethod<T, LayoutInflater>("inflate")
         .invoke(null, layoutInflater) as T
   }

inline fun <reified T : ViewBinding> Fragment.viewBinding()
   : ReadOnlyProperty<Fragment, T> =
   object : ReadOnlyProperty<Fragment, T>, DefaultLifecycleObserver {
      private var binding: T? = null
      override fun getValue(thisRef: Fragment, property: KProperty<*>): T =
         binding ?: bindView().also {
            if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
               viewLifecycleOwner.lifecycle.addObserver(this)
            }
            binding = it
         }

      override fun onDestroy(owner: LifecycleOwner) {
         binding = null
      }

      private fun bindView() = getMethod<T, View>("bind").invoke(null, requireView()) as T
   }

inline fun <reified T : ViewBinding> BottomSheetDialogFragment.viewBinding()
   : ReadOnlyProperty<BottomSheetDialogFragment, T> =
   object : ReadOnlyProperty<BottomSheetDialogFragment, T>, DefaultLifecycleObserver {
      private var binding: T? = null
      override fun getValue(thisRef: BottomSheetDialogFragment, property: KProperty<*>): T =
         binding ?: inflateView().also {
            if (viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
               viewLifecycleOwner.lifecycle.addObserver(this)
            }
            binding = it
         }

      override fun onDestroy(owner: LifecycleOwner) {
         binding = null
      }

      private fun inflateView() = getMethod<T, LayoutInflater>("inflate")
         .invoke(null, LayoutInflater.from(requireContext())) as T
   }

typealias ViewInflater<T> = (LayoutInflater, ViewGroup, Boolean) -> T

inline fun <reified T : ViewBinding> ViewGroup.viewBinding(inflater: ViewInflater<T>): T =
   inflater(LayoutInflater.from(context), this, false)