package com.kamiapk.jetpacks.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


//dataclassなので()で。{}ではない

@Entity
data class DogBreed (

    @ColumnInfo(name = "breed_id")
    @SerializedName("id")
    val breedId : String?,

    @ColumnInfo(name = "dog_breed")
    @SerializedName("name")
    val dogBreed : String?,

    @ColumnInfo(name = "life_span")
    @SerializedName("life_span")
    val lifeSpan : String?,

    @SerializedName("breed_group")
    val breedGroup : String?,

    @ColumnInfo(name = "breed_for")
    @SerializedName("bred_for")
    val bredFor : String?,

    //一単語なので @ColumnInfoを付けない
    @SerializedName("temperament")
    val temperament : String?,

    @ColumnInfo(name = "image_url")
    @SerializedName("url")
    val imageUrl : String?
){
    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0
}





