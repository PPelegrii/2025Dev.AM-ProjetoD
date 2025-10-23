package dev.pinlikest.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PinsDAO{
    @Insert
    fun insertPins(pinIniciais: List<Pin>)
    @Insert
    suspend fun inserir(pin: Pin)

    @Query("SELECT * FROM pins")
    fun buscarTodos() : Flow<List<Pin>>

    @Query("SELECT * FROM pins WHERE id = :id")
    suspend fun buscarPorId(id: Int): Pin

    @Query("SELECT * FROM pins WHERE pinIsSaved = 1")
    fun buscarSalvos() : Flow<List<Pin>>

    @Delete
    suspend fun deletar(pin: Pin)

    @Update
    suspend fun atualizar(pin: Pin)

}
