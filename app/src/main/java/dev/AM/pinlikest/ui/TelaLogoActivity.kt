package dev.AM.pinlikest.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import dev.AM.pinlikest.data.local.AppDatabase
import dev.AM.pinlikest.data.local.Pin
import dev.pinlikest.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class TelaLogoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppNavigation()
        }
    }
}
@Composable
fun TelaLogo(toHome: () -> Unit) {

    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val pinsDao = db.pinsDAO()

    LaunchedEffect(Unit) {
        val pins = pinsDao.buscarTodos()
        if(pins.isEmpty()){
            CoroutineScope(Dispatchers.IO).launch{
                pinsDao.insertPins(pinIniciais())
            }
        }

        db.mensagensDAO().buscarTodos()
        delay(1.seconds)

        toHome()
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
        content = { paddingValues ->
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "",
                        modifier = Modifier.size(120.dp)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "Pinlikest",
                        style = Typography.displayLarge,
                        fontFamily = FontFamily.Serif
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Button(
                        modifier = Modifier.padding(10.dp),
                        onClick = {
                            toHome()
                            Log.d("botaoHome", "usuario-clicouHome_route")
                        }) {
                        Text("Explorar suas próximas ideias")
                    }
                }
            }
        }
    )
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
