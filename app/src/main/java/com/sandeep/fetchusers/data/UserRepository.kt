package com.sandeep.fetchusers.data

import com.sandeep.fetchusers.data.model.User
import com.sandeep.fetchusers.network.ApiService
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getUsers(): List<User> {
        return apiService.getUsers()
    }
}