package dev.AM.pinlikest.data.repository

import androidx.room.Dao
import dev.AM.pinlikest.data.local.Pin
import dev.AM.pinlikest.data.local.PinsDAO
import kotlinx.coroutines.flow.Flow

class PinsRepository(private val pinsDao: PinsDAO) {

    fun insertPins(pinIniciais: List<Pin>){
        pinsDao.insertPins(pinIniciais)
    }
    suspend fun buscarTodos(): Flow<List<Pin>> = pinsDao.buscarTodos()

    suspend fun buscarSalvos() : Flow<List<Pin>> = pinsDao.buscarSalvos()

    suspend fun buscarPorId(id: Int) {
        pinsDao.buscarPorId(id)
    }

    suspend fun inserir(pin: Pin){
        pinsDao.inserir(pin)
    }

    suspend fun atualizar(pin: Pin){
        pinsDao.atualizar(pin)
    }

    suspend fun deletar(pin: Pin){
        pinsDao.deletar(pin)
    }

}
