package dev.AM.pinlikest.ui.pins

import android.content.Context
import android.util.Log
import androidx.compose.runtime.currentRecomposeScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.PrimaryKey
import dev.AM.pinlikest.data.local.Pin
import dev.AM.pinlikest.data.local.PinsDAO
import dev.AM.pinlikest.data.repository.PinsRepository
import dev.pinlikest.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
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
    val uiState: StateFlow<PinUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.buscarTodos().collect() { pins ->
                _uiState.update { currentState ->
                    currentState.copy(listaDePins = pins)
                }
            }
        }
    }

    suspend fun darLikePin(pin: Pin, pinsDao: PinsDAO) {
        val updatedPin = pin.copy(pinIsLiked = !pin.pinIsLiked)
        pinsDao.atualizar(updatedPin)
    }

    suspend fun darSavePin(pin: Pin, pinsDao: PinsDAO) {
        val updatedPin = pin.copy(pinIsSaved = !pin.pinIsSaved)
        pinsDao.atualizar(updatedPin)
    }

    suspend fun criarPin(
        context: Context,

        imagemPin: String?,
        nomePin: String,
        criadorPin: String,
        pinsDao: PinsDAO
    ) {
        try{
            pinsDao.inserir(
                Pin(
                    image = imagemPin,
                    pinNome = nomePin,
                    pinCriador = criadorPin,
                    pinTopComentario = "",
                    pinIsLiked = false,
                    pinIsSaved = false,
                )
            )
        }catch (e: Exception){
            Log.e("Erro ao adicionar", "Msg: ${e.message}")
        }
    }
    suspend fun buscarPins(pinsDao: PinsDAO): Flow<List<Pin>> {
        return try {
            pinsDao.buscarTodos()
        } catch (e: Exception) {
            Log.e("Erro ao buscar", "${e.message}")
            emptyFlow()
        }
    }
    suspend fun buscarPinsSalvos(pinsDAO: PinsDAO): Flow<List<Pin>> {
        return try {
            pinsDAO.buscarSalvos()
        } catch (e: Exception) {
            Log.e("Erro ao buscar", "${e.message}")
            emptyFlow()
        }
    }
    suspend fun deletarPin(
        context: Context,

        id: Int,
        imagemPin: String?,
        nomePin: String,
        criadorPin: String,
        pinsDao: PinsDAO
    ) {
        try{
            pinsDao.deletar(
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
        }catch (e: Exception){
            Log.e("Erro ao remover", "Msg: ${e.message}")
        }
    }

    fun pinIniciais(): List<Pin> {
        val pinIniciais = listOf(
            Pin(
                image = R.drawable.pin1.toString(),
                pinNome = "eu",
                pinCriador = "OgrandeMago13",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin2.toString(),
                pinNome = "não",
                pinCriador = "OgrandeMago3309",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin3.toString(),
                pinNome = "acredito",
                pinCriador = "OgrandeMago1",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin4.toString(),
                pinNome = "nisso",
                pinCriador = "OgrandeMago3309",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin5.toString(),
                pinNome = "q",
                pinCriador = "OgrandeMago2",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin6.toString(),
                pinNome = "isso",
                pinCriador = "OgrandeMago3309",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin7.toString(),
                pinNome = "aqui",
                pinCriador = "OgrandeMago3309",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin8.toString(),
                pinNome = "funcionou",
                pinCriador = "",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin9.toString(),
                pinNome = "Hutao Wallpaper blah blah",
                pinCriador = "RedEnjoyer",
                pinTopComentario = "nice:D",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin10.toString(),
                pinNome = "Hatsune Miku",
                pinCriador = "Miku",
                pinTopComentario = "Miku:D",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin11.toString(),
                pinNome = "Ib",
                pinCriador = "Ib",
                pinTopComentario = "Ib",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin12.toString(),
                pinNome = "My Honest Reaction",
                pinCriador = "Fozi",
                pinTopComentario = "",
                pinIsLiked = true,
                pinIsSaved = true
            ),
            Pin(
                image = R.drawable.pin13.toString(),
                pinNome = "My Honest Reaction",
                pinCriador = "Fozi",
                pinTopComentario = "",
                pinIsLiked = true,
                pinIsSaved = true
            ),
            Pin(
                image = R.drawable.pin14.toString(),
                pinNome = "Mary Ib",
                pinCriador = "IbFan241",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin15.toString(),
                pinNome = "My Honest Reaction",
                pinCriador = "Fozi",
                pinTopComentario = "",
                pinIsLiked = true,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin16.toString(),
                pinNome = "Bocchi from Fortnite",
                pinCriador = "",
                pinTopComentario = "she's not even from fort lmao",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin17.toString(),
                pinNome = "Miyabi",
                pinCriador = "RedEnjoyer",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin18.toString(),
                pinNome = "Miyabi again",
                pinCriador = "",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin19.toString(),
                pinNome = "My Honest Reaction",
                pinCriador = "Fozi",
                pinTopComentario = "",
                pinIsLiked = true,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin20.toString(),
                pinNome = "Yoimiya",
                pinCriador = "GG",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin21.toString(),
                pinNome = "idk",
                pinCriador = "AnimePfps",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin22.toString(),
                pinNome = "Clorinde",
                pinCriador = "",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin23.toString(),
                pinNome = "YaeMiko",
                pinCriador = "WallpaperKing",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin24.toString(),
                pinNome = "KlK",
                pinCriador = "WallpaperKing",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin25.toString(),
                pinNome = "Columbina",
                pinCriador = "GG",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin26.toString(),
                pinNome = "Beautiful",
                pinCriador = "",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin27.toString(),
                pinNome = "man",
                pinCriador = "RedEnjoyer",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin28.toString(),
                pinNome = "truly",
                pinCriador = "",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin29.toString(),
                pinNome = "this",
                pinCriador = "RedEnjoyer",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin30.toString(),
                pinNome = "is",
                pinCriador = "RedEnjoyer",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin_amaldicoado_31_.toString(),
                pinNome = "como?WTF",
                pinCriador = "anticristo",
                pinTopComentario = "ta repreendido",
                pinIsLiked = true,
                pinIsSaved = true
            ),
            Pin(
                image = R.drawable.pin32.toString(),
                pinNome = "Xinyan",
                pinCriador = "RedEnjoyer",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin33.toString(),
                pinNome = "Animepfp",
                pinCriador = "",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin34.toString(),
                pinNome = "Ib Again",
                pinCriador = "",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin35.toString(),
                pinNome = "My Honest Reaction",
                pinCriador = "Fozi",
                pinTopComentario = "",
                pinIsLiked = true,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin36.toString(),
                pinNome = "Abo",
                pinCriador = "",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin37.toString(),
                pinNome = "My Honest Reaction",
                pinCriador = "Fozi",
                pinTopComentario = "",
                pinIsLiked = true,
                pinIsSaved = true
            ),
            Pin(
                image = R.drawable.pin38.toString(),
                pinNome = "Animepfp",
                pinCriador = "RedEnjoyer",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin39.toString(),
                pinNome = "OguriCap",
                pinCriador = "",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin40.toString(),
                pinNome = "My Honest Reaction",
                pinCriador = "Fozi",
                pinTopComentario = "",
                pinIsLiked = true,
                pinIsSaved = true
            ),
            Pin(
                image = R.drawable.pin41.toString(),
                pinNome = "Red",
                pinCriador = "RedEnjoyer",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin42.toString(),
                pinNome = "WallpaperMobile",
                pinCriador = "WallpaperKing",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin43.toString(),
                pinNome = "RedGirl",
                pinCriador = "RedEnjoyer",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin44.toString(),
                pinNome = "My Honest Reaction",
                pinCriador = "Fozi",
                pinTopComentario = "",
                pinIsLiked = true,
                pinIsSaved = true
            ),
            Pin(
                image = R.drawable.pin45.toString(),
                pinNome = "Pfp",
                pinCriador = "",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin46.toString(),
                pinNome = "Miyabi Once Again",
                pinCriador = "",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = true
            ),
            Pin(
                image = R.drawable.pin47.toString(),
                pinNome = "Ryuko",
                pinCriador = "",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin48.toString(),
                pinNome = "My Honest Reaction",
                pinCriador = "Fozi",
                pinTopComentario = "",
                pinIsLiked = true,
                pinIsSaved = true
            ),
            Pin(
                image = R.drawable.pin49.toString(),
                pinNome = "I'm all ears",
                pinCriador = "",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin50.toString(),
                pinNome = "Wallpaper Water",
                pinCriador = "WallpaperKing",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin51.toString(),
                pinNome = "Great Mystical Being",
                pinCriador = "idkwhattoputhere",
                pinTopComentario = ">O<",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin52.toString(),
                pinNome = "Briar League",
                pinCriador = "",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin53.toString(),
                pinNome = "Fischl",
                pinCriador = "",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin54.toString(),
                pinNome = "RedSword lol",
                pinCriador = "WallpaperKing",
                pinTopComentario = "",
                pinIsLiked = false,
                pinIsSaved = false
            ),
            Pin(
                image = R.drawable.pin55.toString(),
                pinNome = "My Honest Reaction",
                pinCriador = "Fozi",
                pinTopComentario = "Why are we still here",
                pinIsLiked = true,
                pinIsSaved = true
            ),
        )
        return pinIniciais
    }

}