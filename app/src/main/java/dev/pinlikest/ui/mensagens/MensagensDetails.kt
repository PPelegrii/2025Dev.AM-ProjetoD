package dev.pinlikest.ui.mensagens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.pinlikest.data.local.AppDatabase.Companion.getDatabase
import dev.pinlikest.data.local.Mensagem
import dev.pinlikest.data.local.botaoAlerta
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MensagemDetailsScreen(
    context: Context,
    toHome:() -> Unit,
    toPinCreate:() -> Unit,
    toProfile:() -> Unit,

    mensagem: Mensagem?,
    onBack: () -> Unit
) {
    val db = getDatabase(context)
    val mensagensDao = db.mensagensDAO()

    var titulo by remember { mutableStateOf(mensagem?.mensagemTitulo ?: "") }
    var descricao by remember { mutableStateOf(mensagem?.mensagemDescricao ?: "") }
    var remetente by remember { mutableStateOf(mensagem?.mensagemRemetente ?: "") }
    var destinatario by remember { mutableStateOf(mensagem?.mensagemDestinatario ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text("Editar Mensagem") },
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
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = descricao,
                    onValueChange = { descricao = it },
                    label = { Text("Descrição") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = remetente,
                    onValueChange = { remetente = it },
                    label = { Text("Remetente") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = destinatario,
                    onValueChange = { destinatario = it },
                    label = { Text("Destinatário") },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = {
                        CoroutineScope(Dispatchers.IO).launch {
                            val mensagemAtualizada = mensagem!!.copy(
                                mensagemTitulo = titulo,
                                mensagemDescricao = descricao,
                                mensagemRemetente = remetente,
                                mensagemDestinatario = destinatario
                            )
                            mensagensDao.atualizar(mensagemAtualizada)
                        }
                        botaoAlerta(context, "Mensagem atualizada!")
                        onBack()
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Salvar")
                }
            }
        }
    )
}