package uk.ac.tees.mad.aninfo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import uk.ac.tees.mad.aninfo.ui.animedetail.AnimeDetailsScreen
import uk.ac.tees.mad.aninfo.ui.authentication.LoginScreen
import uk.ac.tees.mad.aninfo.ui.authentication.RegisterScreen
import uk.ac.tees.mad.aninfo.ui.home.HomeScreen
import uk.ac.tees.mad.aninfo.ui.splash.SplashScreen

@Composable
fun AnimeAppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController = navController)
        }

        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(
            route = "${Screen.AnimeDetails.route}/{animeId}",
            arguments = listOf(navArgument("animeId") { type = NavType.IntType })
        ) {
            AnimeDetailsScreen(navController = navController)
        }

        composable(Screen.Watchlist.route) {

        }
        composable(Screen.Profile.route) {

        }
        composable(Screen.EditProfile.route) {

        }
    }
}
