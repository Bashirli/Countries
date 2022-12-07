package com.bashirli.kotlincountries.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bashirli.kotlincountries.model.Model


@Dao
interface RoomDAO {
    @Insert
    suspend fun insert(vararg model: Model):List<Long>

    @Query("Select * from Model")
    suspend fun getAll():List<Model>

    @Query("Select * from Model where id=:myId")
    suspend fun getCountryById(myId:Int) : Model

    @Query("DELETE From Model")
    suspend fun deleteAll()

}