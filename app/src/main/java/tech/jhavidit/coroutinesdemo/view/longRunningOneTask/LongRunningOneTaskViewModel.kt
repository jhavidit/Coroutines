package tech.jhavidit.coroutinesdemo.view.longRunningOneTask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tech.jhavidit.coroutinesdemo.api.ApiHelper
import tech.jhavidit.coroutinesdemo.room.DatabaseHelper
import tech.jhavidit.coroutinesdemo.util.Resource
import java.lang.Exception

class LongRunningOneTaskViewModel(
    private val apiHelper: ApiHelper,
    private val databaseHelper: DatabaseHelper
) : ViewModel() {

    private val status = MutableLiveData<Resource<String>>()

    init {
        startLongRunningTask()
    }

    private fun startLongRunningTask() {
        viewModelScope.launch {
            status.postValue(Resource.loading(null))
            try {
                doLongRunningTask()
                status.postValue(Resource.success("Task Completed"))
            } catch (e: Exception) {
                status.postValue(Resource.error("Something went wrong", null))
            }
        }
    }

    private suspend fun doLongRunningTask() {
        withContext(Dispatchers.Default) {
            delay(5000)

        }
    }

    fun getStatus(): LiveData<Resource<String>> = status

}