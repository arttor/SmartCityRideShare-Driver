package com.tlabs.smartcity.rideshare.ridesharedriver.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.mapbox.android.core.location.LocationEngineListener
import com.mapbox.android.core.location.LocationEnginePriority
import com.mapbox.android.core.location.LocationEngineProvider
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.geojson.LineString
import com.mapbox.mapboxsdk.annotations.PolylineOptions
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.location.LocationComponent
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.tlabs.smartcity.rideshare.ridesharedriver.MainActivity
import com.tlabs.smartcity.rideshare.ridesharedriver.databinding.MapFrafmentBinding
import com.tlabs.smartcity.rideshare.ridesharedriver.util.ScopedFragment
import kotlinx.android.synthetic.main.map_frafment.*
import org.jetbrains.anko.longToast

class MapFragment : ScopedFragment() {

    private lateinit var mapBoxMap: MapboxMap

    private lateinit var binding: MapFrafmentBinding

    private var locationComponent: LocationComponent? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MapFrafmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.onCreate(savedInstanceState)
        binding.setLifecycleOwner(viewLifecycleOwner)


        binding.toolbarListener = View.OnClickListener {
            val act = requireActivity() as MainActivity
            act.drawerLayout.openDrawer(GravityCompat.START)
        }

        mapView.getMapAsync {
            mapBoxMap = it
            locationComponent = mapBoxMap.locationComponent
            enableUserLocation(it)
        }
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView?.onDestroy()
        locationComponent?.locationEngine?.removeLocationUpdates()
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            22 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    mapView.getMapAsync {
                        enableUserLocation(it)
                    }
                } else {
                    activity?.longToast("")
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }

    private fun drawNavigationPolylineRoute(route: DirectionsRoute) {
        // Check for and remove a previously-drawn navigation route polyline before drawing the new one
        mapView.getMapAsync { map ->

            if (map.polylines.size > 0) {
                map.removePolyline(map.polylines[0])
            }

            // Convert LineString coordinates into a LatLng[]
            val lineString = LineString.fromPolyline(route.geometry()!!, 6)
            val coordinates = lineString.coordinates()
            val polylineDirectionsPoints = arrayOfNulls<LatLng>(coordinates.size)
            for (i in coordinates.indices) {
                polylineDirectionsPoints[i] = LatLng(
                    coordinates[i].latitude(),
                    coordinates[i].longitude()
                )
            }

            // Draw the navigation route polyline on to the map
            map.addPolyline(
                PolylineOptions()
                    .add(*polylineDirectionsPoints)
                    //.color(navigationLineColor)
                    .width(NAVIGATION_LINE_WIDTH)
            )

            val latLngBounds = LatLngBounds.Builder()
                .includes(polylineDirectionsPoints.toMutableList())
                .build()


            map.easeCamera(
                CameraUpdateFactory.newLatLngBounds(latLngBounds, 50, 50, 50, 50),
                2000
            )
        }
    }

    private fun enableUserLocation(mapboxMap: MapboxMap) {
        val locationComponent = mapboxMap.locationComponent
        // Activate with options
        if (ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                22
            )
        } else {
            val task = LocationServices.getSettingsClient(requireContext()).checkLocationSettings(
                LocationSettingsRequest.Builder()
                    .addLocationRequest(
                        LocationRequest().apply {
                            interval = LOCATION_UPDATE_INTERVAL.toLong()
                            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                        })
                    .build()
            )

            task.addOnFailureListener {
                if (it is ResolvableApiException) {
                    try {
                        it.startResolutionForResult(this.activity, 0x1)
                    } catch (sendEx: IntentSender.SendIntentException) {
                        // ignore
                    }
                }
            }

            val engine = LocationEngineProvider(requireContext()).obtainBestLocationEngineAvailable()
            engine.priority = LocationEnginePriority.HIGH_ACCURACY
            // check position every 5 sec
            engine.interval = LOCATION_UPDATE_INTERVAL
            // engine
            locationComponent.activateLocationComponent(requireActivity(), engine)
            locationComponent.isLocationComponentEnabled = true
            locationComponent.locationEngine?.addLocationEngineListener(object : LocationEngineListener {
                override fun onLocationChanged(location: Location?) {
                    onReceiveFirstLocationUpdate(mapboxMap, location)
                    locationComponent.locationEngine?.removeLocationEngineListener(this)
                }

                @SuppressLint("MissingPermission")
                override fun onConnected() {
                    locationComponent.locationEngine?.requestLocationUpdates()
                }
            })
            locationComponent.locationEngine?.activate()
        }
    }

    private fun onReceiveFirstLocationUpdate(mapboxMap: MapboxMap, location: Location?) {
        location?.let {
            val latLng = LatLng(
                it.latitude,
                it.longitude
            )
            mapboxMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    latLng, CAMERA_DEFAULT_ZOOM
                ), object : MapboxMap.CancelableCallback {
                    override fun onFinish() {

                    }

                    override fun onCancel() {
                    }
                }
            )
            //searchVehicleViewModel.currentPosition = latLng
        }
    }

    companion object {
        private const val NAVIGATION_LINE_WIDTH = 9f
        private const val LOCATION_UPDATE_INTERVAL = 5000
        private const val CAMERA_DEFAULT_ZOOM = 11.0
        private const val CAMERA_EXPANDED_PADDING = 1400
        private const val CAMERA_COLLAPSED_PADDING = 700
        private const val MARKER_STATION_LABEL = "STATION"
        private const val MARKER_VEHICLE_LABEL = "VEHICLE"
    }
}
