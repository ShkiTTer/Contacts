package com.example.contacts.presentation.common.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("app:roundImage", "app:placeholder", requireAll = false)
fun ImageView.loadRoundImageSrc(url: String?, placeholder: Drawable?) {
    if (url.isNullOrEmpty()) {
        if (placeholder != null)
            this.loadRoundImage(placeholder)
    } else {
        this.loadRoundImage(url)
    }
}

@BindingAdapter("app:image", "app:placeholder", requireAll = false)
fun ImageView.loadImageSrc(url: String?, placeholder: Drawable?) {
    if (url.isNullOrEmpty()) {
        if (placeholder != null)
            this.loadImage(placeholder)
    } else {
        this.loadImage(url)
    }
}

fun ImageView.loadImage(url: String) {
    Glide.with(context)
        .load(url)
        .into(this)
}

fun ImageView.loadRoundImage(url: String) {
    Glide.with(context)
        .load(url)
        .circleCrop()
        .into(this)
}

fun ImageView.loadImage(id: Drawable) {
    Glide.with(context)
        .load(id)
        .into(this)
}

fun ImageView.loadRoundImage(id: Drawable) {
    Glide.with(context)
        .load(id)
        .circleCrop()
        .into(this)
}