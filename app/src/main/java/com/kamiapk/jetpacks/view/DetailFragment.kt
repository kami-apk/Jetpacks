package com.kamiapk.jetpacks.view


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavDirections
import androidx.navigation.Navigation

import com.kamiapk.jetpacks.R
import com.kamiapk.jetpacks.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailFragment : Fragment() {

    private lateinit var viewModel : DetailViewModel
    private var dogUuid = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    //Viewの初期化はonViewCreatedで行うのがよい
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        //viewModelのデータクラスの更新
        viewModel.feche()

        arguments?.let{
            //argumentsがnullでないならListFragmentから来たdogUuidで書き換える
            dogUuid = DetailFragmentArgs.fromBundle(it).dogUuid
        }

        observeViewModel()

    }

    private fun observeViewModel(){
        //DetailFragmentのテキストの書き換え
        //dogLiveDataのデータクラスのプロパティを参照する
        viewModel.dogLiveData.observe(this, Observer { dog ->
            dog?.let{
                dogName.text = dog.dogBreed
                dogPurpose.text = dog.bredFor
                dogTemperament.text = dog.temperament
                dogLifeSpan.text = dog.lifeSpan
            }
        })
    }


}
