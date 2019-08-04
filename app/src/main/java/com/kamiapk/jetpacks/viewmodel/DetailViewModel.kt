package com.kamiapk.jetpacks.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kamiapk.jetpacks.model.DogBreed



//ある一つのデータクラスを持ちそこからdetailfragmentに値を反映
class DetailViewModel : ViewModel() {

    //データクラスのLiveData
    val dogLiveData = MutableLiveData<DogBreed>()

    //
    fun feche(){
        //モック
        val dog = DogBreed(
            "1",
            "Shiba",
            "10 years",
            "breedGroup",
            "bredFor",
            "temperament",
            ""
        )

        dogLiveData.value = dog
    }



}