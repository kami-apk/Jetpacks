package com.kamiapk.jetpacks.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kamiapk.jetpacks.R


//画像読み込み時にスピナーを起動させる
fun getProgressDrawable(context : Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply{
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}


fun ImageView.loadImage(url :String?, progressDrawable: CircularProgressDrawable){
    val options = RequestOptions()
        .placeholder(progressDrawable)//読み込み
        .error(R.drawable.ic_details)//エラー時
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)//ImageView
}
//この関数をリサイクラービューのアダプター内で呼び出す

