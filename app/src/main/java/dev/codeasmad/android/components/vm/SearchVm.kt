package dev.codeasmad.android.components.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.codeasmad.android.components.data.GithubRepo
import dev.codeasmad.android.components.data.Repository
import dev.codeasmad.android.networking.Resource
import kotlinx.coroutines.launch

class SearchVm : ViewModel() {

    private val _query = MutableLiveData<String>()
    val query: LiveData<String>
        get() = _query

    private val _repos = MutableLiveData<List<Repository>>()

    init {
        _query.observeForever {
            if (it.length > 3) {
                searchRepos(it)
            }
        }
    }

    fun setQuery(q: String) {
        _query.value = q
    }

    private fun searchRepos(query: String) {
        viewModelScope.launch {
            when (val repoRes = GithubRepo.searchRepo(query)) {
                is Resource.Failure -> {
                    Log.e("API", "Error", repoRes.error)
                }
                is Resource.Success -> {
                    _repos.postValue(repoRes.data.repos)
                }
            }
        }
    }
}