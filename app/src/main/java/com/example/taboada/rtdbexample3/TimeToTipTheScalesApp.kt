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
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.makeitso.common.snackbar.SnackbarManager
import com.example.makeitso.theme.TimeToTipTheScalesTheme
import com.example.taboada.rtdbexample3.screens.chats.ChatScreen
import com.example.taboada.rtdbexample3.screens.conversation.ConversationScreen
import com.example.taboada.rtdbexample3.screens.createChat.CreateChatScreen
import com.example.taboada.rtdbexample3.screens.login.LoginScreen
import com.example.taboada.rtdbexample3.screens.settings.SettingsScreen
import com.example.taboada.rtdbexample3.screens.splash.SplashScreen
import com.example.taboada.rtdbexample3.screens.sign_up.SignUpScreen
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
/*
                NavHost(
                    navController = appState.navController,
                    startDestination = SPLASH_SCREEN,
                    modifier = Modifier.padding(innerPaddingModifier)
                ) { makeItGraph(appState) }
*/
               NavHost(
                   navController = appState.navController,
                   startDestination = CREATE_CHAT_SCREEN,
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
    composable(SPLASH_SCREEN) {
        SplashScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp)})
    }

    composable(LOGIN_SCREEN) {
        LoginScreen(openPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) } )
    }

    composable(SIGN_UP_SCREEN) {
        SignUpScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp)})
    }

    composable(CHAT_SCREEN) {
        ChatScreen(openScreen = { route -> appState.navigate(route) } )
    }

    composable(CREATE_CHAT_SCREEN) {
        CreateChatScreen(openScreen = { route -> appState.navigate(route) })
    }

    composable("$CONVERSATION_SCREEN/{chatID}",
        arguments = listOf(navArgument("chatID") { defaultValue = "root" })
    ) { backStackEntry ->
        ConversationScreen(
            chatID = backStackEntry.arguments?.getString("chatID")!!,
            openScreen = { route -> appState.navigate(route) }
        )
    }

    composable(SETTINGS_SCREEN) {
        SettingsScreen(
            restartApp = { route -> appState.clearAndNavigate(route)},
            openScreen = { route -> appState.navigate(route) }
        )
    }

}