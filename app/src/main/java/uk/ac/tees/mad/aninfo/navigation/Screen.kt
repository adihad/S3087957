package uk.ac.tees.mad.aninfo.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object AnimeDetails : Screen("anime_details")
    object Watchlist : Screen("watchlist")
    object Profile : Screen("profile")
    object EditProfile : Screen("edit_profile")
    object WebView : Screen("webview/{url}") {
        fun createRoute(url: String) = "webview/$url"
    }
}
