package dev.pinlikest.ui.mensagens

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.pinlikest.data.local.AppDatabase.Companion.getDatabase
import dev.pinlikest.data.repository.MensagensRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesCreateScreen(
    context: Context,
    toHome:() -> Unit,
    toPinCreate:() -> Unit,
    toProfile:() -> Unit,
    onBack: () -> Boolean,

    viewModel: MensagensViewModel = viewModel(
        factory = MensagensViewModel.MensagensViewModelFactory(
            MensagensRepository(getDatabase(LocalContext.current).mensagensDAO())
        )
    )
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text("Criação de Mensagem") },
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
                                onBack()
                                Log.d("botaoBacktoMessages", "usuario-clicouBacktoMessages")
                            }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
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
                        Icon(
                            imageVector = Icons.Outlined.Home,
                            contentDescription = "",
                            modifier = Modifier.size(30.dp)
                        )
                    }
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
                    Icon(
                        imageVector = Icons.Filled.Email,
                        contentDescription = "",
                        modifier = Modifier.size(40.dp)
                    )
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
            ) {
                MessageController(viewModel, context)
            }
        }
    )
}
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MessageController(
    viewModel: MensagensViewModel,
    context: Context
) {
    var messageTitulo by remember { mutableStateOf("") }
    var messageDescricao by remember { mutableStateOf("") }
    var messageRemetente by remember { mutableStateOf("") }
    var messageDestinatario by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                TextField(
                    value = messageTitulo,
                    onValueChange = { messageTitulo = it },
                    placeholder = { Text("Título") },
                    modifier = Modifier.fillMaxWidth(),
                )
                TextField(
                    value = messageDescricao,
                    onValueChange = { messageDescricao = it },
                    placeholder = { Text("Descrição") },
                    modifier = Modifier.fillMaxWidth(),
                )
                TextField(
                    value = messageRemetente,
                    onValueChange = { messageRemetente = it },
                    placeholder = { Text("Remetente") },
                    modifier = Modifier.fillMaxWidth(),
                )
                TextField(
                    value = messageDestinatario,
                    onValueChange = { messageDestinatario = it },
                    placeholder = { Text("Destinatário") },
                    modifier = Modifier.fillMaxWidth(),
                )
                Button(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        if (messageTitulo.isNotBlank() && messageDestinatario.isNotBlank()) {
                            viewModel.inserirMensagem(
                                context,
                                messageTitulo,
                                messageDescricao,
                                messageRemetente,
                                messageDestinatario
                            )
                            messageTitulo = ""
                            messageDescricao = ""
                            messageRemetente = ""
                            messageDestinatario = ""
                        }
                    }
                ) { Text("Mandar mensagem!") }
            }
        }
    }
}

