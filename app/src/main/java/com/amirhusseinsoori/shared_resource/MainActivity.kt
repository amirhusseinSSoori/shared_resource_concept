package com.amirhusseinsoori.shared_resource

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.hilt.navigation.compose.hiltViewModel
import com.amirhusseinsoori.shared_resource.ui.SharedResourceViewModel
import com.amirhusseinsoori.shared_resource.ui.navigation.InitialNavigation
import com.amirhusseinsoori.shared_resource.ui.theme.Shared_resourceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @OptIn(
            ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class, ExperimentalMaterialApi::class,
            ExperimentalMotionApi::class)
        setContent {
            val viewModel: SharedResourceViewModel = hiltViewModel()

            Shared_resourceTheme {
                InitialNavigation(
                    viewModel
                )

            }
        }
    }
}

