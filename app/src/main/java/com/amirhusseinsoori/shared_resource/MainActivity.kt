package com.amirhusseinsoori.shared_resource

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.amirhusseinsoori.shared_resource.ui.SharedResourceViewModel
import com.amirhusseinsoori.shared_resource.ui.theme.Shared_resourceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: SharedResourceViewModel = hiltViewModel()

            Shared_resourceTheme {
                Surface(color = MaterialTheme.colors.background) {
                   val data= viewModel.stateAtomic.collectAsState()
                    data.let {
                   // Log.e("Tag", "onCreate:  increment : ${ it.value.increment}   increment : ${ it.value.decrement}  ", )

                    }


                    val data2= viewModel.stateSemaphore.collectAsState()
                    data2.let {
               //  Log.e("Tag", "onCreate:  permit1 : ${ it.value.permit1}   permit2 : ${ it.value.permit2}  permit3 : ${ it.value.permit3}   permit4 : ${ it.value.permit4}  ", )

                    }


                    val data3= viewModel.stateMutex.collectAsState()
                    data3.let {
                      Log.e("Tag", "onCreate:  block1 : ${ it.value.block1}   block2 : ${ it.value.block2}  block3 : ${ it.value.block3}   block4 : ${ it.value.block4}  ", )

                    }
                }
            }
        }
    }
}

