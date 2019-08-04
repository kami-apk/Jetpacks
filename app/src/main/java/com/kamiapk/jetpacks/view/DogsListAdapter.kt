package com.kamiapk.jetpacks.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.kamiapk.jetpacks.R
import com.kamiapk.jetpacks.model.DogBreed
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.item_dog.view.*

//引数にデータクラスのリストを引き取る
//継承も RecyclerViewのAdapterクラス
//型引数もDogsListAdapterのviewholderと普通のリサイクラービューのアダプター
class DogsListAdapter(val dogsList:ArrayList<DogBreed>) : RecyclerView.Adapter<DogsListAdapter.DogViewHolder>() {

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
        val view = inflater.inflate(R.layout.item_dog,parent,false)
        return DogViewHolder(view)
    }

    //dogsListは引数のリスト
    override fun getItemCount() = dogsList.size

    //viewholderに対して値の書き換え
    //アイテムのタップ時の処理もここで
    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        //nameはTextViewのid
        holder.view.name.text = dogsList[position].dogBreed
        holder.view.lifespan.text =  dogsList[position].lifeSpan
        holder.view.setOnClickListener{
            Navigation.findNavController(it).navigate(ListFragmentDirections.actionDetailFragment())
        }
    }

    //viewholder class
    class DogViewHolder(var view : View) : RecyclerView.ViewHolder(view)
}