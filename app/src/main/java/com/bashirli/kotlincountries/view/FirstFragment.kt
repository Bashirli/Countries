package com.bashirli.kotlincountries.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bashirli.kotlincountries.adapter.Adapter
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.kotlincountries.R
import com.bashirli.kotlincountries.model.Model
import com.bashirli.kotlincountries.mvvm.FirstMVVM
import com.bashirli.kotlincountries.service.CountryService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_first.*

class FirstFragment : Fragment() {
    private lateinit var adapter:Adapter
    private lateinit var arrayList: ArrayList<Model>
    private lateinit var viewModel:FirstMVVM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProviders.of(this).get(FirstMVVM::class.java)
        viewModel.refreshData()



        arrayList= ArrayList()
        adapter= Adapter(arrayList)
        recyclerCountry.layoutManager=LinearLayoutManager(requireContext())
        recyclerCountry.adapter=adapter

        swipeRefreshLayout.setOnRefreshListener {
        recyclerCountry.visibility=View.GONE
            textView6.visibility=View.GONE
            progressBar.visibility=View.VISIBLE
            swipeRefreshLayout.isRefreshing=false
            viewModel.refreshAPI()
        }
        observeData()
    }

        fun observeData(){
        viewModel.countries.observe(viewLifecycleOwner, Observer {
            it?.let {
               recyclerCountry.visibility=View.VISIBLE
               adapter.updateData(it)
            }
        })

            viewModel.countryError.observe(viewLifecycleOwner, Observer {
                it?.let {
                    if(it){
                        recyclerCountry.visibility=View.GONE
                        progressBar.visibility=View.GONE
                        textView6.visibility=View.VISIBLE
                    }else{

                        textView6.visibility=View.GONE
                    }
                }
            })

            viewModel.countryLoading.observe(viewLifecycleOwner, Observer {
                it?.let {
                    if(it){
                        recyclerCountry.visibility=View.GONE
                        progressBar.visibility=View.VISIBLE
                        textView6.visibility=View.GONE
                    }else{

                        progressBar.visibility=View.GONE

                    }
                }
            })

        }



}