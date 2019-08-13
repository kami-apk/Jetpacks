package com.kamiapk.jetpacks.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.kamiapk.jetpacks.model.DogBreed
import com.kamiapk.jetpacks.model.DogDao
import com.kamiapk.jetpacks.model.DogDatabase
import kotlinx.coroutines.launch


//ある一つのデータクラスを持ちそこからdetailfragmentに値を反映
//DBにアクセスするコールーチンの記述のためにViewModel継承からBaseViewModel継承に変更する
class DetailViewModel(application: Application) : BaseViewModel(application){
    //データクラスのLiveData
    val dogLiveData = MutableLiveData<DogBreed>()

    //引数にuuidを取る
    fun feche(dogUuid : Int){
        launch{

            val dao : DogDao = DogDatabase(getApplication()).dogDao()
            //uuidのリストを取得
            val Dog_info = dao.getDog(dogUuid)
            dogLiveData.value = Dog_info
        }
        //Dog_infoがスコープから外れてしまうのでここには書けない
        //dogLiveData.value = Dog_info
    }

}

