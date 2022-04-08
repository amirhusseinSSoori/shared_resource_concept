package com.amirhusseinsoori.shared_resource.ui.screen

import android.annotation.SuppressLint
import android.graphics.Paint


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.navigation.NavHostController
import com.amirhusseinsoori.shared_resource.ui.util.SharedResourceItem
import com.amirhusseinsoori.shared_resource.ui.util.InputData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@ExperimentalMotionApi
@Composable
fun IntroScreen(nav: NavHostController) {
    Column(Modifier) {
        Spacer(modifier = Modifier.height(0.dp))
        initailMotion()
        LaunchedEffect(Unit) {
            delay(4000L)
            nav.navigate("atomic") {
                popUpTo("intro") { inclusive = true }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalMotionApi
@Composable
private fun initailMotion() {
    var animateImage by rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val imageAnimationProgress by animateFloatAsState(
        targetValue = if (animateImage) 1f else 0f,
        animationSpec = tween(1750)
    )
    coroutineScope.launch {
        delay(2000)
        animateImage = true
    }

    Surface(color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            MotionLayout(
                ConstraintSet(
                    """ {
                atomic: { 
                  width: 70,
                  height: 70,
                  bottom: ['parent', 'top', 1000],
                  start: ['parent', 'start', 200]
                },
                semaphore: { 
                  width: 50,
                  height: 50,
                  end: ['parent', 'start', 200],
                  bottom: ['parent', 'top', 0],
                },
                mutex: { 
                  width: 50,
                  height: 50,
                  top: ['parent', 'top', 1000],
                  start: ['parent', 'start', 200]
                },
                lock: { 
                  width: 50,
                  height: 50,
                  start: ['parent', 'end', 200],
                  bottom: ['parent', 'top', 0]
                },
                paintFab: {
                width: 50,
                height: 50,
               top: ['parent', 'top'],
               start: ['parent','start'],
               end: ['parent','end'],
               bottom: ['parent','bottom']
                    }
            } """
                ),
                ConstraintSet(
                    """ {
                atomic: { 
                  width: 50,
                  height: 50,
                   bottom: ['paintFab', 'top', 40],
                  start: ['paintFab', 'start', 0],
                },
                semaphore: { 
                  width: 50,
                  height: 50,
                  end: ['paintFab', 'start', 40],
                  top: ['paintFab', 'top', 0]
                },
                mutex: { 
                  width: 50,
                  height: 50,
                 top: ['paintFab', 'bottom', 40],
                  start: ['paintFab', 'start', 0]
                },
                lock: { 
                  width: 50,
                  height: 50,
                  start: ['paintFab', 'end', 40],
                  bottom: ['paintFab', 'bottom', 0]
                },
                paintFab: {
                width: 50,
                height: 50,
               top: ['parent', 'top'],
               start: ['parent','start'],
               end: ['parent','end'],
               bottom: ['parent','bottom']
                }
            } """
                ),
                progress = imageAnimationProgress,
                modifier = Modifier
            ) {
                ItemAycn(InputData.Atomic.data)
                ItemAycn(InputData.Semaphore.data)
                ItemAycn(InputData.Mutex.data)
                ItemAycn(InputData.Lock.data)
                ItemAycn(InputData.Main.data, 30)
            }
            Text(text = "shared resource", modifier = Modifier.padding(top = 50.dp))

        }
    }
}

@Composable
fun ItemAycn(data: SharedResourceItem, s: Int = 100) {
    Canvas(
        modifier = Modifier
            .width(s.dp)
            .height(s.dp)
            .layoutId(data.id)
    ) {
        drawCircle(
            color = data.color,
            radius = size.minDimension / 1f
        )
        drawContext.canvas.nativeCanvas.drawText(
            data
                .text,
            size.width / 2f,
            size.height / 1.7f,
            Paint().apply {
                textAlign = Paint.Align.CENTER
                textSize = 35f
                color = Color.White.toArgb()
            })
    }


}

