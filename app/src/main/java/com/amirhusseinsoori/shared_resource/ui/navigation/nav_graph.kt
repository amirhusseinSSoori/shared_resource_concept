package com.amirhusseinsoori.shared_resource.ui.navigation

import androidx.compose.runtime.Composable
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amirhusseinsoori.shared_resource.ui.SharedResourceViewModel
import com.amirhusseinsoori.shared_resource.ui.screen.AtomicScreen
import com.amirhusseinsoori.shared_resource.ui.screen.MotionLayoutDemo


@ExperimentalMotionApi
@Composable
fun InitialNavigation(viewModel: SharedResourceViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "atomic") {
        composable("atomic") { MotionLayoutDemo() }


    }
}