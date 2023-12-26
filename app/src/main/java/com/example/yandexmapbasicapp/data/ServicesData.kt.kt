package com.example.yandexmapbasicapp.data

import android.content.Context
import com.example.yandexmapbasicapp.model.Services
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

class ServicesData {
    @OptIn(ExperimentalSerializationApi::class)
    fun takeServices(context: Context): Services {
        val json = Json { ignoreUnknownKeys = true }
        return json.decodeFromStream(context.assets.open("pins.json"))
    }
}