package tech.jhavidit.coroutinesdemo.view.seriesNetworkCall

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tech.jhavidit.coroutinesdemo.model.ApiUser
import tech.jhavidit.coroutinesdemo.util.Resource


class SeriesNetworkCallViewModel : ViewModel() {

    private val users = MutableLiveData<Resource<List<ApiUser>>>()



}