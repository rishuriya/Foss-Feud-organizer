package org.amfoss.findme.UI

import android.annotation.SuppressLint
import android.graphics.SurfaceTexture
import android.util.Log
import android.util.Size
import android.view.Surface
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.Preview
import androidx.compose.runtime.Composable
import androidx.camera.core.*
import androidx.camera.lifecycle.*
import androidx.camera.view.*
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.*
import kotlinx.coroutines.*
import org.amfoss.findme.Class.BarCodeAnalyser
import org.amfoss.findme.Navigation.AppNavigationItem
import org.amfoss.findme.application.App
import org.amfoss.findme.util.CacheService
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@SuppressLint("RememberReturnType")
@Composable
fun CameraPreview(game:String, navController: NavController){
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var preview by remember { mutableStateOf<Preview?>(null) }
    val barCodeVal = remember { mutableStateOf("") }
    val showDialog = remember { mutableStateOf(false) }
    if(showDialog.value)
    {
        val cache= App.context?.let { CacheService(it, "barCode") }
        cache?.saveData(barCodeVal.value)
        navController.clearBackStack(AppNavigationItem.Rounds.getRoute(game))
        showDialog.value=false
        navController.navigate(
            AppNavigationItem.Rounds.getRoute(game),
            navOptions = navController.currentDestination?.id?.let {
                NavOptions.Builder()
                    .setPopUpTo(it, inclusive = true)
                    .build()
            }
        )
    }

    AndroidView(
        factory = { AndroidViewContext ->
            PreviewView(AndroidViewContext).apply {
                this.scaleType = PreviewView.ScaleType.FILL_CENTER
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            }
        },
        modifier = Modifier
            .fillMaxSize(),
        update = { previewView ->
            val cameraSelector: CameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()
            val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
            val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
                ProcessCameraProvider.getInstance(context)

            cameraProviderFuture.addListener({
                preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
                val barcodeAnalyser = BarCodeAnalyser { barcodes ->
                    barcodes.forEach { barcode ->
                        Log.d("TAG", "CameraPreview: ${barcode.rawValue}")
                        barcode.rawValue?.let { barcodeValue ->
                            barCodeVal.value = barcodeValue
                            showDialog.value = true
                        }
                    }
                }
                val imageAnalysis: ImageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also {
                        it.setAnalyzer(cameraExecutor, barcodeAnalyser)
                    }

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalysis
                    )
                } catch (e: Exception) {
                    Log.d("TAG", "CameraPreview: ${e.localizedMessage}")
                }
            }, ContextCompat.getMainExecutor(context))
        }
    )
}




















