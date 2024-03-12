package com.example.yandexmapbasicapp.ui

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.yandexmapbasicapp.data.ServicesData
import com.example.yandexmapbasicapp.model.Services
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition

class YandexMapsViewModel : ViewModel() {
    private val servicesData = ServicesData()
    var servicesToDisplay by mutableStateOf(listOf("a", "b", "c"))
    var currentCameraPosition by mutableStateOf(CameraPosition(Point(55.7522, 37.6156), 12.0f, 0.0f, 0.0f))
        private set
    fun takeServices(context: Context): Services {
        return servicesData.takeServices(context)
    }

    fun saveCameraPosition(position: CameraPosition) {
        currentCameraPosition = position
    }

    fun checkServiceToBeDisplayed(service: String) {
        servicesToDisplay = if (servicesToDisplay.contains(service)) {
            val updatedServicesMutableList: MutableList<String> = servicesToDisplay.toMutableList()
            updatedServicesMutableList.remove(service)
            updatedServicesMutableList.toList()
        } else {
            val updatedServicesMutableList: MutableList<String> = servicesToDisplay.toMutableList()
            updatedServicesMutableList.add(service)
            updatedServicesMutableList.toList()
        }
    }
}