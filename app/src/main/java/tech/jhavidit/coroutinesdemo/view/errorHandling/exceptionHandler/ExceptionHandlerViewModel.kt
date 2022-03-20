package tech.jhavidit.coroutinesdemo.view.errorHandling.exceptionHandler

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import tech.jhavidit.coroutinesdemo.api.ApiHelper
import tech.jhavidit.coroutinesdemo.model.ApiUser
import tech.jhavidit.coroutinesdemo.room.DatabaseHelper
import tech.jhavidit.coroutinesdemo.util.Resource

class ExceptionHandlerViewModel(
    private val apiHelper: ApiHelper,
    private val databaseHelper: DatabaseHelper
) : ViewModel() {

    private val users = MutableLiveData<Resource<List<ApiUser>>>()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        users.postValue(Resource.error(exception.message.toString(), null))
    }

    init {
        fetchUserData()
    }

    private fun fetchUserData() {
        viewModelScope.launch(exceptionHandler) {
            users.postValue(Resource.loading(null))
            val getUserFromApi = apiHelper.getUsers()
            users.postValue(Resource.success(getUserFromApi))
        }
    }

    fun getUsers(): LiveData<Resource<List<ApiUser>>> = users

}