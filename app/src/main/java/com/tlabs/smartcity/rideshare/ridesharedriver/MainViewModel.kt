package com.tlabs.smartcity.rideshare.ridesharedriver

import com.tlabs.smartcity.rideshare.ridesharedriver.api.BackendApi
import com.tlabs.smartcity.rideshare.ridesharedriver.data.ParkReq
import com.tlabs.smartcity.rideshare.ridesharedriver.util.ScopedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

class MainViewModel : ScopedViewModel() {
    var token: String? = null

    var parkId: String = "lTqw1fqw1f6qwe15fwqe1f"

    var balance: Double = 0.0

    suspend fun getBal(): Double = with(Dispatchers.IO) {
        BackendApi.instance.getBalance("0xaff4a042646F6e32F897e6F3C2e310A781606fd5").await().balance / 10e15
    }

    suspend fun getBalDel(): Double = with(Dispatchers.IO) {
        kotlinx.coroutines.delay(1000)
        BackendApi.instance.getBalance("0xaff4a042646F6e32F897e6F3C2e310A781606fd5").await().balance / 10e15
    }

    suspend fun pay() = with(Dispatchers.IO) {
        BackendApi.instance.parkCar(
            ParkReq(
                parkId, "0xaff4a042646F6e32F897e6F3C2e310A781606fd5",
                "ff4d5f08009439ca3c6f2822e51c86aa556e59daca224f3c70afe249851502be"
            )
        ).await()
    }


}

