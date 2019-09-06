package com.vitor.cursojetpack.service

import com.vitor.cursojetpack.model.Dog
import io.reactivex.Single
import retrofit2.http.GET

interface DogsApi {

    @GET("dogs.json")
    fun getDogs(): Single<List<Dog>>

}