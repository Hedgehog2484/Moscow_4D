package com.android.moscow4D.fragments.map

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.android.moscow4D.R
import android.util.Log
import com.android.moscow4D.fragments.map.subUI.BottomSheetFragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

import com.google.android.gms.maps.model.PolylineOptions

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors

import com.google.android.gms.maps.model.Marker

import com.android.moscow4D.database.PlaceEntity
import PlacesViewModel
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory


class MapController(
    _activity: MapsFragment,
    _placesViewModel: PlacesViewModel,
    googleMap: GoogleMap,
    _fragmentManager: FragmentManager,
    _context: Context
) : GoogleMap.OnMarkerClickListener {
    /**
     * This class is made for more comfortable usage of map functions:
     *      -routing
     *      -marker management
     *      -map styles**/

    //some default numbers
    private lateinit var myLoc: LatLng

    private lateinit var locationManager: LocationManager

    private var originLatitude: Double = 28.5021359
    private var originLongitude: Double = 77.4054901
    private val originLocation = LatLng(originLatitude, originLongitude)
    private var destinationLatitude: Double = 28.65974527072595
    private var destinationLongitude: Double = 77.243185837775
    private val destinationLocation = LatLng(destinationLatitude, destinationLongitude)

    private val placesViewModel = _placesViewModel

    private var markerMap = mutableMapOf<Marker, PlaceEntity>()

    private val frManager = _fragmentManager
    public val context = _context
    private val activity = _activity
    private val bottomSheetFragment = BottomSheetFragment(this)

    private var mMap: GoogleMap = googleMap

    private val mapExecutor = Executors.newSingleThreadExecutor()
    private val mapHandler = Handler(Looper.getMainLooper())

    fun onMapReady(p0: GoogleMap?) {// map initialisation and properties setting
        val mMap = p0!!

        //styles
        val mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.dark_theme)

        mMap.setMapStyle(mapStyleOptions)

        //markers
        mMap.clear()
        mMap.setOnMarkerClickListener(this)

        initMarkers(mMap)
        mMap.addMarker(MarkerOptions().position(originLocation))
        //mMap.addMarker(MarkerOptions().position(LatLng(43.996428831830414,44.100213266771945)))
        mMap.addMarker(MarkerOptions().position(destinationLocation))

        Log.d("GoogleMap", "before URL")
        val url = getDirectionURL(originLocation,destinationLocation, context.getString(R.string.google_maps_key))
        Log.d("GoogleMap", "URL : $url")
        //val res = GetDirection(mMap, URL).execute()
        //buildRoute(url)
        Log.d("GoogleMap", "URL : $url")

        //val sydney = LatLng(-34.0, 151.0)
        //mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))

        mMap.moveCamera(CameraUpdateFactory.newLatLng(destinationLocation))
    }

    private fun initMarkers(map: GoogleMap) {
        val places = placesViewModel.allPlacesList

        for (place in places){
            val options = MarkerOptions().position(LatLng(place.place_lat!!.toDouble(), place.place_lng!!.toDouble()))
            val marker = map.addMarker(options)
            if (marker != null) {
                markerMap[marker] = place
            }
        }
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        //event that cold when marker has been clicked

        val placeData: PlaceEntity? = markerMap[p0]

        if(placeData != null) {
            val bundle = Bundle()
            bundle.putParcelable("place", placeData)

            bottomSheetFragment.arguments = bundle
            bottomSheetFragment.show(frManager, "Bottom sheet dialog!")
        }

        return false
    }

    public fun getDirectionURL(origin:LatLng, dest:LatLng, secret: String) : String{
        //fynction that returns full rout link for the path
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}" +
                "&destination=${dest.latitude},${dest.longitude}" +
                "&sensor=false" +
                "&mode=driving" +
                "&key=$secret"
    }

    fun getLastKnownLocation(): LatLng {
        locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(activity.activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(activity.activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 2)
        }
        val localGpsLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        if (localGpsLocation != null)
            myLoc = LatLng(localGpsLocation.latitude, localGpsLocation.longitude)

        return myLoc
    }

    public fun buildRouteFromMyLocation(destination: LatLng){
        val loc: LatLng = getLastKnownLocation()

        buildRoute(getDirectionURL(
                LatLng(loc.latitude,loc.longitude),
                LatLng(destination.latitude, destination.longitude),
                activity.getString(R.string.google_maps_key)))
    }

    public fun buildRoute(url: String){
        mapExecutor.execute {
            Log.d("GoogleMap", "URL : $url")
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body!!.string()

            val result =  ArrayList<List<LatLng>>()
            try{
                val respObj = Gson().fromJson(data, MapData::class.java)
                val path =  ArrayList<LatLng>()
                for (i in 0 until respObj.routes[0].legs[0].steps.size){
                    path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                }
                result.add(path)
            }catch (e:Exception){
                e.printStackTrace()
            }

            mapHandler.post {
                Log.d("GoogleMap", "after URL 1")
                val lineoption = PolylineOptions()
                for (i in result.indices) {
                    lineoption.addAll(result[i])
                    lineoption.width(10f)
                    lineoption.color(Color.RED)
                    lineoption.geodesic(true)
                }

                mMap.addPolyline(lineoption)
                Log.d("GoogleMap", "after URL 2")
            }
        }
    }

    private fun decodePolyline(encoded: String): List<LatLng> {
        //some magic with google rout line style
        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0
        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng
            val latLng = LatLng((lat.toDouble() / 1E5),(lng.toDouble() / 1E5))
            poly.add(latLng)
        }
        return poly
    }
}