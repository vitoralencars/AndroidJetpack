package com.vitor.cursojetpack.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vitor.cursojetpack.model.Dog
import com.vitor.cursojetpack.model.DogDatabase
import com.vitor.cursojetpack.service.DogsService
import com.vitor.cursojetpack.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListViewModel(application: Application): BaseViewModel(application) {

    private var prefHelper = SharedPreferencesHelper(application)
    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L

    private val dogsService = DogsService()
    private val disposable = CompositeDisposable()

    val dogs = MutableLiveData<List<Dog>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(){
        val updateTime = prefHelper.getUpdateTime()

        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){
            fetchFromDatabase()
        }else {
            fetchFromRemote()
        }
    }

    fun refreshBypassCache(){
        fetchFromRemote()
    }

    private fun fetchFromDatabase(){
        loading.value = true
        launch {
            val dogs = DogDatabase(getApplication()).dogDao().getAllDogs()
            dogsRetrieved(dogs)
        }
    }

    private fun fetchFromRemote(){
        disposable.add(
            dogsService.getDogs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Dog>>(){
                    override fun onSuccess(dogList: List<Dog>) {
                        storeDogsLocally(dogList)
                    }

                    override fun onError(e: Throwable) {
                        dogsLoadError.value = true
                        loading.value = false
                    }
                })
        )
    }

    private fun dogsRetrieved(dogList: List<Dog>){
        dogs.value = dogList
        dogsLoadError.value = false
        loading.value = false
    }

    private fun storeDogsLocally(dogList: List<Dog>){
        launch {
            val dao = DogDatabase(getApplication()).dogDao()
            dao.deleteAllDogs()
            val result = dao.insertAll(*dogList.toTypedArray())

            var i = 0
            while(i < dogList.size){
                dogList[i].uuid = result[i].toInt()
                ++i
            }
            dogsRetrieved(dogList)
        }

        prefHelper.saveUpdateTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}