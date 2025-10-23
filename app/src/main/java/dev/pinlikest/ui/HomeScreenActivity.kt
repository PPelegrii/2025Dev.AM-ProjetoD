package dev.pinlikest.ui

import android.R
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Star
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import dev.pinlikest.data.local.AppDatabase.Companion.getDatabase
import dev.pinlikest.data.local.Pin
import dev.pinlikest.data.local.botaoAlerta
import dev.pinlikest.ui.pins.PinsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.pinlikest.data.repository.PinsRepository

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
    toProfile: () -> Unit,

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
                    Icon(Icons.Filled.Home, "", Modifier.size(40.dp))
                    IconButton(onClick = {
                        toPinCreate()
                        Log.d("botaoCreate/Upload", "usuario-clicouCreate/Upload_route")
                    }) {
                        Icon(Icons.Outlined.Add, "", Modifier.size(30.dp))
                    }
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
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier,
                contentPadding = paddingValues,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalItemSpacing = 8.dp
            ) {
                items(uiState.listaDePins) { pin ->
                    PinHomeTemplate(context, pin,{ onClickPinDetails(pin) })
                }
            }
        }
    )
}
@Composable
fun PinHomeTemplate(
    context: Context,
    pin: Pin,
    onClickPinDetails: () -> Unit,

    viewModel: PinsViewModel = viewModel(
        factory = PinsViewModel.PinsViewModelFactory(
            PinsRepository(getDatabase(LocalContext.current).pinsDAO())
        )
    )
) {
    val db = getDatabase(context)
    val pinsDao = db.pinsDAO()

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        shape = ShapeDefaults.ExtraSmall,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickPinDetails() }
    ) {
        PinImage(pinImage = pin.image)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(2.dp)
        ) {
            Text(pin.pinNome)
            IconButton(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.toggleLike(pin, pinsDao)
                    }
                    botaoAlerta(context, "Você curtiu/descurtiu o Pin!")
                }
            ) {
                Icon(
                    imageVector = if (pin.pinIsLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = ""
                )
            }
            IconButton(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.toggleSave(pin, pinsDao)
                    }
                    botaoAlerta(context, "Pin salvo/desmarcado!")
                }
            ) {
                Icon(
                    imageVector = if (pin.pinIsSaved) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = ""
                )
            }
        }
    }
}

@Composable
fun PinImage(pinImage: String?, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val painter = when {
        pinImage?.startsWith("content://") == true || pinImage?.startsWith("file://") == true || pinImage?.startsWith("http") == true -> {
            painterResource(id = pinImage.toInt())
        }
        else -> {
            val resId = context.resources.getIdentifier(pinImage, "drawable", context.packageName)
            if (resId != 0) painterResource(id = resId) else painterResource(R.drawable.ic_menu_report_image)
        }
    }
    Image(painter = painter, contentDescription = null, modifier = modifier)
}