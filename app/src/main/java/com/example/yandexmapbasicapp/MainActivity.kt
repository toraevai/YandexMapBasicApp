package com.example.yandexmapbasicapp

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.yandexmapbasicapp.ui.YandexMapScreen
import com.example.yandexmapbasicapp.ui.theme.YandexMapBasicAppTheme
import com.yandex.mapkit.MapKitFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.packageManager
            .getApplicationInfo(this.packageName, PackageManager.GET_META_DATA)
            .apply {
                MapKitFactory.setApiKey(metaData.getString("com.yandex.android.geo.API_KEY")!!)
            }
        MapKitFactory.initialize(this)
        setContent {
            YandexMapBasicAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    YandexMapScreen()
                }
            }
        }
    }
}