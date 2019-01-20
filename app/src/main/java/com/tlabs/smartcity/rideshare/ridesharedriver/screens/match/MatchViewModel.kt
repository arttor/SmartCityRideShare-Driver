package com.tlabs.smartcity.rideshare.ridesharedriver.screens.match

import com.google.gson.Gson
import com.tlabs.smartcity.rideshare.ridesharedriver.api.SovrApi
import com.tlabs.smartcity.rideshare.ridesharedriver.data.AccAndCreProof
import com.tlabs.smartcity.rideshare.ridesharedriver.data.CreateConnOfferRespMsg
import com.tlabs.smartcity.rideshare.ridesharedriver.util.ScopedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MatchViewModel : ScopedViewModel() {
    var msg: String? = null
    suspend fun postInfo(msg: String, token: String) = withContext(Dispatchers.IO) {
        val gson = Gson()
        val req = gson.fromJson(msg, CreateConnOfferRespMsg::class.java)
        SovrApi.instance.acceptConnOffer(token, req).await()
        for (i in 1..120) {
            delay(1000)
            val resp = SovrApi.instance.getAllProofReq(token).await()
            if (resp != null && !resp.isEmpty()) {
                val r = resp[0]
                val sendResp = SovrApi.instance.acceptProofReqAndSendProof(token, AccAndCreProof(r.id)).await()
                break
            }
        }
    }

}

