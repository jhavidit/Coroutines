package tech.jhavidit.coroutinesdemo.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tech.jhavidit.coroutinesdemo.api.ApiHelper
import tech.jhavidit.coroutinesdemo.room.DatabaseHelper
import tech.jhavidit.coroutinesdemo.view.errorHandling.exceptionHandler.ExceptionHandlerActivity
import tech.jhavidit.coroutinesdemo.view.errorHandling.exceptionHandler.ExceptionHandlerViewModel
import tech.jhavidit.coroutinesdemo.view.errorHandling.ignoreErrorAndContinue.IgnoreErrorAndContinueViewModel
import tech.jhavidit.coroutinesdemo.view.longRunningOneTask.LongRunningOneTaskViewModel
import tech.jhavidit.coroutinesdemo.view.longRunningTwoTask.LongRunningTwoTaskViewModel
import tech.jhavidit.coroutinesdemo.view.parallelNetworkCall.ParallelNetworkCallViewModel
import tech.jhavidit.coroutinesdemo.view.room.RoomDBViewModel
import tech.jhavidit.coroutinesdemo.view.seriesNetworkCall.SeriesNetworkCallViewModel
import tech.jhavidit.coroutinesdemo.view.singleNetworkCall.SingleNetworkCallViewModel
import tech.jhavidit.coroutinesdemo.view.timeOut.TimeOutViewModel

class ViewModelFactory(
    private val apiHelper: ApiHelper,
    private val databaseHelper: DatabaseHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SingleNetworkCallViewModel::class.java))
            return SingleNetworkCallViewModel(apiHelper, databaseHelper) as T
        else if (modelClass.isAssignableFrom(ParallelNetworkCallViewModel::class.java))
            return ParallelNetworkCallViewModel(apiHelper, databaseHelper) as T
        else if (modelClass.isAssignableFrom(SeriesNetworkCallViewModel::class.java))
            return SeriesNetworkCallViewModel(apiHelper, databaseHelper) as T
        else if (modelClass.isAssignableFrom(RoomDBViewModel::class.java))
            return RoomDBViewModel(apiHelper, databaseHelper) as T
        else if (modelClass.isAssignableFrom(LongRunningOneTaskViewModel::class.java))
            return LongRunningOneTaskViewModel(apiHelper, databaseHelper) as T
        else if (modelClass.isAssignableFrom(LongRunningTwoTaskViewModel::class.java))
            return LongRunningTwoTaskViewModel(apiHelper, databaseHelper) as T
        else if (modelClass.isAssignableFrom(TimeOutViewModel::class.java))
            return TimeOutViewModel(apiHelper, databaseHelper) as T
        else if (modelClass.isAssignableFrom(ExceptionHandlerViewModel::class.java))
            return ExceptionHandlerViewModel(apiHelper, databaseHelper) as T
        else if (modelClass.isAssignableFrom(IgnoreErrorAndContinueViewModel::class.java))
            return IgnoreErrorAndContinueViewModel(apiHelper, databaseHelper) as T
        throw IllegalArgumentException("Unknown class name")
    }
}