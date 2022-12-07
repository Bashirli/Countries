package com.bashirli.kotlincountries.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class CustomSP {

    val PREFERENCES_TIME="time"
    companion object{
        private var sharedPreferences:SharedPreferences?=null

        @Volatile private var instance:CustomSP?=null

        operator fun invoke(context: Context)= instance?: synchronized(Any()){
           instance?: makeCustomSP(context).also {
                instance=it
            }
        }

        private fun makeCustomSP(context: Context):CustomSP{
            sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSP()
        }

    }

    fun saveTime(long: Long){
        sharedPreferences?.edit(commit = true){
            putLong(PREFERENCES_TIME,long)
        }


    }

    fun getTime():Long{
        return sharedPreferences!!.getLong(PREFERENCES_TIME,0)
    }

}