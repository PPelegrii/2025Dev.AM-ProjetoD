package dev.AM.pinlikest.ui

import android.R
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import dev.AM.pinlikest.data.local.AppDatabase.Companion.getDatabase
import dev.AM.pinlikest.data.local.Pin
import dev.AM.pinlikest.data.local.PinsDAO
import dev.AM.pinlikest.data.local.botaoAlerta
import dev.AM.pinlikest.ui.pins.buscarPins
import kotlinx.coroutines.flow.emptyFlow

class HomeScreenActivity : ComponentActivity() {
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
fun HomeScreen(
    context: Context,
    onClickPinDetails: (Pin) -> Unit,

    toPinCreate: () -> Unit,
    toMessages: () -> Unit,
    toProfile: () -> Unit
) {
    val db = getDatabase(context)
    val pinsDao = db.pinsDAO()

    var pins by remember { mutableStateOf(emptyFlow<List<Pin>>()) }

    LaunchedEffect(Unit) {
        pins = buscarPins(pinsDao)
        Log.d("Busca ok", "... $pins")
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Para Você") },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
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
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "",
                        modifier = Modifier.size(40.dp)
                    )
                    /*IconButton(onClick = {
                        Log.d("botaoSearch", "usuario-clicouSearch_route")
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "",
                            modifier = Modifier.size(30.dp)
                        )
                    }*/
                    IconButton(onClick = {
                        toPinCreate()
                        Log.d("botaoCreate/Upload", "usuario-clicouCreate/Upload_route")
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Add,
                            contentDescription = "",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    IconButton(onClick = {
                        toMessages()
                        Log.d("botaoMessages", "usuario-clicouMessages_route")
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.MailOutline,
                            contentDescription = "",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    IconButton(onClick = {
                        toProfile()
                        Log.d("botaoUserProfile", "usuario-clicouUserProfile_route")
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.AccountCircle,
                            contentDescription = "",
                            modifier = Modifier.size(30.dp)
                        )
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column(
                            Modifier
                                .weight(1f)
                                .padding(4.dp),
                            verticalArrangement = Arrangement.Center,
                        ) {
                            /*pins.filterIndexed { index, _ -> index % 2 == 0 }
                                .forEach { pin ->
                                    PinHomeTemplate(context, pin) { onClickPinDetails(pin) }
                                }*/
                        }
                        Column(
                            Modifier
                                .weight(1f)
                                .padding(4.dp),
                            verticalArrangement = Arrangement.Center,
                        ) {
                            /*pins.filterIndexed { index, _ -> index % 2 != 0 }
                                .forEach { pin ->
                                    PinHomeTemplate(context, pin) { onClickPinDetails(pin) }
                                }*/
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun PinHomeTemplate(
    context: Context,
    pin: Pin,
    onClickPinDetails: () -> Unit
) {
    val db = getDatabase(context)
    val pinsDao = db.pinsDAO()

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = ShapeDefaults.ExtraSmall,
        modifier = Modifier
            .fillMaxWidth()
            .border(BorderStroke(2.dp, MaterialTheme.colorScheme.surfaceVariant))
            .clickable {
                onClickPinDetails()
                Log.d("usuarioGetPinDetails", "usuarioClicouPin")
            }
    ) {
        PinImage(
            pinImage = pin.image,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .padding(2.dp)
                .fillMaxWidth()
                .height(24.dp)
        ) {
            Text(pin.pinNome)
            IconButton(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        darLikePin(pin, pinsDao)
                    }
                    botaoAlerta(context,
                        if (!pin.pinIsLiked) "Você curtiu o Pin!"
                        else "Você descurtiu o Pin!"
                    )
                    Log.d("ButaoLikePin", "UserLikePinButton")
                }) {
                Icon(
                    imageVector = if (pin.pinIsLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = ""
                )
            }
            IconButton(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        darSavePin(pin, pinsDao)
                    }
                    botaoAlerta(context,
                        if (!pin.pinIsLiked) "Você salvou o Pin em seu perfil!"
                        else "Você retirou o Pin do perfil!"
                    )
                    Log.d("ButaoSavePin", "UserSavePinButton")
                }) {
                Icon(
                    imageVector = if (pin.pinIsSaved) Icons.Filled.Star else Icons.Outlined.AccountBox,
                    contentDescription = ""
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun PinImage(pinImage: String?, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val painter = when {
        pinImage?.startsWith("content://") == true ||
                pinImage?.startsWith("file://") == true ||
                pinImage?.startsWith("http") == true -> {
                return
                }
        else -> {
            val resId = context.resources.getIdentifier(pinImage, "drawable", context.packageName)
            if (resId != 0) painterResource(id = resId)
            else painterResource(R.drawable.ic_menu_report_image)
        }
    }
    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier,
    )
}

suspend fun darLikePin(pin: Pin, pinsDao: PinsDAO) {
    val updatedPin = pin.copy(pinIsLiked = !pin.pinIsLiked)
    pinsDao.atualizar(updatedPin)
}

suspend fun darSavePin(pin: Pin, pinsDao: PinsDAO) {
    val updatedPin = pin.copy(pinIsSaved = !pin.pinIsSaved)
    pinsDao.atualizar(updatedPin)
}
