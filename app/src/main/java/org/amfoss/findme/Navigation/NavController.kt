package org.amfoss.findme.Navigation

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.amfoss.findme.UI.CameraPreview
import org.amfoss.findme.UI.FindMeScreen
import org.amfoss.findme.UI.LoginPage
import org.amfoss.findme.UI.rounds

@Composable
fun AppNavigation(
    navController: NavController = rememberNavController(),
    activity: ComponentActivity
) {
    NavHost(
        navController = navController as NavHostController,
        modifier = Modifier.fillMaxSize(),
        startDestination = AppNavigationItem.Login.route
    ){
        composable(route = AppNavigationItem.Home.route,
        ){
            FindMeScreen(navController= navController)
        }
        composable(route = AppNavigationItem.Rounds.route,
            arguments = listOf(navArgument("name") {
                type= NavType.StringType
            })){it.destination

            Log.d("AppNavigation", "AppNavigation: ${it.arguments?.getString("name")}")
            rounds(navController= navController, page=it.arguments?.getString("name")?:"")
        }
        composable(route = AppNavigationItem.QRScanner.route,
            arguments= listOf(navArgument("name"){
                type= NavType.StringType
            })
        ){

            CameraPreview(game = it.arguments?.getString("name")?:"", navController = navController)
        }
        composable(route = AppNavigationItem.Login.route){
            LoginPage(navController = navController)
        }
    }
}

