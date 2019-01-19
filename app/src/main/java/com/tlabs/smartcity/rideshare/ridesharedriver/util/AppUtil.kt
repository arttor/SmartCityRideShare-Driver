package com.tlabs.smartcity.rideshare.ridesharedriver.util

import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.geometry.LatLng


fun LatLng.toPoint(): Point = Point.fromLngLat(this.longitude, this.latitude)