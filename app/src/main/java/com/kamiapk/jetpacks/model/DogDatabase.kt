package com.kamiapk.jetpacks.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


//まずabstractクラスにしてDBアノテーションを
//複数のエンティティを持つ場合は,カンマで区切る
@Database(entities = arrayOf(DogBreed::class),version = 1)
abstract class DogDatabase : RoomDatabase() {

    abstract fun dogDao() : DogDao

    //シングルトンオブジェクトいすることによって多重アクセスによる不具合の発生を抑制する目的
    companion object{
        //このアノテーションはマルチスレッドで利用されるものに使われる
        //コンパイラに向けたアノテーションであり、コードの最適化を抑制することで
        //不具合を起きるのを防いでいる
        @Volatile private var instance: DogDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context : Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { //インスタンス作成
                instance  = it
            }
        }

        private fun buildDatabase(context: Context ) = Room.databaseBuilder(
            context.applicationContext,
            DogDatabase::class.java,
            "dogdatabase"
        ).build()
    }




}

