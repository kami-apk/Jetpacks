package com.kamiapk.jetpacks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext


//データベースに接続するのでapplicationの引数が必要となる。
//(データベースはcontextが必要)
//ということで継承もAndroidViewModel
//サブスレッド処理をコールーチンを用いて記述するのでCoroutineScopeインターフェイスも利用する
//データベースは非同期処理じゃないとアクセスできないし
abstract class BaseViewModel(application: Application) : AndroidViewModel(application), CoroutineScope
{

    //コールーチン用
    private val job =  Job()

    override  val coroutineContext : CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}