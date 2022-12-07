package com.bashirli.kotlincountries.mvvm


import android.app.Application
import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.bashirli.kotlincountries.R
import com.bashirli.kotlincountries.databinding.FragmentFirstBinding
import com.bashirli.kotlincountries.model.Model
import com.bashirli.kotlincountries.service.CountryService
import com.bashirli.kotlincountries.service.RoomDB
import com.bashirli.kotlincountries.util.CustomSP
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FirstMVVM(application: Application) : BaseVM(application) {

    private  var countryService= CountryService()
    private var compositeDisposable= CompositeDisposable()
    val countries= MutableLiveData<List<Model>>()
    val countryError= MutableLiveData<Boolean>()
    val countryLoading= MutableLiveData<Boolean>()
    private var customSP=CustomSP(getApplication())
    private var refreshTime=10*60*1000*1000*1000L




    fun refreshData(){
        val updateTime=customSP.getTime()
        if(updateTime!=0L && updateTime!=null && System.nanoTime()-updateTime<refreshTime){
            getDataFromSQLite()
        }else{
            getDataFromAPI()
        }
    }

    fun getDataFromSQLite(){
        countryLoading.value=true
        launch {
            val countries=RoomDB(getApplication()).getDao().getAll()
            showCountries(countries)
            Toast.makeText(getApplication(),"SQL",Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshAPI(){
        getDataFromAPI()
    }

    fun getDataFromAPI(){
        countryLoading.value=true
        compositeDisposable.add(countryService.getData()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableSingleObserver<List<Model>>(){
                override fun onSuccess(t: List<Model>) {
                    storeInSQL(t)
                    Toast.makeText(getApplication(),"api",Toast.LENGTH_SHORT).show()

                }

                override fun onError(e: Throwable) {
                  countryError.value=true
                    countryLoading.value=false
                }

            }))
    }


    fun showCountries(list:List<Model>){
        countries.value=list
        countryError.value=false
        countryLoading.value=false
    }

    fun storeInSQL(list:List<Model>){
        launch {
            var dao=RoomDB(getApplication()).getDao()
            dao.deleteAll()
            val myLong=dao.insert(*list.toTypedArray())
            var i=0
            while (i<list.size){
                list.get(i).id=myLong.get(i).toInt()
                i++
            }

            showCountries(list)
        }
        customSP.saveTime(System.nanoTime())

    }


}