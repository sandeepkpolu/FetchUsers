package com.sandeep.fetchusers.network

import com.sandeep.fetchusers.data.model.User
import retrofit2.http.GET

interface ApiService {

    @GET("hiring.json")
    suspend fun getUsers(): List<User>

}