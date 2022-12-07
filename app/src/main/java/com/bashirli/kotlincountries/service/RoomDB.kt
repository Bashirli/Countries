package com.bashirli.kotlincountries.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bashirli.kotlincountries.model.Model

@Database(entities = arrayOf(Model::class), version = 1)
abstract class  RoomDB : RoomDatabase() {
    abstract fun getDao() : RoomDAO

    companion object{
        @Volatile private var instance:RoomDB?=null

        operator fun invoke(context: Context)= instance ?: synchronized(Any()){
            instance?: makeDB(context).also {
                instance=it
            }
        }

        private fun makeDB(context:Context)= Room.databaseBuilder(context.applicationContext,
        RoomDB::class.java,"CountryDB").build()
    }

}