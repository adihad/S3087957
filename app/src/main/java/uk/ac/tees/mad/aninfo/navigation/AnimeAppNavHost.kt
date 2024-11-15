package uk.ac.tees.mad.aninfo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import uk.ac.tees.mad.aninfo.ui.splash.SplashScreen

@Composable
fun AnimeAppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.Login.route) {

        }
        composable(Screen.Home.route) {

        }
        composable(Screen.AnimeDetails.route) {

        }
        composable(Screen.Watchlist.route) {

        }
        composable(Screen.Profile.route) {

        }
        composable(Screen.EditProfile.route) {

        }
    }
}
