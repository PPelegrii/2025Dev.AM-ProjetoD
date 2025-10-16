package dev.AM.pinlikest.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mensagens")
data class Mensagem(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val mensagemTitulo: String,
    val mensagemDescricao: String,
    val mensagemRemetente: String,
    val mensagemDestinatario: String
)