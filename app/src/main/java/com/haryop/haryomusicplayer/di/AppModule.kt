package com.haryop.haryomusicplayer.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.haryop.haryomusicplayer.data.remote.ApiServices
import com.haryop.haryomusicplayer.data.remote.RemoteDataSource
import com.haryop.haryomusicplayer.data.repository.DataRepository
import com.haryop.haryomusicplayer.ui.ConstantsObj
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    fun logOkHttplient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return client
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(ConstantsObj.API_BASEURL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(logOkHttplient())
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideAPIService(retrofit: Retrofit): ApiServices =
        retrofit.create(ApiServices::class.java)

    @Singleton
    @Provides
    fun provideRemoteDataSource(apiServices: ApiServices) =
        RemoteDataSource(apiServices)

    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSource: RemoteDataSource,
        apiServices: ApiServices
    ) =
        DataRepository(remoteDataSource, apiServices)

}
