package com.kamiapk.jetpacks.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager


//Databaseクラスと基本的な構造は同じ
class SharedPreferencesHelper {
    //シングルトンオブジェクト
    companion object{
        //key-value形式のkeyを定数として置いておく
        private const val PREF_TIME = "Pref_time"
        //DBでいうinstance
        private var prefs : SharedPreferences? = null

        @Volatile private var instance: SharedPreferencesHelper? = null
        private val LOCK = Any()

        //synchronizedを使うのは複数のスレッドからアクセスされる可能性
        //シングルトンオブジェクトであるの二つの観点
        operator fun invoke(context: Context): SharedPreferencesHelper = instance ?: synchronized(LOCK){
            instance ?: buildHelper(context).also {
                instance = it
            }
        }

        private fun buildHelper(context: Context) : SharedPreferencesHelper{
            prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPreferencesHelper()
        }
    }

    //操作はcompanion objectの外側に記述する
    //共有プリファレンスを使ってデータのやり取りを行う
    fun saveUpdateTime(time :Long) {
        prefs?.edit(commit = true) {
            //put〇〇で型指定をしている。
            putLong(PREF_TIME, time)
        }
    }

    //読み込み
    fun getUpdateTime() = prefs?.getLong(PREF_TIME,0)


}