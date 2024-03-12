package com.example.yandexmapbasicapp.ui


import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.yandexmapbasicapp.R
import com.example.yandexmapbasicapp.model.Pin
import com.example.yandexmapbasicapp.ui.parts.YandexMapsTopAppBar
import com.example.yandexmapbasicapp.ui.theme.YandexMapBasicAppTheme
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider


enum class YandexMapScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    ServicesList(title = R.string.services_list)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YandexMapScreen(
    viewModel: YandexMapsViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = YandexMapScreen.valueOf(
        backStackEntry?.destination?.route ?: YandexMapScreen.Start.name
    )
    Scaffold(
        topBar = {
            YandexMapsTopAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val services = viewModel.takeServices(LocalContext.current)
        val listOfPins: List<Pin> =
            services.listOfPins.filter { viewModel.servicesToDisplay.contains(it.service) }
        NavHost(
            navController = navController,
            startDestination = YandexMapScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = YandexMapScreen.Start.name) {
                YandexMap(
                    listOfPins = listOfPins,
                    goToServicesListClick = { navController.navigate(YandexMapScreen.ServicesList.name) },
                    currentCameraPosition = viewModel.currentCameraPosition,
                    saveCameraPosition = { viewModel.saveCameraPosition(it) }
                )
            }
            composable(route = YandexMapScreen.ServicesList.name) {
                ServicesListScreen(
                    listOfServicesToDisplay = viewModel.servicesToDisplay,
                    onCheckedChange = { viewModel.checkServiceToBeDisplayed(it) }
                )
            }
        }
    }
}

@Composable
fun YandexMap(
    listOfPins: List<Pin>,
    goToServicesListClick: () -> Unit,
    currentCameraPosition: CameraPosition,
    saveCameraPosition: (CameraPosition) -> Unit
) {
    val context = LocalContext.current
    val placemarkTapListener = MapObjectTapListener { _, point ->
        Toast.makeText(
            context,
            "Tapped the point (${point.longitude}, ${point.latitude})",
            Toast.LENGTH_SHORT
        ).show()
        true
    }
    val imageProvider = ImageProvider.fromResource(context, R.drawable.placemark_icon)

    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = stringResource(R.string.choose_services),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { goToServicesListClick() }
        )
        AndroidView(
            factory = { context ->
                val view: MapView =
                    LayoutInflater.from(context).inflate(R.layout.yandex_map, null, false)
                        .findViewById(R.id.mapview)
                MapKitFactory.getInstance().onStart()
                view.onStart()
                view.map.move(currentCameraPosition)
                view
            },
            update = { view ->
                for (pin in listOfPins) {
                    val placemark = view.map.mapObjects.addPlacemark().apply {
                        geometry = Point(pin.coordinates.lat, pin.coordinates.lng)
                        setIcon(imageProvider)
                    }
                    placemark.addTapListener(placemarkTapListener)
                }
            },
            onRelease = { view ->
                val mapView: MapView = view.findViewById(R.id.mapview)
                saveCameraPosition(view.map.cameraPosition)
                mapView.onStop()
                MapKitFactory.getInstance().onStop()
            }
        )
    }
}

@Preview
@Composable
fun YandexMapPreview() {
    val cameraPosition = CameraPosition(Point(55.7522, 37.6156), 12.0f, 0.0f, 0.0f)
    YandexMapBasicAppTheme {
        YandexMap(
            listOfPins = listOf(),
            goToServicesListClick = {},
            currentCameraPosition = cameraPosition,
            saveCameraPosition = {}
        )
    }
}