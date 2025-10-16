package dev.AM.pinlikest.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MensagensDAO{

    @Insert
    suspend fun inserir(mensagem: Mensagem)

    @Query("SELECT * FROM mensagens")
    suspend fun buscarTodos() : Flow<List<Mensagem>>

    @Delete
    suspend fun deletar(mensagem: Mensagem)

    @Update
    suspend fun atualizar(mensagem: Mensagem)

}