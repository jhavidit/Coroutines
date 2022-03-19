package tech.jhavidit.coroutinesdemo.view.parallelNetworkCall

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.GlobalScope.coroutineContext
import tech.jhavidit.coroutinesdemo.api.ApiHelper
import tech.jhavidit.coroutinesdemo.model.ApiUser
import tech.jhavidit.coroutinesdemo.room.DatabaseHelper
import tech.jhavidit.coroutinesdemo.util.Resource
import kotlin.coroutines.coroutineContext

class ParallelNetworkCallViewModel(
    private val apiHelper: ApiHelper,
    private val dbHelper: DatabaseHelper
) : ViewModel() {

    private val users = MutableLiveData<Resource<List<ApiUser>>>()

    init {
        fetchUserData()
    }

    private fun fetchUserData() {
        viewModelScope.launch {
            users.postValue(Resource.loading(null))
            try {
                coroutineScope {
                    val usersFromDeferred = async(Dispatchers.IO) { apiHelper.getUsers() }
                    val moreUserFromDeferred = async(Dispatchers.IO) {
                        apiHelper.getMoreUsers()
                    }
                    val usersFromApi = usersFromDeferred.await()
                    val moreUsersFromApi = moreUserFromDeferred.await()
                    val allUsers = mutableListOf<ApiUser>()
                    allUsers.addAll(usersFromApi)
                    allUsers.addAll(moreUsersFromApi)
                    users.postValue(Resource.success(allUsers))
                }
            } catch (e: Exception) {
                users.postValue(Resource.error("Something Went Wrong", null))
            }
        }
    }

    fun getUsers(): LiveData<Resource<List<ApiUser>>> = users

}