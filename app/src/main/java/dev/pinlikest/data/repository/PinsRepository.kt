package dev.pinlikest.data.repository

import dev.pinlikest.data.local.Pin
import dev.pinlikest.data.local.PinsDAO
import kotlinx.coroutines.flow.Flow

class PinsRepository(private val pinsDao: PinsDAO) {

    fun insertPins(pinIniciais: List<Pin>){
        return pinsDao.insertPins(pinIniciais)
    }
    fun buscarTodos(): Flow<List<Pin>> {
        return pinsDao.buscarTodos()
    }

    fun buscarSalvos() : Flow<List<Pin>> {
        return pinsDao.buscarSalvos()
    }

    suspend fun buscarPorId(id: Int): Pin {
        return pinsDao.buscarPorId(id)
    }

    suspend fun inserir(pin: Pin){
        return pinsDao.inserir(pin)
    }

    suspend fun atualizar(pin: Pin){
        return pinsDao.atualizar(pin)
    }

    suspend fun deletar(pin: Pin){
        return pinsDao.deletar(pin)
    }
}
