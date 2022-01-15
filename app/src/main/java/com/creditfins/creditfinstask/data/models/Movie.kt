package com.creditfins.creditfinstask.data.models

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, thumbnailImage: String) {
    Glide.with(view.context)
        .load("https://image.tmdb.org/t/p/w500$thumbnailImage")
        .into(view)
}

data class Movie(val id:Int,val name:String,val image:String)
