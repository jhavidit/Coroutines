package tech.jhavidit.coroutinesdemo.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tech.jhavidit.coroutinesdemo.api.ApiHelper
import tech.jhavidit.coroutinesdemo.room.DatabaseHelper
import tech.jhavidit.coroutinesdemo.view.parallelNetworkCall.ParallelNetworkCallViewModel
import tech.jhavidit.coroutinesdemo.view.singleNetworkCall.SingleNetworkCallViewModel

class ViewModelFactory(
    private val apiHelper: ApiHelper,
    private val databaseHelper: DatabaseHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SingleNetworkCallViewModel::class.java))
            return SingleNetworkCallViewModel(apiHelper, databaseHelper) as T
        else if (modelClass.isAssignableFrom(ParallelNetworkCallViewModel::class.java))
            return ParallelNetworkCallViewModel(apiHelper, databaseHelper) as T
        throw IllegalArgumentException("Unknown class name")
    }
}