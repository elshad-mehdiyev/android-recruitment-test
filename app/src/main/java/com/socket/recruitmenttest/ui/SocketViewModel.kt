package com.socket.recruitmenttest.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.socket.recruitmenttest.data.model.SocketModel
import com.socket.recruitmenttest.data.repo.SocketRepository
import com.socket.recruitmenttest.util.utils.convert
import com.socket.recruitmenttest.util.utils.isOnline
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SocketViewModel @Inject constructor(
    private val repo: SocketRepository
) : ViewModel() {

    private var _allData = MutableLiveData<MutableList<SocketModel>>()
    val allData: LiveData<MutableList<SocketModel>>
        get() = _allData

    private var _socketIsConnected = MutableLiveData<Boolean>()
    val socketIsConnected: LiveData<Boolean> get() = _socketIsConnected

    fun getData(context: Context) {
        if (isOnline(context)) {
            getDataFromUrl()
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                val data = repo.getAllData()
                withContext(Dispatchers.Main) {
                    _allData.value = data
                    _socketIsConnected.value = false
                }
            }
        }
    }

    private fun getDataFromUrl() {
        val socket = repo.getFromUrl()
        socket.on("message") { args ->
            val array = Array<Any>(args.size) { 0 }
            viewModelScope.launch(Dispatchers.Main) {
                val list = convert(args, array)
                storeInSqlite(list)
                _socketIsConnected.value = socket.connected()
            }
        }
    }
    private fun storeInSqlite(list: MutableList<SocketModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteFromLocalMemory()
            repo.saveToLocalMemory(list)
            withContext(Dispatchers.Main) {
                _allData.value = list
            }
        }
    }
}