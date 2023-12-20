package com.example.yandexmapbasicapp

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.mapview.MapView

class MainActivity : ComponentActivity() {
    private lateinit var mapView: MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.packageManager
            .getApplicationInfo(this.packageName, PackageManager.GET_META_DATA)
            .apply {
                MapKitFactory.setApiKey(metaData.getString("com.yandex.android.geo.API_KEY")!!)
            }
        MapKitFactory.initialize(this)
        setContentView(R.layout.yandex_map)
        mapView = findViewById(R.id.mapview)
        /*setContent {
            YandexMapBasicAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                }
            }
        }*/
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
}

@Composable
fun YandexMap() {

}