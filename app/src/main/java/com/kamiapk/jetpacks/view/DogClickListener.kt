package com.kamiapk.jetpacks.view

import android.view.View



//データバインディング用に利用される
//<variable>タグ内に置いて紐付けがなされる
interface DogClickListener {
    fun onDogClicked(v : View)
}