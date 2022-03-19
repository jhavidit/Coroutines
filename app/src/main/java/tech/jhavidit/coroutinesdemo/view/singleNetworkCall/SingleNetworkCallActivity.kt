package tech.jhavidit.coroutinesdemo.view.singleNetworkCall

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

class SingleNetworkCallActivity : AppCompatActivity() {
    private lateinit var adapter: ApiUserAdapter
    private lateinit var viewModel: SingleNetworkCallViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        viewModel =
            ViewModelProvider(
                this,
                ViewModelFactory(
                    ApiHelperImpl(RetrofitBuilder.apiService),
                    DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
                )
            ).get(SingleNetworkCallViewModel::class.java)
        adapter = ApiUserAdapter(ArrayList<ApiUser>())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.getUser().observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = GONE
                    it.data?.let { users ->
                        renderList(users)
                    }
                    recyclerView.visibility = VISIBLE
                }

                Status.LOADING -> {
                    progressBar.visibility = VISIBLE
                    recyclerView.visibility = GONE
                }
                Status.ERROR -> {
                    progressBar.visibility = GONE
                    recyclerView.visibility = GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun renderList(users: List<ApiUser>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }

}