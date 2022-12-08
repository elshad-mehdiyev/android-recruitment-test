package com.socket.recruitmenttest.data.remote

import com.github.nkzawa.socketio.client.Socket
import com.github.nkzawa.socketio.client.IO
import com.socket.recruitmenttest.util.utils.Constant.URL
import java.net.URISyntaxException

object SocketInstance {
    lateinit var mSocket: Socket

    @Synchronized
    fun setSocket() {
        try {
            mSocket = IO.socket(URL)
        } catch (e: URISyntaxException) {

        }
    }

    @Synchronized
    fun getSocket(): Socket {
        return mSocket
    }

    @Synchronized
    fun establishConnection() {
        mSocket.connect()
    }

    @Synchronized
    fun closeConnection() {
        mSocket.disconnect()
    }
}