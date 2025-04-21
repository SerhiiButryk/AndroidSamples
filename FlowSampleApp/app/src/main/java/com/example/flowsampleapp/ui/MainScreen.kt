package com.example.flowsampleapp.ui

import android.content.res.Configuration
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.flowsampleapp.ui.theme.MainAppTheme
import com.example.flowsampleapp.viewmodel.SampleViewModel

object Destinations {
    val ROUTE_MAIN = "main"
    val ROUTE_DETAIL = "detail"
    val ROUTE_ABOUT = "about"
    val ROUTE_APP_CONTENT = "content"
    val ROUTE_AUTH = "auth"
    val ROUTE_LOGIN = "login"
}

@Composable
fun MainAppScreen() {
    MainAppTheme {
        MainAppScreenUI()
    }
}

@Composable
private fun MainAppScreenUI(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Destinations.ROUTE_AUTH) {

        // Main app graph with app content
        navigation(
            startDestination = Destinations.ROUTE_MAIN,
            route = Destinations.ROUTE_APP_CONTENT
        ) {

            composable(Destinations.ROUTE_MAIN) { entry ->
                MainScreen(
                    modifier = modifier,
                    navigateToDetailsScreen = { navController.navigate(route = Destinations.ROUTE_DETAIL) },
                    navController = navController,
                    viewModel = entry.getViewModel<SampleViewModel>(navController)
                )
            }

            composable(Destinations.ROUTE_DETAIL) {
                DetailsScreen(modifier, navigateToMainScreen = navController::popBackStack)
            }
        }

        // Separate login app graph
        navigation(
            startDestination = Destinations.ROUTE_LOGIN,
            route = Destinations.ROUTE_AUTH
        ) {
            composable(Destinations.ROUTE_LOGIN) { entry ->

                val viewModel = entry.getViewModel<SampleViewModel>(navController)

                // Don't show login screen as this condition means that
                // User is logged in
                if (viewModel.hasUserData()) {

                    navController.navigate(Destinations.ROUTE_MAIN) {
                        launchSingleTop = true
                    }

                } else {

                    LoginScreen(
                        modifier = modifier
                    ) {
                        // Simulate that user is logged in successfully
                        viewModel.updateUserData()

                        // Leave this screen and remove this nav graph
                        navController.navigate(Destinations.ROUTE_APP_CONTENT) {
                            popUpTo(Destinations.ROUTE_AUTH) {
                                this.inclusive = true
                            }
                        }
                    }

                }
            }
        }

        // Some content which should be reachable from any screen or location
        composable(Destinations.ROUTE_ABOUT) {
            AboutScreen(modifier, navController::popBackStack)
        }
    }
}

@Composable
private fun MainScreen(
    modifier: Modifier = Modifier,
    navigateToDetailsScreen: () -> Unit = {},
    navController: NavController? = null,
    viewModel: SampleViewModel? = null
) {

    Log.i("COMPOSE", "MainScreen() IN")

    StartUI(
        modifier = modifier,
        text = "Main screen",
        buttonText = "Go to Details Screen",
        navigateTo = navigateToDetailsScreen
    )

    Log.i("COMPOSE", "MainScreen() OUT")

}

@Preview("Default", showSystemUi = true)
@Preview("Default", showSystemUi = true, device = "spec:parent=pixel_5,orientation=landscape")
@Preview("Dark mode", showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MainScreenUIPreview() {
    MainAppTheme {
        MainScreen()
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.getViewModel(navController: NavController): T {

    val parentNavGraph = destination.parent
    if (parentNavGraph == null)
        throw RuntimeException("Parent nav graph is null. Might be unexpected.")

    val route = parentNavGraph.route
    if (route == null) {
        return viewModel()
    }

    val parentEntry = remember(this) { navController.getBackStackEntry(route) }
    return viewModel(parentEntry)
}