package com.vitor.cursojetpack.service

import com.vitor.cursojetpack.model.Dog
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DogsService{

    private val BASE_URL = "https://raw.githubusercontent.com/devtides/dogsapi/master/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(DogsApi::class.java)

    fun getDogs(): Single<List<Dog>>{
        return retrofit.getDogs()
    }

}