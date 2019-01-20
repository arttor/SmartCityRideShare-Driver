package com.tlabs.smartcity.rideshare.ridesharedriver.screens.map

import com.google.firebase.iid.FirebaseInstanceId
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.mapboxsdk.geometry.LatLng
import com.tlabs.smartcity.rideshare.ridesharedriver.api.BackendApi
import com.tlabs.smartcity.rideshare.ridesharedriver.api.MapBoxApi
import com.tlabs.smartcity.rideshare.ridesharedriver.api.SovrApi
import com.tlabs.smartcity.rideshare.ridesharedriver.data.*
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
        BackendApi.instance.registerDriver(RegDriverReq(wallet = "0xaff4a042646F6e32F897e6F3C2e310A781606fd5",
            rideData = RideData(
                from = Coordinates(longitude = start!!.longitude,
                    latitude = start!!.latitude),
                to = Coordinates(longitude = finish!!.longitude,
                    latitude = finish!!.latitude)
            )
        )).await()
    }

    suspend fun login() = withContext(Dispatchers.IO) {

        val logResp = SovrApi.instance.login(LoginReq(password = "test123test", username = "jesse")).await()
        token = logResp.token
        val regResp =BackendApi.instance.registerDevice(
            RegDevReq(
                tokenId = token,
                firebaseToken = FirebaseInstanceId.getInstance().token?: "",
                wallet = "0xaff4a042646F6e32F897e6F3C2e310A781606fd5"
            )
        ).await()
    }

}

