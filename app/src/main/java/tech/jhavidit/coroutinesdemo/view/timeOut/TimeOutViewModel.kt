package tech.jhavidit.coroutinesdemo.view.timeOut

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import tech.jhavidit.coroutinesdemo.api.ApiHelper
import tech.jhavidit.coroutinesdemo.model.ApiUser
import tech.jhavidit.coroutinesdemo.room.DatabaseHelper
import tech.jhavidit.coroutinesdemo.util.Resource

class TimeOutViewModel(
    private val apiHelper: ApiHelper,
    private val databaseHelper: DatabaseHelper
) : ViewModel() {

    private val users = MutableLiveData<Resource<List<ApiUser>>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            users.postValue(Resource.loading(null))
            try {
                withTimeout(100) {
                    val usersFromApi = apiHelper.getUsers()
                    users.postValue(Resource.success(usersFromApi))
                }
            } catch (e: TimeoutCancellationException) {
                users.postValue(Resource.error("Timed out exception", null))
            } catch (e: Exception) {
                users.postValue(Resource.error("Something went wrong", null))
            }
        }
    }

    fun getUsers(): LiveData<Resource<List<ApiUser>>> = users


}