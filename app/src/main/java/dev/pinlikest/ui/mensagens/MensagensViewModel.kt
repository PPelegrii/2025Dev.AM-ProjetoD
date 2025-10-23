package dev.pinlikest.ui.mensagens

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dev.pinlikest.data.local.Mensagem
import dev.pinlikest.data.local.botaoAlerta
import dev.pinlikest.data.repository.MensagensRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MensagemUiState(
    val listaDeMensagens: List<Mensagem> = emptyList(),
    val mensagemTitulo: String = "",
    val mensagemDescricao: String = "",
    val mensagemRemetente: String = "",
    val mensagemDestinatario: String = ""
)

class MensagensViewModel(private val repository: MensagensRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(MensagemUiState())
    val uiState: StateFlow<MensagemUiState> = _uiState

    init {
        viewModelScope.launch {
            repository.buscarTodos().collect { mensagens ->
                _uiState.update { currentState ->
                    currentState.copy(mensagens)
                }
            }
        }
    }

    fun inserirMensagem(
        context: Context,
        mensagemTitulo: String,
        mensagemDescricao: String,
        mensagemRemetente: String,
        mensagemDestinatario: String
    ) {
        viewModelScope.launch {
            try {
                viewModelScope.launch {
                    repository.inserir(
                        Mensagem(
                            mensagemTitulo = mensagemTitulo,
                            mensagemDescricao = mensagemDescricao,
                            mensagemRemetente = mensagemRemetente,
                            mensagemDestinatario = mensagemDestinatario
                        )
                    )
                }
                botaoAlerta(context, "Mensagem adicionada :)")
            } catch (e: Exception) {
                Log.e("Erro ao adicionar", "Msg: ${e.message}")
            }
        }
    }

    fun atualizarMensagem(mensagem: Mensagem, context: Context) {
        viewModelScope.launch {
            try {
                viewModelScope.launch {
                    repository.atualizar(mensagem)
                }
            } catch (e: Exception) {
                Log.e("Erro ao atualizar", "Msg: ${e.message}")
            }
        }
    }

    fun deletarMensagem(mensagem: Mensagem, context: Context) {
        viewModelScope.launch {
            try {
                viewModelScope.launch {
                    repository.deletar(mensagem)
                }
                botaoAlerta(context, "Mensagem removida :)")
            } catch (e: Exception) {
                Log.e("Erro ao remover", "Msg: ${e.message}")
            }
        }
    }

    class MensagensViewModelFactory(private val repository: MensagensRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MensagensViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MensagensViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}