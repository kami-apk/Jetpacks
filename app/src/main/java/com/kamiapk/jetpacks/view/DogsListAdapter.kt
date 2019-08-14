package com.kamiapk.jetpacks.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.kamiapk.jetpacks.R
import com.kamiapk.jetpacks.databinding.ItemDogBinding
import com.kamiapk.jetpacks.model.DogBreed
import kotlinx.android.synthetic.main.item_dog.view.*

//引数にデータクラスのリストを引き取る
//継承も RecyclerViewのAdapterクラス
//型引数もDogsListAdapterのviewholderと普通のリサイクラービューのアダプター
class DogsListAdapter(val dogsList:ArrayList<DogBreed>) : RecyclerView.Adapter<DogsListAdapter.DogViewHolder>() , DogClickListener {

    //スワイプ時の処理などに利用
    fun updateDogList(newDogsList : List<DogBreed>){
        dogsList.clear()
        dogsList.addAll(newDogsList)
        //この一文が無い場合更新されない
        notifyDataSetChanged()
    }

    //viewholderの作成
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val inflater : LayoutInflater = LayoutInflater.from(parent.context)
        //リサイクラービューのアイテムのレイアウト
        //↓ここをデータバインディング用に切り替えたものを用意する
        //val view = inflater.inflate(R.layout.item_dog,parent,false)
        val view = DataBindingUtil.inflate<ItemDogBinding>(inflater,R.layout.item_dog,parent,false)
        return DogViewHolder(view)
    }

    //dogsListは引数のリスト
    override fun getItemCount() = dogsList.size

    //viewholderに対して値の書き換え
    //アイテムのタップ時の処理もここで
    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        //ここのdogはitem_dog.xmlで宣言したdog
        //rebuildしないとエラー吐く
        holder.view.dog = dogsList[position]

        //これがないとだめ
        //このアダプターがリスナーとなるため
        holder.view.listener = this

        /*
        //nameはTextViewのid
        holder.view.name.text = dogsList[position].dogBreed
        holder.view.lifespan.text =  dogsList[position].lifeSpan
        //要素がタップされた時の操作をここに記述
        holder.view.setOnClickListener{

            //ListFragmentからDetailFragmentへのフラグメント切り替え
            //uuidさえわかればDBから全情報取得できる

            //どこからどこへ推移するのか
            val action = ListFragmentDirections.actionDetailFragment()
            //アクションにuuidの情報を持たせる
            //dogUuidはDetailFragment.ktで宣言されている変数
            action.dogUuid = dogsList[position].uuid
            Navigation.findNavController(it).navigate(action)

        }
        //画像読み込み
        //getProgressDrawableの引数のcontextが微妙に厄介
        holder.view.imageView.loadImage(dogsList[position].imageUrl, getProgressDrawable(holder.view.imageView.context))
        */
    }

    override fun onDogClicked(v: View) {
        //割りと冗長ですが
        //view,id,..
        val uuid = v.dogId.text.toString().toInt()
        //どこからどこへ推移するのか
        val action = ListFragmentDirections.actionDetailFragment()
        action.dogUuid = uuid
        //実際のフラグメント推移
        Navigation.findNavController(v).navigate(action)
    }

    //viewholder class
    //class DogViewHolder(var view : View) : RecyclerView.ViewHolder(view)
    /*
    第一引数を変なのに変更
    viewをview.rootに変更
    ItemDogBindingは自動で作成される
     */
    class DogViewHolder(var view : ItemDogBinding) : RecyclerView.ViewHolder(view.root)
}