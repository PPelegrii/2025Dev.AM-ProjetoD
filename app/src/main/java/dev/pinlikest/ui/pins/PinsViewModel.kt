package dev.pinlikest.ui.pins

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dev.pinlikest.data.local.Pin
import dev.pinlikest.data.local.PinsDAO
import dev.pinlikest.data.repository.PinsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PinUiState(

    val listaDePins: List<Pin> = emptyList(),
    val image: String? = "",
    val pinNome: String = "",
    val pinCriador: String = "",
    val pinTopComentario: String = "",
    val pinIsLiked: Boolean = false,
    val pinIsSaved: Boolean = false
)
class PinsViewModel(private val repository: PinsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(PinUiState())
    val uiState: StateFlow<PinUiState> = _uiState

    init {
        viewModelScope.launch {
            repository.buscarTodos().collect { pins ->
                _uiState.update { currentState ->
                    currentState.copy(pins)
                }
            }
        }
    }

    fun toggleLike(pin: Pin, pinsDao: PinsDAO) {
        viewModelScope.launch {
            try {
                val updatedPin = pin.copy(pinIsLiked = !pin.pinIsLiked)
                pinsDao.atualizar(updatedPin)
            } catch (e: Exception) {
                Log.e("PinsViewModel", "Erro ao dar like: ${e.message}")
            }
        }
    }
    fun toggleSave(pin: Pin, pinsDao: PinsDAO) {
        viewModelScope.launch {
            try {
                val updatedPin = pin.copy(pinIsSaved = !pin.pinIsSaved)
                pinsDao.atualizar(updatedPin)
            } catch (e: Exception) {
                Log.e("PinsViewModel", "Erro ao salvar: ${e.message}")
            }
        }
    }
    fun criarPin(

        imagemPin: String?,
        nomePin: String,
        criadorPin: String,
    ) {
        try{
            viewModelScope.launch {
                repository.inserir(
                    Pin(
                        image = imagemPin,
                        pinNome = nomePin,
                        pinCriador = criadorPin,
                        pinTopComentario = "",
                        pinIsLiked = false,
                        pinIsSaved = false,
                    )
                )
            }
        }catch (e: Exception){
            Log.e("Erro ao adicionar", "Msg: ${e.message}")
        }
    }
    fun buscarPins(){
        try {
            viewModelScope.launch {
                repository.buscarTodos().collect { pins ->
                    _uiState.update { it.copy(listaDePins = pins) }
                }
            }
        } catch (e: Exception) {
            Log.e("Erro ao buscar", "${e.message}")
        }
    }
    fun buscarPinsSalvos(){
        try {
            viewModelScope.launch {
                repository.buscarTodos().collect { pins ->
                    _uiState.update { it.copy(listaDePins = pins) }
                }
            }
        } catch (e: Exception) {
            Log.e("Erro ao buscar", "${e.message}")
        }
    }
    suspend fun deletarPin(

        id: Int,
        imagemPin: String?,
        nomePin: String,
        criadorPin: String,
    ) {
        try{
            viewModelScope.launch {
                repository.deletar(
                    Pin(
                        id,
                        imagemPin,
                        nomePin,
                        criadorPin,
                        pinTopComentario = "",
                        pinIsLiked = false,
                        pinIsSaved = false,
                    )
                )
            }
        }catch (e: Exception){
            Log.e("Erro ao remover", "Msg: ${e.message}")
        }
    }
    fun shufflePins() {
        _uiState.update { current ->
            current.copy(listaDePins = current.listaDePins.shuffled())
        }
    }
    class PinsViewModelFactory(private val repository: PinsRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PinsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PinsViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}