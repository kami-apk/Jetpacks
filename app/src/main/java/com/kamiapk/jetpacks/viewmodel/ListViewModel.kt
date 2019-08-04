package com.kamiapk.jetpacks.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kamiapk.jetpacks.model.DogBreed

class ListViewModel : ViewModel(){


    //MutableLiveDataを3つ用意する。
    //こいつらは外部から変更不可能なのでこのViewModel内で値の変更が行われる

    //List<DogBreed>はデータクラスのリスト
    val dogs = MutableLiveData<List<DogBreed>>()
    //データ取得ができなかったらtrueを返す
    val dogsLoadError = MutableLiveData<Boolean>()
    //ロード中なのかのフラグ
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        //モック
        val dog1 = DogBreed(
            "1",
            "Shiba",
            "10 years",
            "breedGroup",
            "bredFor",
            "temperament",
            ""
            )
        val dog2 = DogBreed(
            "2",
            "Shiba",
            "10 years",
            "breedGroup",
            "bredFor",
            "temperament",
            ""
        )
        val dog3 = DogBreed(
            "3",
            "Shiba",
            "10 years",
            "breedGroup",
            "bredFor",
            "temperament",
            ""
        )
        //アダプター用に引き渡すのですが今回はモックのためこんな感じに
        val dogList = arrayListOf<DogBreed>(dog1,dog2,dog3)

        //ここもいったん固定
        dogs.value = dogList
        dogsLoadError.value = false
        loading.value = false
    }

}


