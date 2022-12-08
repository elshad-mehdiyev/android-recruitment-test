package com.socket.recruitmenttest.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.socket.recruitmenttest.data.model.SocketModel

@Dao
interface SocketDao {
    @Insert
    suspend fun saveAllData(list: MutableList<SocketModel>)
    @Query("DELETE FROM SocketModel")
    suspend fun deleteAllData()
    @Query("SELECT * FROM SocketModel")
    fun getAllData(): MutableList<SocketModel>
}