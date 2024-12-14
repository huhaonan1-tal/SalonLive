package com.example.salonlive.data
import com.google.gson.annotations.SerializedName

data class ChatMessage(
    @SerializedName("name") val name: String,
    @SerializedName("message") val message: String,
)
