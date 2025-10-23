package dev.pinlikest.ui.pins

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.pinlikest.ui.AppNavigation
import dev.pinlikest.ui.PinImage
import dev.pinlikest.data.local.AppDatabase.Companion.getDatabase
import dev.pinlikest.data.local.Pin
import dev.pinlikest.data.local.botaoAlerta
import dev.pinlikest.data.repository.PinsRepository
import dev.pinlikest.ui.PinHomeTemplate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PinDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppNavigation()
        }
    }
}
@Composable
fun PinDetails(
    pinImg: Any,
    pinNome: String,
    pinCriador: String?,
    pinTopComentario: String?,
    onBack: () -> Unit,
    viewModel: PinsViewModel = viewModel(
        factory = PinsViewModel.PinsViewModelFactory(
            PinsRepository(getDatabase(LocalContext.current).pinsDAO())
        )
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val db = getDatabase(context)
    val pinsDao = db.pinsDAO()



    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
        content = { paddingValues ->
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                IconButton(
                    modifier = Modifier
                        .size(width = 40.dp, height = 40.dp),
                    onClick = {
                        onBack()
                        Log.d("botaoBackToHome", "usuario-clicouVoltarParaHome")
                    }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "",
                        modifier = Modifier.size(40.dp)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    PinImage(
                        pinImage = pinImg.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(25.dp)
                ) {
                    Text(
                        text = uiState.pinNome,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .padding(start = 12.dp)

                    )
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.End
                        ) {

                            IconButton(onClick = {
                                uiState.listaDePins.forEach { pin ->
                                    viewModel.toggleLike(pin, pinsDao)

                                    val newLiked = !pin.pinIsLiked
                                    botaoAlerta(
                                        context,
                                        if (newLiked) "Você curtiu o Pin!" else "Você descurtiu o Pin!"
                                    )
                                }
                            }) {
                                Icon(
                                    imageVector = if (uiState.pinIsLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                    contentDescription = ""
                                )
                                Log.d("botaoLike", "usuario-clicouCurtirPin")

                                IconButton(onClick = {
                                    uiState.listaDePins.forEach { pin ->
                                        viewModel.toggleSave(pin, pinsDao)

                                        val newSaved = !pin.pinIsSaved
                                        botaoAlerta(
                                            context,
                                            if (newSaved) "Você salvou o Pin!" else "Você removeu o Pin dos salvos!"
                                        )
                                    }
                                }) {
                                    Icon(
                                        imageVector = if (uiState.pinIsLiked) Icons.Filled.AccountBox else Icons.Outlined.AccountBox,
                                        contentDescription = ""
                                    )
                                    Log.d("botaoSave", "usuario-clicouSalvarPin")
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    if (pinCriador != null) {
                        Text(
                            text = uiState.pinCriador,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .padding(start = 12.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    if (pinTopComentario != null) {
                        Text(
                            text = uiState.pinTopComentario,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                //.padding(start = 36.dp)
                        )
                    }
                }
            }
        }
    )
}