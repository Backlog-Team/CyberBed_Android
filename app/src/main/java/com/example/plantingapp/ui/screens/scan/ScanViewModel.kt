package com.example.plantingapp.ui.screens.scan

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
import com.example.plantingapp.domain.Resource
import com.example.plantingapp.ui.states.LoadingStates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ScanViewModel(
    repository: PlantRepositoryInterface,
): ViewModel() {
    var lensFacing = CameraSelector.LENS_FACING_BACK
    private val useCase = CameraUseCase(repository)
    private var _plants = MutableStateFlow(emptyList<Plant>())
    var plants: StateFlow<List<Plant>> = _plants

    private val _loadingStates = MutableStateFlow(LoadingStates.Loading)
    val loadingState = _loadingStates.asStateFlow()

    fun recognizePhoto(image: Bitmap) {
        viewModelScope.launch {
            useCase.recognizeImage(image)
                .collect {
                    when (it) {
                        is Resource.Internet -> _loadingStates.value = LoadingStates.Error

                        is Resource.Loading -> _loadingStates.value = LoadingStates.Loading

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
                _plants.value = emptyList()
                val img: Bitmap = image.toBitmap()
                recognizePhoto(img)
                cameraExecutor.shutdown()
            }
        })
    }

    fun changeFacing() {
        lensFacing = if (lensFacing == CameraSelector.LENS_FACING_BACK) {
            CameraSelector.LENS_FACING_FRONT
        } else {
            CameraSelector.LENS_FACING_BACK
        }
    }
}