package tech.jhavidit.coroutinesdemo.view.longRunningTwoTask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import tech.jhavidit.coroutinesdemo.api.ApiHelper
import tech.jhavidit.coroutinesdemo.room.DatabaseHelper
import tech.jhavidit.coroutinesdemo.util.Resource
import java.lang.Exception

class LongRunningTwoTaskViewModel(
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
                val firstLongRunningTask = async { firstLongRunningTask() }
                val secondLongRunningTask = async { secondLongRunningTask() }
                val firstTaskResult = firstLongRunningTask.await()
                val secondTaskResult = secondLongRunningTask.await()
                val combinedResult = firstTaskResult + secondTaskResult
                status.postValue(Resource.success(combinedResult))
            } catch (e: Exception) {
                status.postValue(Resource.error("Something Went Wrong!!", null))
            }
        }
    }

    private suspend fun firstLongRunningTask(): String {
        delay(5000)
        return "A"
    }

    private suspend fun secondLongRunningTask(): String {
        delay(5000)
        return "B"
    }

    fun getStatus(): LiveData<Resource<String>> = status

}