package com.bashirli.kotlincountries.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bashirli.kotlincountries.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.setImageUrl(url:String?,progressDrawable: CircularProgressDrawable){
val options=RequestOptions().placeholder(progressDrawable)
    .error(R.mipmap.ic_launcher)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)

}

fun placeHolderProgressBar(context: Context):CircularProgressDrawable{
return CircularProgressDrawable(context).apply {
    strokeWidth=8f
    centerRadius=40f
    start()
}
}

@BindingAdapter("android:downloadImage")
fun downloadImage(view:ImageView,url:String?){
    view.setImageUrl(url, placeHolderProgressBar(view.context))
}