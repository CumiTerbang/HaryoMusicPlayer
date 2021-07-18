package com.haryop.haryomusicplayer.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiServices: ApiServices
) : BaseDataSource() {

    suspend fun getSearchContent(term: String) = getResult { apiServices.getSearchContent(term) }

}