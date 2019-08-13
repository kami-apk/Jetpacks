package com.kamiapk.jetpacks.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


//基本構造がRetrofitのそれに似ている
@Dao
interface DogDao{
    //データをデータベースに挿入
    @Insert
    suspend fun insertAll(vararg dogs : DogBreed) : List<Long>//可変長引数
    //引数のDogBreedをもとにデータクラスのuuidをListにして返すみたいな感じ
    //suspendはサブスレッドで呼び出されるべきであるというのをコンパイラ向けに示す

    //データベースから値を持ってくる
    //データベースはすべて小文字で記述されるのでdogbreed(すべて小文字)
    @Query("SELECT * FROM dogbreed")
    suspend fun getAllDogs(): List<DogBreed>

    //:dogIdの:は後で記述されることを表す(関数の引数内に今回は使われてる)
    @Query("SELECT * FROM dogbreed WHERE uuid = :dogId")
    suspend fun getDog(dogId : Int) : DogBreed

    @Query(" DELETE FROM dogbreed")
    suspend fun deleteAllDogs()

}

