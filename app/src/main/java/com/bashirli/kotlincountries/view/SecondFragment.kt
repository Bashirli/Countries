package com.bashirli.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.bashirli.kotlincountries.R
import com.bashirli.kotlincountries.mvvm.SecondMVVM
import com.bashirli.kotlincountries.util.placeHolderProgressBar
import com.bashirli.kotlincountries.util.setImageUrl
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_second.*

class SecondFragment : Fragment() {
    private lateinit var viewModel:SecondMVVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
var id:Int=0
        arguments?.let {
            id=SecondFragmentArgs.fromBundle(it).id
        }

        viewModel=ViewModelProviders.of(this).get(SecondMVVM::class.java)
        viewModel.getDataFromRoom(id)
        observeData()

    }

    private fun observeData(){
    viewModel.countryLiveData.observe(viewLifecycleOwner, Observer {
        textView.setText(it.countryName)
        textView3.setText(it.countryCapital)
        textView4.setText(it.countryCurrency)
        textView5.setText(it.countryRegion)
        imageView.setImageUrl(it.imageUrl, placeHolderProgressBar(requireContext()))
    })
    }


}