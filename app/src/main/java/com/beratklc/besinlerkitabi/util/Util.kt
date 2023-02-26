package com.beratklc.besinlerkitabi.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.beratklc.besinlerkitabi.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.progressindicator.CircularProgressIndicator


/*  Extension
 fun String.benimEklentim (parametre:String){

 }
 */

fun ImageView.gorselIndir(url : String?,placeholder : CircularProgressDrawable){

    val options = RequestOptions().placeholder(placeholder).error(R.mipmap.ic_launcher)

    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
    //this ImageView a referans veriyor

}

fun placeHolderYap(context : Context) : CircularProgressDrawable{
    return  CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

@BindingAdapter("android:downloadImage")
fun downloadImage(view: ImageView,url: String?){
    view.gorselIndir(url, placeHolderYap(view.context))
}