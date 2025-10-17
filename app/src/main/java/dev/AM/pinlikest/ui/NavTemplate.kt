package dev.AM.pinlikest.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavTemplate(
    toHome: () -> Unit,
    toPinCreate: () -> Unit,
    toMessages: () -> Unit,
    toProfile: () -> Unit
) {
Scaffold( topBar = {
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
            IconButton(onClick = {
                toHome()
                Log.d("botaoHome", "usuario-clicouHome_route")
            }) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "",
                modifier = Modifier.size(40.dp)
            )
                }
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
content = { paddingValues -> NavTemplate(toHome, toPinCreate, toMessages, toProfile)}
)
}