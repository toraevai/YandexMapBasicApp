package com.example.yandexmapbasicapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Services (
    @SerialName(value = "services")
    val listOfServices: List<String>,
    @SerialName(value = "pins")
    val listOfPins: List<Pin>
)

@Serializable
data class Pin(
    val id: Int,
    val service: String,
    val coordinates: Coordinates
)

@Serializable
data class Coordinates(
    val lat: Double,
    val lng: Double
)