package tech.jhavidit.coroutinesdemo.view.singleNetworkCall

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tech.jhavidit.coroutinesdemo.api.ApiHelper
import tech.jhavidit.coroutinesdemo.model.ApiUser
import tech.jhavidit.coroutinesdemo.room.DatabaseHelper
import tech.jhavidit.coroutinesdemo.util.Resource
import tech.jhavidit.coroutinesdemo.util.Status

class SingleNetworkCallViewModel(
    private val apiHelper: ApiHelper,
    private val dbHelper: DatabaseHelper
) : ViewModel() {

    private val users = MutableLiveData<Resource<List<ApiUser>>>()

    init {
        fetchUser()
    }

    private fun fetchUser() {
        viewModelScope.launch {
            users.postValue(Resource.loading(null))
            try {
                val userFromApi = apiHelper.getUsers()
                users.postValue(Resource.success(userFromApi))
            } catch (e: Exception) {
                users.postValue(Resource.error(e.toString(), null))
            }
        }
    }

    fun getUser(): LiveData<Resource<List<ApiUser>>> = users

}