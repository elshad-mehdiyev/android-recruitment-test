package com.socket.recruitmenttest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.socket.recruitmenttest.data.model.SocketModel

@Database(version = 1, entities = [SocketModel::class], exportSchema = false)
abstract class SocketDb: RoomDatabase() {
    abstract fun getSocketDao(): SocketDao
}