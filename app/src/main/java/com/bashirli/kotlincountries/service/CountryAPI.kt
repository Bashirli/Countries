package com.bashirli.kotlincountries.service

import com.bashirli.kotlincountries.model.Model
import io.reactivex.Single
import retrofit2.http.GET

interface CountryAPI {
    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getDataCountries():Single<List<Model>>
}