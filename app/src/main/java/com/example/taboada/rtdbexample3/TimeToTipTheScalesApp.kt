package com.example.taboada.rtdbexample3

import android.content.res.Resources
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.makeitso.common.snackbar.SnackbarManager
import com.example.makeitso.theme.TimeToTipTheScalesTheme
import com.example.taboada.rtdbexample3.screens.chats.ChatScreen
import kotlinx.coroutines.CoroutineScope

@Composable
@ExperimentalMaterialApi
fun TimeToTipTheScalesApp() {
    TimeToTipTheScalesTheme() {
        Surface(color = MaterialTheme.colors.background) {
            val appState = rememberAppState()
           Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = it,
                        modifier = Modifier.padding(8.dp),
                        snackbar = { snackbarData ->
                            Snackbar(snackbarData, contentColor = MaterialTheme.colors.onPrimary)
                        }
                    )
                },
                scaffoldState = appState.scaffoldState
            ) { innerPaddingModifier ->
                NavHost(
                    navController = appState.navController,
                    startDestination = "CHATS",
                    modifier = Modifier.padding(innerPaddingModifier)
                ) { makeItGraph(appState) }
            }
        }
    }
}

@Composable
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(scaffoldState,navController, snackbarManager, resources, coroutineScope) {
    TimeToTipTheScalesAppState(scaffoldState,
        navController as NavHostController, snackbarManager, resources, coroutineScope)
}

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

@ExperimentalMaterialApi
fun NavGraphBuilder.makeItGraph(appState: TimeToTipTheScalesAppState) {
    composable("CHATS") {
        ChatScreen()
    }
}