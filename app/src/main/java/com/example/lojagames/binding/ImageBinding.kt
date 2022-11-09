package com.example.lojagames.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object ImageBinding {

    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageUrl(imageView: ImageView, url: String) {
        if (url.isNotEmpty()) {

            Glide.with(imageView).load(url).centerCrop().into(imageView)
        }
    }
}