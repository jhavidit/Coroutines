package tech.jhavidit.coroutinesdemo.view.errorHandling.ignoreErrorAndContinue

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import tech.jhavidit.coroutinesdemo.api.ApiHelper
import tech.jhavidit.coroutinesdemo.model.ApiUser
import tech.jhavidit.coroutinesdemo.room.DatabaseHelper
import tech.jhavidit.coroutinesdemo.util.Resource

class IgnoreErrorAndContinueViewModel(
    private val apiHelper: ApiHelper,
    private val databaseHelper: DatabaseHelper
) : ViewModel() {

    private val users = MutableLiveData<Resource<List<ApiUser>>>()

    init {
        fetchUserData()
    }

    private fun fetchUserData() {
        viewModelScope.launch {
            users.postValue(Resource.loading(null))
            try {
                supervisorScope {
                    val userFromApiDeferred = async { apiHelper.getUsers() }
                    val moreUsersFromApiDeferred = async { apiHelper.getMoreUsers() }
                    val userFromApi = try {
                        userFromApiDeferred.await()
                    } catch (e: Exception) {
                        emptyList()
                    }
                    val moreUsersFromApi = try {
                        moreUsersFromApiDeferred.await()
                    } catch (e: Exception) {
                        emptyList()
                    }
                    val finalResult = mutableListOf<ApiUser>()
                    finalResult.addAll(userFromApi)
                    finalResult.addAll(moreUsersFromApi)
                    users.postValue(Resource.success(finalResult))
                }
            } catch (e: Exception) {
                users.postValue(Resource.error("Something went wrong", null))
            }
        }
    }

    fun getUsers(): LiveData<Resource<List<ApiUser>>> = users

}