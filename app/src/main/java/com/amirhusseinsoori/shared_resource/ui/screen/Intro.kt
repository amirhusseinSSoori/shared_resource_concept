package com.amirhusseinsoori.shared_resource.ui.screen

import android.annotation.SuppressLint
import android.graphics.Paint


import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import com.amirhusseinsoori.shared_resource.R
import com.amirhusseinsoori.shared_resource.ui.theme.Green50
import com.amirhusseinsoori.shared_resource.ui.theme.Red800
import com.amirhusseinsoori.shared_resource.ui.util.SharedResourceItem
import com.amirhusseinsoori.shared_resource.ui.util.InputData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@ExperimentalMotionApi
@Composable
fun MotionLayoutDemo() {
    Column(Modifier) {
        Spacer(modifier = Modifier.height(0.dp))
        ColorMotion()
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalMotionApi
@Composable
private fun ColorMotion() {
    var animateImage by rememberSaveable { mutableStateOf(false) }
    var colorSurface by remember { mutableStateOf(Color.White) }
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
            modifier = Modifier,
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
                    .fillMaxSize()

            ) {

                ItemAycn(InputData.Atomic.data) {
                    animateImage = !animateImage
                    colorSurface = Green50

                }
                ItemAycn(InputData.Semaphore.data) {

                }
                ItemAycn(InputData.Mutex.data) {

                }
                ItemAycn(InputData.Lock.data) {

                }
                FloatingActionButton(
                    onClick = {
                        animateImage = !animateImage

                    },
                    modifier = Modifier
                        .padding(2.dp)
                        .size(25.dp)
                        .layoutId("paintFab"),
                    backgroundColor = Red800,
                    contentColor = Color.White,
                ) {
                    Icon(painter = painterResource(R.drawable.ic_close), "empty_screen")
                }
            }
        }
    }
}

@Composable
fun ItemAycn(data: SharedResourceItem, setAnime: () -> Unit) {
    Canvas(modifier = Modifier
        .width(100.dp)
        .height(100.dp)
        .layoutId(data.id)

        .clickable {
            setAnime()
        }) {


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
                textSize = 50f
                color = Color.White.toArgb()
            })
    }


}

