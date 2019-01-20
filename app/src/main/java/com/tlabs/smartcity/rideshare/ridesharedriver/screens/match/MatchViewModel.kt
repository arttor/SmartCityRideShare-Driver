package com.tlabs.smartcity.rideshare.ridesharedriver.screens.match

import android.util.Log
import com.google.gson.Gson
import com.tlabs.smartcity.rideshare.ridesharedriver.api.SovrApi
import com.tlabs.smartcity.rideshare.ridesharedriver.data.AccAndCreProof
import com.tlabs.smartcity.rideshare.ridesharedriver.data.CreateConnOfferRespMsg
import com.tlabs.smartcity.rideshare.ridesharedriver.data.SovrinReq
import com.tlabs.smartcity.rideshare.ridesharedriver.util.ScopedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.lang.Exception

class MatchViewModel : ScopedViewModel() {
    var msg: String? = null
    suspend fun postInfo(msg: String, token: String):Boolean = withContext(Dispatchers.IO) {
        val gson = Gson()
        val req = gson.fromJson(msg, CreateConnOfferRespMsg::class.java)
        try {
            SovrApi.instance.acceptConnOffer(token, SovrinReq(req)).await()
        }catch (e:Exception
        ){
            Log.e("",e.message,e)
        }
        for (i in 1..120) {
            delay(1000)
            val resp = try {

             SovrApi.instance.getAllProofReq(token).await()
            }catch (e:Exception){

                Log.e("",e.message,e)
                null
            }
            if (resp != null && !resp.isEmpty()) {
                val r = resp[0]
                val sendResp = try {
                    SovrApi.instance.acceptProofReqAndSendProof(token, AccAndCreProof(r.id)).await()
                } catch (e:Exception){
                    Log.e("",e.message,e)
                    null
                }
                return@withContext true
            }
        }
        return@withContext false
    }

}

