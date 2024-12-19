package uk.ac.tees.mad.aninfo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import uk.ac.tees.mad.aninfo.ui.WebViewScreen
import uk.ac.tees.mad.aninfo.ui.animedetail.AnimeDetailsScreen
import uk.ac.tees.mad.aninfo.ui.authentication.LoginScreen
import uk.ac.tees.mad.aninfo.ui.authentication.RegisterScreen
import uk.ac.tees.mad.aninfo.ui.home.HomeScreen
import uk.ac.tees.mad.aninfo.ui.profile.EditProfileScreen
import uk.ac.tees.mad.aninfo.ui.profile.ProfileScreen
import uk.ac.tees.mad.aninfo.ui.splash.SplashScreen
import uk.ac.tees.mad.aninfo.ui.watchlist.WatchlistScreen

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
            WatchlistScreen(navController = navController)
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
        composable(Screen.EditProfile.route) {
            EditProfileScreen(navController = navController)
        }

        composable(
            route = Screen.WebView.route,
            arguments = listOf(
                navArgument("url") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val urlString = backStackEntry.arguments?.getString("url")?.replace("%2F", "/")
            urlString?.let {
                WebViewScreen(navController = navController, urlString = it)
            } ?: navController.popBackStack()
        }
    }
}
