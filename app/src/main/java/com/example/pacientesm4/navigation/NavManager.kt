package com.example.pacientesm4.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pacientesm4.viewsModels.ViewModel

import com.example.pacientesm4.views.HomeView
import com.example.pacientesm4.views.ImcView
import com.example.pacientesm4.views.OnboardingScreen

@Composable
fun NavManager(viewModel: ViewModel) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = if(viewModel.getFirstRunView()) "Onboarding" else "Home") {
        composable("Home") {
            viewModel.addFirstRun(false)
            HomeView(navController, viewModel)
        }
        composable(
            "imc/{id}", arguments = listOf(
                navArgument("id") { type = NavType.IntType })

        ) {
            val id = it.arguments!!.getInt("id")
            ImcView(navController, viewModel, id)
        }
         composable("Onboarding") {
             OnboardingScreen(navController)
         }
    }
}