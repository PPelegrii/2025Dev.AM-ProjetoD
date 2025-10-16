package dev.AM.pinlikest.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pins")
data class Pin(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val image: String?,
    val pinNome: String,
    val pinCriador: String,
    val pinTopComentario: String,
    val pinIsLiked: Boolean,
    val pinIsSaved: Boolean
)


