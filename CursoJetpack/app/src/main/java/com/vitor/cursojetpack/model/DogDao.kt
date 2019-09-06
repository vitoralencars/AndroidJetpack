package com.vitor.cursojetpack.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DogDao {

    @Insert
    suspend fun insertAll(vararg dogs: Dog): List<Long>

    @Query("SELECT * FROM Dog")
    suspend fun getAllDogs(): List<Dog>

    @Query("SELECT * FROM Dog WHERE uuid = :dogId")
    suspend fun getDog(dogId: Int): Dog

    @Query("DELETE FROM Dog")
    suspend fun deleteAllDogs()
}