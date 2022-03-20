package tech.jhavidit.coroutinesdemo.view.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tech.jhavidit.coroutinesdemo.api.ApiHelper
import tech.jhavidit.coroutinesdemo.model.ApiUser
import tech.jhavidit.coroutinesdemo.room.DatabaseHelper
import tech.jhavidit.coroutinesdemo.room.User
import tech.jhavidit.coroutinesdemo.util.Resource
import java.lang.Exception

class RoomDBViewModel(
    private val apiHelper: ApiHelper,
    private val databaseHelper: DatabaseHelper
) : ViewModel() {

    private val users = MutableLiveData<Resource<List<User>>>()

    init {
        fetchUser()
    }

    private fun fetchUser() {
        viewModelScope.launch {
            users.postValue(Resource.loading(null))
            try {
                val userFromDb = databaseHelper.getUsers()
                if (userFromDb.isEmpty()) {
                    val userFromApi = apiHelper.getUsers()
                    val userToInsertInDB = mutableListOf<User>()
                    for (userApi in userFromApi) {
                        val user = User(
                            id = userApi.id,
                            name = userApi.name,
                            email = userApi.email,
                            avatar = userApi.avatar,
                        )
                        userToInsertInDB.add(user)
                    }
                    databaseHelper.insertAll(userToInsertInDB)
                    users.postValue(Resource.success(userToInsertInDB))
                } else {
                    users.postValue(Resource.success(userFromDb))
                }
            } catch (e: Exception) {
                users.postValue(Resource.error("Something went wrong", null))
            }
        }
    }

    fun getUsers(): LiveData<Resource<List<User>>> = users

}