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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.pinlikest.data.local.AppDatabase.Companion.getDatabase
import dev.pinlikest.data.local.Mensagem
import dev.pinlikest.data.local.botaoAlerta
import dev.pinlikest.data.repository.MensagensRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesScreen(
    toHome:() -> Unit,
    toPinCreate:() -> Unit,
    toMessageCreate:() -> Unit,
    toProfile:() -> Unit,
    onClickMessageDetails: (Mensagem) -> Unit,

    viewModel: MensagensViewModel = viewModel(
        factory = MensagensViewModel.MensagensViewModelFactory(
            MensagensRepository(getDatabase(LocalContext.current).mensagensDAO())
        )
    )
) {
    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsState()

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
                            Icon(Icons.Default.Create,"",Modifier.size(40.dp)
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
                        Icon(Icons.Outlined.Home, "", Modifier.size(30.dp)
                        )
                    }
                    IconButton(onClick = {
                        toPinCreate()
                        Log.d("botaoCreate/Upload", "usuario-clicouCreate/Upload_route")
                    }) {
                        Icon(Icons.Outlined.Add, "", Modifier.size(30.dp)
                        )
                    }
                    Icon(Icons.Filled.Email, "", Modifier.size(40.dp)
                    )
                    IconButton(onClick = {
                        toProfile()
                        Log.d("botaoUserProfile", "usuario-clicouUserProfile_route")
                    }) {
                        Icon(Icons.Outlined.AccountCircle, "", Modifier.size(30.dp)
                        )
                    }
                }
            }
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(uiState.listaDeMensagens) { mensagem ->
                    MensagemTemplate(
                        context = context,
                        mensagem = mensagem,
                        onClickMessage = { onClickMessageDetails(mensagem) },
                        onDeleteMessage = { viewModel.deletarMensagem(mensagem, context)
                        }
                    )
                }
            }
        }
    )
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MensagemTemplate(
    context: Context,
    mensagem: Mensagem,
    onClickMessage: () -> Unit,
    onDeleteMessage: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        shape = ShapeDefaults.ExtraLarge,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClickMessage,
                onLongClick = {
                    onDeleteMessage()
                    botaoAlerta(context, "Mensagem removida :(")
                }
            )
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Titulo: ${mensagem.mensagemTitulo}", fontWeight = FontWeight.ExtraBold)
                Text("De: ${mensagem.mensagemRemetente}", fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text("Descrição: ${mensagem.mensagemDescricao}", fontWeight = FontWeight.Normal)
        }
    }
}