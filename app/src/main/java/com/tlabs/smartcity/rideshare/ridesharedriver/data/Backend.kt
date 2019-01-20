package com.tlabs.smartcity.rideshare.ridesharedriver.data

data class RegDevReq(val tokenId:String, val firebaseToken:String, val wallet:String)
data class RegDriverReq(val wallet:String, val rideData:RideData)
data class RideData(val from:Coordinates, val to:Coordinates)
data class Coordinates(val longitude:Double, val latitude:Double)