package com.example.yandexmapbasicapp.ui

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.yandexmapbasicapp.data.ServicesData
import com.example.yandexmapbasicapp.model.Services

class YandexMapsViewModel : ViewModel() {
    private val servicesData = ServicesData()
    var servicesToDisplay by mutableStateOf(listOf("a", "b", "c"))
    fun takeServices(context: Context): Services {
        return servicesData.takeServices(context)
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