package com.amirhusseinsoori.shared_resource.ui.util
import androidx.compose.ui.graphics.*
import java.io.Serializable
data class SharedResourceItem(
    val color: Color,
    val id: String,
    val text:String
) : Serializable

sealed class InputData(var data:SharedResourceItem){
    object Atomic:InputData(SharedResourceItem(Color.Black,"atomic","Atomic"))
    object Semaphore:InputData(SharedResourceItem(Color.Gray,"semaphore","Semaphore"))
    object Mutex:InputData(SharedResourceItem(Color.Blue,"mutex","Mutex"))
    object Lock:InputData(SharedResourceItem(Color.Magenta,"lock","channel"))
    object Main:InputData(SharedResourceItem(Color.Transparent,"paintFab",""))
}