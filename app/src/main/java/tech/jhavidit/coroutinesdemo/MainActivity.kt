package tech.jhavidit.coroutinesdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import tech.jhavidit.coroutinesdemo.view.errorHandling.exceptionHandler.ExceptionHandlerActivity
import tech.jhavidit.coroutinesdemo.view.errorHandling.ignoreErrorAndContinue.IgnoreErrorAndContinueActivity
import tech.jhavidit.coroutinesdemo.view.longRunningOneTask.LongRunningOneTaskActivity
import tech.jhavidit.coroutinesdemo.view.longRunningTwoTask.LongRunningTwoTaskActivity
import tech.jhavidit.coroutinesdemo.view.parallelNetworkCall.ParallelNetworkCallActivity
import tech.jhavidit.coroutinesdemo.view.room.RoomDBActivity
import tech.jhavidit.coroutinesdemo.view.seriesNetworkCall.SeriesNetworkCallActivity
import tech.jhavidit.coroutinesdemo.view.singleNetworkCall.SingleNetworkCallActivity
import tech.jhavidit.coroutinesdemo.view.timeOut.TimeOutActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startSingleNetworkCallActivity(view: View) {
        startActivity(Intent(this@MainActivity, SingleNetworkCallActivity::class.java))
    }

    fun startSeriesNetworkCallsActivity(view: View) {
        startActivity(Intent(this@MainActivity, SeriesNetworkCallActivity::class.java))
    }

    fun startParallelNetworkCallsActivity(view: View) {
        startActivity(Intent(this@MainActivity, ParallelNetworkCallActivity::class.java))
    }

    fun startRoomDatabaseActivity(view: View) {
        startActivity(Intent(this@MainActivity, RoomDBActivity::class.java))
    }

    fun startTimeoutActivity(view: View) {
        startActivity(Intent(this@MainActivity, TimeOutActivity::class.java))
    }

//    fun startTryCatchActivity(view: View) {
//        startActivity(Intent(this@MainActivity, TryCatchActivity::class.java))
//    }
//
    fun startExceptionHandlerActivity(view: View) {
        startActivity(Intent(this@MainActivity, ExceptionHandlerActivity::class.java))
    }

    fun startIgnoreErrorAndContinueActivity(view: View) {
        startActivity(Intent(this@MainActivity, IgnoreErrorAndContinueActivity::class.java))
    }

    fun startLongRunningTaskActivity(view: View) {
        startActivity(Intent(this@MainActivity, LongRunningOneTaskActivity::class.java))
    }

    fun startTwoLongRunningTasksActivity(view: View) {
        startActivity(Intent(this@MainActivity, LongRunningTwoTaskActivity::class.java))
    }

}
