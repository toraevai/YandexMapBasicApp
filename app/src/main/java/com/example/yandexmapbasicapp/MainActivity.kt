package com.example.yandexmapbasicapp

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.example.yandexmapbasicapp.ui.theme.YandexMapBasicAppTheme
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.mapview.MapView

class MainActivity : ComponentActivity() {
    private lateinit var mapView: MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YandexMapBasicAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    YandexMap()
                }
            }
        }
    }
}

@Composable
fun YandexMap() {
    AndroidView(
        factory = { context ->
            context.packageManager
                .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
                .apply {
                    MapKitFactory.setApiKey(metaData.getString("com.yandex.android.geo.API_KEY")!!)
                }
            MapKitFactory.initialize(context)
            val view = LayoutInflater.from(context).inflate(R.layout.yandex_map, null, false)
            val mapView: MapView = view.findViewById(R.id.mapview)
            MapKitFactory.getInstance().onStart()
            mapView.onStart()
            view
        },
        onRelease = { view ->
            val mapView: MapView = view.findViewById(R.id.mapview)
            mapView.onStop()
            MapKitFactory.getInstance().onStop()
        }
    )
}