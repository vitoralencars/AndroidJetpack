package com.vitor.cursojetpack.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.vitor.cursojetpack.model.Dog
import com.vitor.cursojetpack.model.DogDatabase
import kotlinx.coroutines.launch

class DetailViewModel(application: Application): BaseViewModel(application) {

    val dog = MutableLiveData<Dog>()

    fun fetch(dogUuid: Int){
        launch {
            dog.value = DogDatabase(getApplication()).dogDao().getDog(dogUuid)
        }
    }

}