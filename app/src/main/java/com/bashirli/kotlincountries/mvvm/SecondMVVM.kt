package com.bashirli.kotlincountries.mvvm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bashirli.kotlincountries.model.Model
import com.bashirli.kotlincountries.service.RoomDB
import kotlinx.coroutines.launch

class SecondMVVM(application:Application) : BaseVM(application) {

    var countryLiveData=MutableLiveData<Model>()

    fun getDataFromRoom(id:Int){
        launch {
            val country=RoomDB(getApplication()).getDao().getCountryById(id)
            countryLiveData.value=country

        }
    }


}