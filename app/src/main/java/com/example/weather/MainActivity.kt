package com.example.weather

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.example.weather.databinding.ActivityMainBinding
import com.google.android.gms.location.*


import dagger.hilt.android.AndroidEntryPoint
import java.time.format.DateTimeFormatter


@AndroidEntryPoint


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewmodel: ViewModel by viewModels()
  private  lateinit var mfusedlocation: FusedLocationProviderClient

    private val LOCATION_PERMISSION_REQUEST_CODE = 101


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


  
        mfusedlocation = LocationServices.getFusedLocationProviderClient(this)
        getlocation()

        viewmodel.data.observe(this, Observer { response ->
            if (response.isSuccessful) {
                binding.apply {

                    val temps = response.body()?.main?.temp_min?.minus(273.5)?.toInt().toString()
                    temp.text = temps + "°c"
                    humidity.text = response.body()?.main?.humidity.toString()+"%"
                    sunrise.text = response.body()?.sys?.sunriseformat+" A.M"
                    sunset.text = response.body()?.sys?.sunsetformat+" P.M"

                    pressure.text = response.body()?.main?.pressure.toString()
                    wind.text = response.body()?.wind?.speed.toString()+"km/hr"
                    address.text = response.body()?.name
                    status.text = response.body()?.weather?.get(0)?.description?.capitalize()

                }
            }

        })



        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {

                    viewmodel.getweather(query)

                    binding.searchView.clearFocus()

                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })


    }

    @SuppressLint("MissingPermission")
    private fun getlocation() {
        if (checkpermission()) {
            if (location_enabled()) {
                mfusedlocation.lastLocation.addOnCompleteListener { task ->

                    var location: Location?= task.result

                    if (location == null) {
                        newlocation()
                    }
                    else {
                        Log.d("latit", location.latitude.toString())
                        Log.d("long", location.longitude.toString())
                        viewmodel.getweatherusinglocation(location.latitude, location.longitude)
                    }
                }
            } else {
                Toast.makeText(this, "Turn on Location", Toast.LENGTH_LONG).show()
            }
        } else {
            request_permission()


        }


    }


    @SuppressLint("MissingPermission")
    private fun newlocation() {
      var locationrequest=LocationRequest()
        locationrequest.priority=LocationRequest.PRIORITY_HIGH_ACCURACY
        locationrequest.interval=0
        locationrequest.fastestInterval=0
        locationrequest.numUpdates=1
        mfusedlocation=LocationServices.getFusedLocationProviderClient(this)
        mfusedlocation.requestLocationUpdates(locationrequest,locationcallback, Looper.myLooper())
    }

    private val locationcallback=object :LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            var lastlocation:Location=p0.lastLocation


        }
    }

    private fun request_permission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    private fun location_enabled(): Boolean {


        var locationmanager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationmanager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationmanager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )


    }

    private fun checkpermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        } else {
            return false
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty()) {

            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getlocation()
            }
        }


    }

}