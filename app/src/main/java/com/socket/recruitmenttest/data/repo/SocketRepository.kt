package com.socket.recruitmenttest.data.repo

import androidx.lifecycle.LiveData
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.socket.recruitmenttest.data.local.SocketDao
import com.socket.recruitmenttest.data.model.SocketModel
import com.socket.recruitmenttest.data.remote.SocketInstance
import javax.inject.Inject

class SocketRepository @Inject constructor(
    private val dao: SocketDao,
    private val socketInstance: SocketInstance
) {
    suspend fun saveToLocalMemory(list: MutableList<SocketModel>) {
        dao.saveAllData(list)
    }
    suspend fun deleteFromLocalMemory() {
        dao.deleteAllData()
    }
    fun getAllData(): MutableList<SocketModel> {
        return dao.getAllData()
    }
    fun getFromUrl(): Socket {
        SocketInstance.setSocket()
        SocketInstance.establishConnection()
        val mSocket = SocketInstance.getSocket()
        mSocket.connect()
        val options = IO.Options()
        options.reconnection = true
        options.forceNew = true
        return mSocket
    }
}