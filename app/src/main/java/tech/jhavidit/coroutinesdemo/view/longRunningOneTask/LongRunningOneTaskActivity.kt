package tech.jhavidit.coroutinesdemo.view.longRunningOneTask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_long_running_task.*
import kotlinx.android.synthetic.main.activity_recycler_view.*
import kotlinx.android.synthetic.main.activity_recycler_view.progressBar
import tech.jhavidit.coroutinesdemo.R
import tech.jhavidit.coroutinesdemo.api.ApiHelper
import tech.jhavidit.coroutinesdemo.api.ApiHelperImpl
import tech.jhavidit.coroutinesdemo.api.RetrofitBuilder
import tech.jhavidit.coroutinesdemo.room.DatabaseBuilder
import tech.jhavidit.coroutinesdemo.room.DatabaseHelper
import tech.jhavidit.coroutinesdemo.room.DatabaseHelperImpl
import tech.jhavidit.coroutinesdemo.util.Status
import tech.jhavidit.coroutinesdemo.util.ViewModelFactory

class LongRunningOneTaskActivity : AppCompatActivity() {
    private lateinit var viewModel: LongRunningOneTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_long_running_task)
        initViewModel()
        initObserver()
    }

    private fun initObserver() {
        viewModel.getStatus().observe(this, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    textView.text = it.data
                    textView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    textView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                ApiHelperImpl(RetrofitBuilder.apiService),
                DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
            )
        ).get(LongRunningOneTaskViewModel::class.java)
    }
}