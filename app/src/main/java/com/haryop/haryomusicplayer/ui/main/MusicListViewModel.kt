package com.haryop.haryomusicplayer.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.haryop.haryomusicplayer.data.entities.ContentEntity
import com.haryop.haryomusicplayer.data.repository.DataRepository
import com.haryop.haryomusicplayer.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MusicListViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {
    fun start(term: String) {
        _term.value = term
    }

    private val _term = MutableLiveData<String>()

    private val _getSearchContent = _term.switchMap { source ->
        dataRepository.getSearchContent(source)
    }

    val getSearchContent: LiveData<Resource<List<ContentEntity>>> = _getSearchContent

}