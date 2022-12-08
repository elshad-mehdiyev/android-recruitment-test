package com.socket.recruitmenttest.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SocketModel (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val upOrDown: String,
    val name: String,
    val priceOne: String,
    val priceTwo: String,
    val priceThree: String,
    val priceFour: String,
    val place: String,
    val date: String
)
