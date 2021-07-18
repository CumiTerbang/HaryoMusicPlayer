package com.haryop.haryomusicplayer.data.remote

import com.haryop.haryomusicplayer.data.entities.ContentEntities
import com.haryop.haryomusicplayer.data.entities.ContentEntity
import com.haryop.mynewsportal.utils.ConstantsObj
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("search?media=music")
    suspend fun getSearchContent(@Query("term") term: String?): Response<ContentEntities>

}