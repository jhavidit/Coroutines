package tech.jhavidit.coroutinesdemo.api

import retrofit2.http.GET
import tech.jhavidit.coroutinesdemo.model.ApiUser

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<ApiUser>

    @GET("more-users")
    suspend fun getMoreUsers(): List<ApiUser>

    @GET("error")
    suspend fun getUsersWithError(): List<ApiUser>

}