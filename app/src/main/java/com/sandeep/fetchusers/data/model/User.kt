package com.sandeep.fetchusers.data.model

import com.google.gson.annotations.SerializedName


data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("listId") val listId: Int,
    @SerializedName("name") val name: String)