package org.d3if3131.assesmen2.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.d3if3131.assesmen2.ui.screen.AboutScreen
import org.d3if3131.assesmen2.ui.screen.AddTanamanScreen
import org.d3if3131.assesmen2.ui.screen.CekScreen
import org.d3if3131.assesmen2.ui.screen.DetailScreen
import org.d3if3131.assesmen2.ui.screen.MainScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.AddTanaman.route
    ) {
        composable (route = Screen.Main.route) {
            MainScreen(navController)
        }
        composable (route = Screen.About.route) {
            AboutScreen(navController)
        }
        composable(route = Screen.FormBaru.route) {
            DetailScreen(navController)
        }
        composable(route = Screen.Cek.route) {
            CekScreen(navController)
        }
        composable(route = Screen.AddTanaman.route) {
            AddTanamanScreen(navController)
        }
        composable(
            route = Screen.FormUbah.route,
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val id = arguments.getLong("id")
            DetailScreen(navController, id)
        }
    }
}