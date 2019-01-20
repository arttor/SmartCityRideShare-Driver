package com.tlabs.smartcity.rideshare.ridesharedriver.screens.map

import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.mapboxsdk.geometry.LatLng
import com.tlabs.smartcity.rideshare.ridesharedriver.api.MapBoxApi
import com.tlabs.smartcity.rideshare.ridesharedriver.api.SovrApi
import com.tlabs.smartcity.rideshare.ridesharedriver.data.CreateConnOfferRespMsg
import com.tlabs.smartcity.rideshare.ridesharedriver.data.LoginReq
import com.tlabs.smartcity.rideshare.ridesharedriver.util.ScopedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MapViewModel : ScopedViewModel() {
    var start: LatLng? = null
    var finish: LatLng? = null
    lateinit var token: String

    suspend fun buildRoute(): DirectionsRoute = withContext(Dispatchers.IO) {
        MapBoxApi.buildRoute(start!!, finish!!).await()
    }

    suspend fun addRoute() = withContext(Dispatchers.IO) {

    }

    suspend fun login() = withContext(Dispatchers.IO) {
        val logResp = SovrApi.instance.login(LoginReq(password = "", username = "")).await()
        token = logResp.token
//        val crConnResp = SovrApi.instance.createConnOffer(token).await()
//        val accConnResp = SovrApi.instance.acceptConnOffer(token, crConnResp.message).await()
    }

}

