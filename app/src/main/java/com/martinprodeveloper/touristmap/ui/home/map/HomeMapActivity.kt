package com.martinprodeveloper.touristmap.ui.home.map

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import android.provider.Settings
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.martinprodeveloper.touristmap.R
import com.martinprodeveloper.touristmap.databinding.ActivityHomeMapBinding
import com.martinprodeveloper.touristmap.domain.model.TouristPlace

class HomeMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private val TAG = "HomeMapActivity"
    private lateinit var binding: ActivityHomeMapBinding
    var touristPlace: TouristPlace? = null
    val gson = Gson()

    private var googleMap: GoogleMap? = null
    private val PERMISSION_ID = 42

    private var markerAddress: Marker? = null
    private var myLocationLatLng: LatLng? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation = locationResult?.lastLocation
            myLocationLatLng = LatLng(lastLocation?.latitude ?: 0.0, lastLocation?.longitude ?: 0.0)
            addAddressMarker()
            drawRoute()
            Log.d("LOCALIZACIÓN", "Callback: $lastLocation")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        touristPlace = gson.fromJson(intent.getStringExtra("tourist_place"), TouristPlace::class.java)
        Log.d(TAG, "JSON recibido: ${touristPlace?.toJson()}")

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()

        setUpView()
        setUpObservers()
    }

    private fun setUpView() {
        //
    }

    private fun setUpObservers() {
        //
    }

    private fun getLastLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                fusedLocationClient?.lastLocation?.addOnCompleteListener { task ->
                    val location = task.result
                    if (location != null) {
                        myLocationLatLng = LatLng(location.latitude, location.longitude)
                        addAddressMarker()
                        drawRoute()
                        googleMap?.moveCamera(
                            CameraUpdateFactory.newCameraPosition(
                                CameraPosition.builder().target(
                                    LatLng(location.latitude, location.longitude)
                                ).zoom(15f).build()
                            )
                        )
                    }
                }
            } else {
                Toast.makeText(this, "Habilita el GPS", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermission()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    private fun addAddressMarker() {
        val addressLocation = LatLng(touristPlace?.location?.lat ?: 0.0, touristPlace?.location?.lng ?: 0.0)
        markerAddress = googleMap?.addMarker(
            MarkerOptions()
                .position(addressLocation)
                .title("Lugar turístico")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        )
        myLocationLatLng?.let { currentLocation ->
            googleMap?.addMarker(
                MarkerOptions()
                    .position(currentLocation)
                    .title("Ubicación actual")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.home))
            )
        }
    }

    private fun drawRoute() {
        val currentLocation = myLocationLatLng
        if (currentLocation != null && touristPlace != null) {
            val addressLocation = LatLng(touristPlace?.location?.lat ?: 0.0, touristPlace?.location?.lng ?: 0.0)

            googleMap?.addPolyline(
                com.google.android.gms.maps.model.PolylineOptions()
                    .add(currentLocation, addressLocation)
                    .color(Color.GREEN)
                    .width(12f)
            )
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap?.uiSettings?.isZoomControlsEnabled = true
    }

    override fun onDestroy() {
        fusedLocationClient?.removeLocationUpdates(locationCallback)
        super.onDestroy()
    }
}