package com.dee.popularmovies.utils

import android.util.Patterns
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.dee.popularmovies.R

fun ImageView.loadImage(image: String) {
    val options: RequestOptions = RequestOptions()
        .format(DecodeFormat.PREFER_RGB_565)
    Glide.with(context)
        .load(image)
        .apply(options)
        .placeholder(R.drawable.ic_placeholder)
        .error(R.drawable.ic_placeholder)
        .transition(DrawableTransitionOptions.with(DrawableAlwaysCrossFadeFactory()))
        .centerCrop()
        .into(this)
}

fun String.isValidEmail(): Boolean {
    val pattern = Patterns.EMAIL_ADDRESS
    return pattern.matcher(this).matches()
}