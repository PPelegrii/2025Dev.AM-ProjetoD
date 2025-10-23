package dev.pinlikest.ui.pins

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.pinlikest.ui.AppNavigation
import dev.pinlikest.ui.PinImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import dev.pinlikest.data.local.AppDatabase.Companion.getDatabase
import dev.pinlikest.data.local.Pin
import dev.pinlikest.data.local.botaoAlerta
import dev.pinlikest.data.repository.PinsRepository

class PinCreateActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            AppNavigation()
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PinCreate(
    context: Context,

    toHome: () -> Unit,
    toMessages: () -> Unit,
    toProfile: () -> Unit,

    onPickImage: () -> Unit,
    imageUri: String?,

    viewModel: PinsViewModel = viewModel(
        factory = PinsViewModel.PinsViewModelFactory(
            PinsRepository(getDatabase(LocalContext.current).pinsDAO())
        )
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text("Criação de Pin") },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    Row(
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                Log.d("botaoCreatePin", "usuario-clicouCreatePin")
                            }) {
                            Icon(
                                imageVector = Icons.Default.Create,
                                contentDescription = "",
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxHeight(.087f),
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,

                ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        toHome()
                        Log.d("botaoHome", "usuario-clicouHome_route")
                    }) {
                        Icon(Icons.Outlined.Home, "", Modifier.size(30.dp))
                    }
                    Icon(Icons.Filled.Add, "", Modifier.size(30.dp)
                    )
                    IconButton(onClick = {
                        toMessages()
                        Log.d("botaoMessages", "usuario-clicouMessages_route")
                    }) {
                        Icon(Icons.Outlined.MailOutline, "", Modifier.size(30.dp))
                    }
                    IconButton(onClick = {
                        toProfile()
                        Log.d("botaoUserProfile", "usuario-clicouUserProfile_route")
                    }) {
                        Icon(Icons.Outlined.AccountCircle, "", Modifier.size(30.dp))
                    }
                }
            }
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
            ) {
                item {
                    PinController(context, onPickImage, imageUri)
                }
            }
        }
    )
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PinCreateTemplate(pin: Pin) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        shape = ShapeDefaults.ExtraLarge,
        modifier = Modifier
            .padding(top = 2.dp)
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    Log.d("usuarioGetPin", "usuarioClicouPin")
                }
            )
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth()
            ) {
                PinImage(
                    pinImage = pin.image,
                    modifier = Modifier.fillMaxWidth()
                )
                if(pin.pinNome.isEmpty()){
                    Text(pin.pinNome)
                }
                Text(pin.pinCriador)
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PinController(
    context: Context,
    onPickImage: () -> Unit,
    imageUri: String?,

    viewModel: PinsViewModel = viewModel(
        factory = PinsViewModel.PinsViewModelFactory(
            PinsRepository(getDatabase(LocalContext.current).pinsDAO())
        )
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    var nome by remember { mutableStateOf("") }
    var criador by remember { mutableStateOf("") }

    val pinPreview = Pin(
        image = imageUri,
        pinNome = nome,
        pinCriador = criador,
        pinTopComentario = "",
        pinIsLiked = false,
        pinIsSaved = false
    )

    Surface(
        modifier = Modifier.padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onPickImage
                ) {
                    Text("Escolher imagem")
                }
                TextField(
                    value = nome,
                    onValueChange = { nome = it },
                    placeholder = { Text("Nome do Pin") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = criador,
                    onValueChange = { criador = it },
                    placeholder = { Text("Criador pin") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text("Prévia do Pin:")
                PinCreateTemplate( pinPreview)

                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    shape = ShapeDefaults.ExtraSmall,
                    onClick = {
                        if (!imageUri.isNullOrBlank() && nome.isNotBlank()) {
                            CoroutineScope(Dispatchers.IO).launch {
                                viewModel.criarPin(
                                    imageUri,
                                    nome,
                                    criador,
                                )
                            }
                            botaoAlerta(context, "Pin adicionado :)")
                            Log.d("addPin", "pinAdicionado")

                            nome = ""
                            criador = ""
                        }
                    }
                ) { Text("Adicionar Pin!") }
            }
        }
    }
}