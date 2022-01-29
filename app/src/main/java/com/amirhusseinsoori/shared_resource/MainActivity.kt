package com.amirhusseinsoori.shared_resource

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.amirhusseinsoori.shared_resource.ui.SharedResourceViewModel
import com.amirhusseinsoori.shared_resource.ui.theme.Shared_resourceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {git
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: SharedResourceViewModel = hiltViewModel()

            Shared_resourceTheme {


                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                   val data= viewModel.stateFlow.collectAsState()
                    data.let {
                        Log.e("TAG", "onCreate:  increment : ${ it.value.increment}   increment : ${ it.value.decrement}  ", )

                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Shared_resourceTheme {
        Greeting("Android")
    }
}