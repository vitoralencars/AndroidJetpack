package com.vitor.cursojetpack.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun getProgressDrawable(context: Context): CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun ImageView.loadImage(url: String?, progressDrawable: CircularProgressDrawable){
    val options = RequestOptions()
        .placeholder(progressDrawable)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .fitCenter()
        .into(this)
}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?){
    view.loadImage(url, getProgressDrawable(view.context))
}