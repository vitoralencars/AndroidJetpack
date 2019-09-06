package com.vitor.cursojetpack.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Dog(
    @ColumnInfo(name = "breed_id")
    @SerializedName("id")
    val id: String?,

    @ColumnInfo(name = "dog_name")
    @SerializedName("name")
    val breed: String?,

    @ColumnInfo(name = "life_span")
    @SerializedName("life_span")
    val lifeSpan: String?,

    @ColumnInfo(name = "breed_group")
    @SerializedName("breed_group")
    val breedGroup: String?,

    @ColumnInfo(name = "bred_for")
    @SerializedName("bred_for")
    val bredFor: String?,

    @ColumnInfo(name = "temperament")
    @SerializedName("temperament")
    val temperament: String?,

    @ColumnInfo(name = "image_url")
    @SerializedName("url")
    val imageUrl: String?
){
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}

data class DogPalette(
    var color: Int
)