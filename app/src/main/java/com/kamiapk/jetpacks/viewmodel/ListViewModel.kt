package com.kamiapk.jetpacks.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.kamiapk.jetpacks.model.DogBreed
import com.kamiapk.jetpacks.model.DogDao
import com.kamiapk.jetpacks.model.DogDatabase
import com.kamiapk.jetpacks.model.DogsApiService
import com.kamiapk.jetpacks.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

//ViewModelの継承からBaseViewModelの継承に切り替えて
//引数にapplicationを取るようにする
//BaseViewModelはAndroidViewModelを継承しているViewModel
class ListViewModel(application: Application) : BaseViewModel(application){

    //共有プリファレンスを使う準備
    //SharedPreferencesHelperクラスのインスタンス取得
    private var prefHelper = SharedPreferencesHelper(getApplication())

    //データをどこからとってくるかの基準時間を置く
    //5分間。単位はナノセカンド
    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L


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

    //初回はリストフラグメントが生成されるとき
    //二度目以降は、最上部でスワイプされるときにこの関数が呼ばれる
    fun refresh() { //平たく言えばデータ更新が行われる

        //最後に書き込まれた時間を取得する
        val updateTime : Long? = prefHelper.getUpdateTime()

        //特に初回起動の時はnull、もしくはデフォルトヴァリューの値である0を取っているので
        //本当に初回の回避.また更新して5分以上のときはリモートからデータを取得する
        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){
            fetchFromDatabase()
        }else {
            fetchFromRemote()
        }
    }

    //でも結局こういった少なめのデータはリモートで取ってくるのがよい
    fun refreshBypassCache(){
        fetchFromRemote()
    }

    //DBからデータの取得を行う
    //さらにレイアウトの制御も行う
    private fun fetchFromDatabase() {
        loading.value = true
        //DBのアクセスはサブスレッドで
        //ということでコールーチン
        launch{
            //DBから List<DogBreed>のクエリ
            val dogs : List<DogBreed> = DogDatabase(getApplication()).dogDao().getAllDogs()
            //レイアウト反映のためにLiveDataを書き換える
            dogsRetrieved(dogs)
            //コールーチン内だし、アクティビティ上じゃないからcontextはgetApplication()で呼び出されているのかな
            Toast.makeText(getApplication(),"Dogs retrieved from Database",Toast.LENGTH_SHORT).show()
        }

    }

    //通信によりデータをネットから取得する
    //さらにレイアウトの制御も行う
    private fun fetchFromRemote() {
        //最初はローディングフラグはtrue
        loading.value = true
        disposable.add(
            dogsSrevice.getDogs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<DogBreed>>(){

                    override fun onSuccess(dogList: List<DogBreed>) {
                        //データをデータベースに入れて...
                        storeDogsLocally(dogList)
                        Toast.makeText(getApplication(),"Dogs retrieved from endpoint",Toast.LENGTH_SHORT).show()
                        /*関数を新たに作り記述を分ける。
                        dogs.value = dogList
                        dogsLoadError.value = false
                        loading.value = false
                        */

                    }

                    override fun onError(e: Throwable) {
                        dogsLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

    // storeDogsLocally()内で呼び出される
    //LiveDataに値を渡してUIに反映させる
    private fun dogsRetrieved(dogList: List<DogBreed>){
        dogs.value = dogList
        dogsLoadError.value = false
        loading.value = false
    }

    //ここでの処理はぶっちゃけいらな(ry
    //ソートをしている
    private fun storeDogsLocally(list : List<DogBreed>){
        //launch内でコールーチン処理を記述する.
        //具体的にはデータベースアクセス
        launch{
            //最初にdaoのインスタンスを取得しておく
            val dao : DogDao = DogDatabase(getApplication()).dogDao()
            //まず、二回目以降のことを考えデータベースをリセットする
            dao.deleteAllDogs()
            //uuidのリストを取得
            val result : List<Long> = dao.insertAll( *list.toTypedArray())

            //煩雑だけどlistにresultのuuidを入れていく
            var i = 0
            while ( i < list.size ){
                list[i].uuid = result[i].toInt()
                ++i
            }
            //最後にlistを渡す
            dogsRetrieved(list)
        }
        //時間を共有プリファレンスに書き込む
        prefHelper.saveUpdateTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}


