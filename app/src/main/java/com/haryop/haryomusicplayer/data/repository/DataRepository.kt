package com.haryop.haryomusicplayer.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.haryop.haryomusicplayer.data.entities.ContentEntities
import com.haryop.haryomusicplayer.data.entities.ContentEntity
import com.haryop.haryomusicplayer.data.remote.ApiServices
import com.haryop.haryomusicplayer.data.remote.RemoteDataSource
import com.haryop.mynewsportal.utils.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val apiServices: ApiServices
) {

    fun getSearchContent(term: String) = performGetSearchContentOperation(
        networkCall = { remoteDataSource.getSearchContent(term) }
    )

    fun <A> performGetSearchContentOperation(
        networkCall: suspend () -> Resource<A>
    ): LiveData<Resource<List<ContentEntity>>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading())

            val responseStatus = networkCall.invoke()
            if (responseStatus.status == Resource.Status.SUCCESS) {
                val listSource = (responseStatus.data!! as ContentEntities).results
                emit(Resource.success(listSource))

            } else if (responseStatus.status == Resource.Status.ERROR) {
                emit(Resource.error(responseStatus.message!!))
            }
        }

}