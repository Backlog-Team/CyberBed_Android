package com.example.plantingapp.ui.screens.camera

import android.graphics.Bitmap
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantingapp.data.repository.PlantRepositoryInterface
import com.example.plantingapp.domain.models.Plant
import com.example.plantingapp.domain.usecases.CameraUseCase
import com.example.plantingapp.domain.usecases.Resource
import com.example.plantingapp.ui.LoadingStates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraViewModel(
    repository: PlantRepositoryInterface,
): ViewModel() {
    var lensFacing = CameraSelector.LENS_FACING_BACK
    private val useCase = CameraUseCase(repository)
    private val _plants = MutableStateFlow(emptyList<Plant>())
    val plants: StateFlow<List<Plant>> = _plants
    private val _loadingStates = MutableStateFlow(LoadingStates.Loading)
    val loadingState = _loadingStates.asStateFlow()
    fun recognizePhoto(image: ImageProxy) {
        val img: Bitmap = image.toBitmap()
        viewModelScope.launch {
            useCase.recognizeImage(img)
                .collect {
                    when (it) {
                        is Resource.Internet -> _loadingStates.value = LoadingStates.Error

                        is Resource.Loading -> LoadingStates.Loading

                        is Resource.Success -> {
                            _plants.value = it.data ?: listOf()
                            _loadingStates.value = LoadingStates.Success
                        }

                        else -> _loadingStates.value = LoadingStates.Error
                    }
                }
        }
    }

    fun takePhoto(
        imageCapture: ImageCapture,
        cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
    ) {
        imageCapture.takePicture(cameraExecutor, object: ImageCapture.OnImageCapturedCallback() {
            override fun onError(exception: ImageCaptureException) {
                Log.e("kilo", "Take photo error:", exception)
                onError(exception)
            }

            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)
                recognizePhoto(image)
                cameraExecutor.shutdown()
            }
        })
    }
}