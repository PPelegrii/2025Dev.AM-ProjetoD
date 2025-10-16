package dev.AM.pinlikest.data.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import dev.AM.pinlikest.data.local.Mensagem
import dev.AM.pinlikest.data.local.MensagensDAO
import dev.AM.pinlikest.data.local.Pin
import dev.AM.pinlikest.data.local.PinsDAO
import kotlinx.coroutines.flow.Flow

class MensagensRepository(private val mensagensDao: MensagensDAO) {

    suspend fun buscarTodos(): Flow<List<Mensagem>> = mensagensDao.buscarTodos()

    suspend fun inserir(mensagem: Mensagem){
        mensagensDao.inserir(mensagem)
    }

    suspend fun atualizar(mensagem: Mensagem){
        mensagensDao.atualizar(mensagem)
    }

    suspend fun deletar(mensagem: Mensagem){
        mensagensDao.deletar(mensagem)
    }

}
