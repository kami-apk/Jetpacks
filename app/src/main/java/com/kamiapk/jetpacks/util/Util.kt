package com.kamiapk.jetpacks.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
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

//この拡張関数を使って画像の表示を行う
fun ImageView.loadImage(url :String?, progressDrawable: CircularProgressDrawable){
    val options = RequestOptions()
        .placeholder(progressDrawable)//読み込み
        .error(R.drawable.ic_details)//エラー時
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)//ImageView
}

//上の拡張関数を使ってデータバインディング用の関数を作成する
//作成したらrebuild
@BindingAdapter("android:imageUrl")
fun loadImage(view : ImageView, url : String?){
    //拡張関数
    view.loadImage(url, getProgressDrawable(view.context))
}


