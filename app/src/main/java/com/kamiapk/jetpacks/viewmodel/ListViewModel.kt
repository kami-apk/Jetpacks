package com.kamiapk.jetpacks.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kamiapk.jetpacks.model.DogBreed
import com.kamiapk.jetpacks.model.DogsApiService
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListViewModel : ViewModel(){

    //DogsApiServiceクラスのインスタンス宣言
    private val dogsSrevice = DogsApiService()
    //リモートからデータを取得しに行くとき↓を使う
    private val disposable = CompositeDisposable()


    //MutableLiveDataを3つ用意する。
    //こいつらは外部から変更不可能なのでこのViewModel内で値の変更が行われる

    //List<DogBreed>はデータクラスのリスト
    val dogs = MutableLiveData<List<DogBreed>>()
    //データ取得ができなかったらtrueを返す
    val dogsLoadError = MutableLiveData<Boolean>()
    //ロード中なのかのフラグ
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchFromRemote()
    }


    private fun fetchFromRemote() {
        //最初はローディングフラグはtrue
        loading.value = true
        disposable.add(
            dogsSrevice.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<DogBreed>>(){
                    override fun onSuccess(dogList: List<DogBreed>) {
                        dogs.value = dogList
                        dogsLoadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        dogsLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }

                }
                )
        )

    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}


