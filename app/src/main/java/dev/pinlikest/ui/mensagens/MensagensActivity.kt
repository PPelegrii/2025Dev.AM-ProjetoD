package dev.pinlikest.ui.mensagens

import android.content.Context
import android.util.Log
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
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Home
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.pinlikest.data.local.AppDatabase.Companion.getDatabase
import dev.pinlikest.data.local.Mensagem
import dev.pinlikest.data.local.botaoAlerta
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesScreen(
    context: Context,
    toHome:() -> Unit,
    toPinCreate:() -> Unit,
    toMessageCreate:() -> Unit,
    toProfile:() -> Unit,
    onClickMessageDetails: (Mensagem) -> Unit
) {
    val dbcontext = LocalContext.current
    val db = getDatabase(dbcontext)
    val mensagensDao = db.mensagensDAO()

    var mensagens by remember { mutableStateOf(emptyFlow<List<Mensagem>>()) }

    LaunchedEffect(Unit) {
        mensagens = buscarMensagens(mensagensDao)
        Log.d("Busca ok", "... $mensagens")
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text("Caixa de Entrada") },
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
                                toMessageCreate()
                                Log.d("botaoCreateMessage", "usuario-clicouCreateMessage")
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
            ) {
                item {
                    Column(
                        Modifier
                            .padding(4.dp),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        /*mensagens.forEach { mensagem ->
                            MensagemTemplate(context, mensagem) {
                                onClickMessageDetails(mensagem)
                        }*/
                         //   Log.d("mensagem", "$mensagem")
                        }
                    }
                }
            //}
        }
    )
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MensagemTemplate(
    context: Context,
    mensagem: Mensagem,
    onClickMessage: () -> Unit
) {
    val db = getDatabase(context)
    val mensagensDao = db.mensagensDAO()

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
                    onClickMessage()
                    Log.d("usuarioGetMensagem", "usuarioClicouMensagem")
                },
                onLongClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        deletarMensagem(
                            context,
                            mensagem.id,
                            mensagem.mensagemTitulo,
                            mensagem.mensagemDescricao,
                            mensagem.mensagemRemetente,
                            mensagem.mensagemDestinatario,
                            mensagensDao
                        )
                    }
                    botaoAlerta(context, "Mensagem removida :(")
                    Log.d("usuarioLongPressMensagem", "usuarioPressionouMensagem")
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
                Text(
                    text = "Titulo: " + mensagem.mensagemTitulo,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "De: " + mensagem.mensagemRemetente,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Descrição: " + mensagem.mensagemDescricao,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    Spacer(modifier = Modifier.height(8.dp))
}