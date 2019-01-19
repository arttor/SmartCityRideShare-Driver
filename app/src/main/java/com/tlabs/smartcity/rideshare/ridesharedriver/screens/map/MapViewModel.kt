package com.tlabs.smartcity.rideshare.ridesharedriver.screens.map

import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.mapboxsdk.geometry.LatLng
import com.tlabs.smartcity.rideshare.ridesharedriver.api.MapBoxApi
import com.tlabs.smartcity.rideshare.ridesharedriver.util.ScopedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MapViewModel : ScopedViewModel() {
    var start: LatLng? = null
    var finish: LatLng? = null

    suspend fun buildRoute(): DirectionsRoute = withContext(Dispatchers.IO) {
        MapBoxApi.buildRoute(start!!, finish!!).await()
    }

}

