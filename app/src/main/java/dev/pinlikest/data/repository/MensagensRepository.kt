package dev.pinlikest.data.repository

import dev.pinlikest.data.local.Mensagem
import dev.pinlikest.data.local.MensagensDAO
import kotlinx.coroutines.flow.Flow

class MensagensRepository(private val mensagensDao: MensagensDAO) {

    fun buscarTodos(): Flow<List<Mensagem>> = mensagensDao.buscarTodos()

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
