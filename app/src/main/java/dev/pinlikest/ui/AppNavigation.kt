package dev.pinlikest.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.pinlikest.MainActivity
import dev.pinlikest.data.local.AppDatabase
import dev.pinlikest.data.local.Mensagem
import dev.pinlikest.ui.mensagens.MensagemDetailsScreen
import dev.pinlikest.ui.mensagens.MessagesCreateScreen
import dev.pinlikest.ui.mensagens.MessagesScreen
import dev.pinlikest.ui.pins.PinCreate
import dev.pinlikest.ui.pins.PinDetails

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "TelaLogo") {

        composable("MainActivity") {
            MainActivity()
        }
        composable("TelaLogo") {
            TelaLogo(
                toHome = { navController.navigate("HomeScreen") }
            )
        }
        composable("HomeScreen") {
            HomeScreen(
                context = navController.context,


                onClickPinDetails = { pin ->
                    navController.navigate(
                        "PinDetails/${pin.image}/" +
                                "${pin.pinNome}/" +
                                "${pin.pinCriador}/" +
                                "${pin.pinTopComentario}"
                    )
                },

                toPinCreate = { navController.navigate("PinCreate") },
                toMessages = { navController.navigate("MessagesScreen") },
                toProfile = { navController.navigate("PerfilScreen") },
            )
        }

        composable("PinCreate") {
            var imageUri by remember { mutableStateOf<Uri?>(null) }

            val galleryLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.GetContent()
            ) { uri ->
                imageUri = uri
            }

            PinCreate(
                context = navController.context,
                
                toHome = { navController.popBackStack("HomeScreen", false) },
                toMessages = { navController.navigate("MessagesScreen") },
                toProfile = { navController.navigate("PerfilScreen") },

                onPickImage = {
                    galleryLauncher.launch("image/*")
                },
                imageUri = imageUri?.toString()
            )
        }
        composable("PerfilScreen") {
            PerfilScreen(
                context = navController.context,

                pinDetails = { pin ->
                    navController.navigate(
                        "PinDetails/${pin.image}/" +
                                "${pin.pinNome}/" +
                                "${pin.pinCriador}/" +
                                "${pin.pinTopComentario}"
                    )
                },

                toHome = { navController.popBackStack("HomeScreen", false) },
                toPinCreate = { navController.navigate("PinCreate") },
                toMessages = { navController.navigate("MessagesScreen") },
            )
        }
        composable("MessagesScreen") {
            MessagesScreen(
                context = navController.context,

                toHome = { navController.popBackStack("HomeScreen", false) },
                toPinCreate = { navController.navigate("PinCreate") },
                toMessageCreate = { navController.navigate("MessagesCreate") },
                toProfile = { navController.navigate("PerfilScreen") },
                onClickMessageDetails = { mensagem ->
                    navController.navigate("MensagemDetails/${mensagem.id}")
                }
            )
        }
        composable("MessagesCreate") {
            MessagesCreateScreen(
                context = navController.context,

                onBack = { navController.popBackStack() },
                toHome = { navController.popBackStack("HomeScreen", false) },
                toPinCreate = { navController.navigate("PinCreate") },
                toProfile = { navController.navigate("PerfilScreen") }
            )
        }
        composable(
            route = "MensagemDetails/{mensagemId}",
            arguments = listOf(
                navArgument("mensagemId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val mensagemId = backStackEntry.arguments?.getInt("mensagemId") ?: -1
            val context = LocalContext.current
            val db = AppDatabase.getDatabase(context)
            val mensagensDao = db.mensagensDAO()
            var mensagem by remember { mutableStateOf<Mensagem?>(null) }

            LaunchedEffect(mensagemId) {
                //mensagem = mensagensDao.buscarTodos().collect() { mensagem.id == mensagemId }
            }

                MensagemDetailsScreen(
                    context,
                    mensagem = mensagem,
                    onBack = { navController.popBackStack() },
                    toHome = { navController.popBackStack("HomeScreen", false) },
                    toPinCreate = { navController.navigate("PinCreate") },
                    toProfile = { navController.navigate("PerfilScreen") },
                )
        }
        composable(
            route = "PinDetails/{pinImg}/{pinNome}/{pinCriador}/{pinTopComentario}",
            arguments = listOf(
                navArgument("pinImg") { type = NavType.IntType },
                navArgument("pinNome") { type = NavType.StringType },
                navArgument("pinCriador") { type = NavType.StringType },
                navArgument("pinTopComentario") { type = NavType.StringType }
            )
        ) { backstackEntry ->
            val pinImg: Int = backstackEntry.arguments!!.getInt("pinImg")
            val pinNome = backstackEntry.arguments?.getString("pinNome") ?: ""
            val pinCriador = backstackEntry.arguments?.getString("pinCriador")
            val pinTopComentario = backstackEntry.arguments?.getString("pinTopComentario") ?: ""


            PinDetails(
                pinImg,
                pinNome,
                pinCriador,
                pinTopComentario
            ) { navController.popBackStack() }
        }
    }
}
