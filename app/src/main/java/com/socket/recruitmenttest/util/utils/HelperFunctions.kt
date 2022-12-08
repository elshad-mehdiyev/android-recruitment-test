package com.socket.recruitmenttest.util.utils

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.socket.recruitmenttest.data.model.SocketModel
import org.json.JSONObject

@BindingAdapter("android:changeBackgroundColor")
fun changeBackgroundColor(view: View, changer: String) {
    if (changer == "up") {
        view.setBackgroundColor(Color.parseColor("#00FF00"))
    } else if (changer == "down") {
        view.setBackgroundColor(Color.parseColor("#FF0000"))
    }
}
@BindingAdapter("android:changeTextColor")
fun changeTextColor(text: TextView, changer: String) {
    if (changer == "up") {
        text.setTextColor(Color.parseColor("#00FF00"))
    } else if (changer == "down") {
        text.setTextColor(Color.parseColor("#FF0000"))
    }
}

fun convert(from: Array<out Any>, to: Array<Any>): MutableList<SocketModel> {
    val socketList = mutableListOf<SocketModel>()
    assert(from.size == to.size)
    for (i in from.indices)
        to[i] = from[i]
    val list = mutableListOf<String>()
    for (i in to.indices) {
        list.add(to[i].toString())
    }
    val jsonObject = JSONObject(list[0])
    val jsonArray = jsonObject.getJSONArray("result")
    for (i in 0 until jsonArray.length()) {
        val json = jsonArray.getJSONObject(i)
        val upOrDown = json.getString("0")
        val name = json.getString("1")
        val priceOne = json.getString("2")
        val priceTwo = json.getString("3")
        val priceThree = json.getString("4")
        val priceFour = json.getString("5")
        val place = json.getString("6")
        val date = json.getString("7")
        val socketModel = SocketModel(
            upOrDown = upOrDown, name = name, priceOne = priceOne, priceTwo = priceTwo,
            priceThree = priceThree, priceFour = priceFour, place = place, date = date
        )
        socketList.add(socketModel)
    }
    return socketList
}

fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                return true
            }
        }
        return false
    } else {
        @Suppress("DEPRECATION") val networkInfo =
            connectivityManager.activeNetworkInfo ?: return false
        @Suppress("DEPRECATION")
        return networkInfo.isConnected
    }
}


