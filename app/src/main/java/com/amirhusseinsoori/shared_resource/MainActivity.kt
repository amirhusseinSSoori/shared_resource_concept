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
                   val data= viewModel._state.collectAsState()
                    data.let {

                        it.value.mutex.let {
                          Log.e("Tag", "onCreate:  block1 : ${ it.block1}   block2 : ${ it.block2}  block3 : ${ it.block3}  block4 : ${ it.block4}  ", )
                        }


                        it.value.semaphore.let {
                          // Log.e("Tag", "onCreate:  permit1 : ${ it.permit1}   permit2 : ${ it.permit2}  permit3 : ${ it.permit3}  permit4 : ${ it.permit4}  ", )
                        }


                        it.value.atomicState.let {
                           //Log.e("Tag", "onCreate:  increment : ${ it.increment}   decrement : ${ it.decrement} ", )
                        }



                    }


                }
            }
        }
    }
}

