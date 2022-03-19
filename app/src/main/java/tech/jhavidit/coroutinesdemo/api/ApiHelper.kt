package tech.jhavidit.coroutinesdemo.api

import tech.jhavidit.coroutinesdemo.model.ApiUser

interface ApiHelper {

    suspend fun getUsers(): List<ApiUser>

    suspend fun getMoreUsers(): List<ApiUser>

    suspend fun getUsersWithError(): List<ApiUser>

}