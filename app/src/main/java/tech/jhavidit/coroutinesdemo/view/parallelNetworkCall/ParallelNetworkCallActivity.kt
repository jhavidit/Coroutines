package tech.jhavidit.coroutinesdemo.view.parallelNetworkCall

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_recycler_view.*
import tech.jhavidit.coroutinesdemo.R
import tech.jhavidit.coroutinesdemo.api.ApiHelperImpl
import tech.jhavidit.coroutinesdemo.api.RetrofitBuilder
import tech.jhavidit.coroutinesdemo.model.ApiUser
import tech.jhavidit.coroutinesdemo.room.DatabaseBuilder
import tech.jhavidit.coroutinesdemo.room.DatabaseHelperImpl
import tech.jhavidit.coroutinesdemo.util.Status
import tech.jhavidit.coroutinesdemo.util.ViewModelFactory
import tech.jhavidit.coroutinesdemo.view.adapter.ApiUserAdapter

class ParallelNetworkCallActivity : AppCompatActivity() {
    private lateinit var viewModel: ParallelNetworkCallViewModel
    private lateinit var adapter: ApiUserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        initViewModel()
        initAdapter()
        setUpObserver()
    }

    private fun setUpObserver() {
        viewModel.getUsers().observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                    recyclerView.visibility = GONE
                    progressBar.visibility = VISIBLE
                }
                Status.SUCCESS -> {
                    recyclerView.visibility = VISIBLE
                    progressBar.visibility = GONE
                    it.data?.let { users
                        ->
                        renderList(users)
                    }
                }
                Status.ERROR -> {
                    progressBar.visibility = GONE
                    Toast.makeText(this, "${it.data}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initAdapter() {
        adapter = ApiUserAdapter(ArrayList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this, ViewModelFactory(
                ApiHelperImpl(RetrofitBuilder.apiService),
                DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
            )
        ).get(ParallelNetworkCallViewModel::class.java)
    }

    private fun renderList(users: List<ApiUser>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }

}