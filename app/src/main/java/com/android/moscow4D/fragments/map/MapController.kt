package com.android.moscow4D.fragments.map

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.AsyncTask
import androidx.fragment.app.FragmentManager
import com.android.moscow4D.R
import com.android.moscow4D.fragments.map.subUI.BottomSheetFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

class MapController(_fragmentManager: FragmentManager, _context: Context) : GoogleMap.OnMarkerClickListener{
    /**
     * This class is made for more comfortable usage of map functions:
     *      -routing
     *      -marker management
     *      -map styles**/

    private var originLatitude: Double = 28.5021359 // some default numbers
    private var originLongitude: Double = 77.4054901
    private var destinationLatitude: Double = 55.75252563689488
    private var destinationLongitude: Double = 37.6171499490738
    private val destinationLocation = LatLng(destinationLatitude, destinationLongitude)

    private val frManager = _fragmentManager
    private val context = _context
    private val bottomSheetFragment = BottomSheetFragment()

    fun onMapReady(p0: GoogleMap?) {// map initialisation and properties setting
        val mMap = p0!!

        //styles
        val mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.dark_theme)

        mMap.setMapStyle(mapStyleOptions)

        //markers
        mMap.clear()
        mMap.setOnMarkerClickListener(this)
        mMap.addMarker(MarkerOptions().position(destinationLocation))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(destinationLocation, 18F))

        val polyline1 = mMap.addPolyline(
            PolylineOptions()
                .clickable(false)
                .add(
                    LatLng(-35.016, 143.321),
                    LatLng(-34.747, 145.592),
                    LatLng(-34.364, 147.891),
                    LatLng(-33.501, 150.217),
                    LatLng(-32.306, 149.248),
                    LatLng(-32.491, 147.309)))

        // Position the map's camera near Alice Springs in the center of Australia,
        // and set the zoom factor so most of Australia shows on the screen.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(-23.684, 133.903), 4f))

        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        //event that cold when marker has been clicked
        bottomSheetFragment.show(frManager, "Bottom sheet dialog!")

        return false
    }

    private fun getDirectionURL(origin:LatLng, dest:LatLng, secret: String) : String{
        //fynction that returns full rout link for the path
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}" +
                "&destination=${dest.latitude},${dest.longitude}" +
                "&sensor=false" +
                "&mode=driving" +
                "&key=$secret"
    }

    @SuppressLint("StaticFieldLeak")
    private inner class GetDirection(val url : String) : AsyncTask<Void, Void, List<List<LatLng>>>(){
        //function that parses json that was returned from routing link
        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
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
            return result
        }

        fun onPostExecute(mMap: GoogleMap, result: List<List<LatLng>>) {
            val lineoption = PolylineOptions()
            for (i in result.indices){
                lineoption.addAll(result[i])
                lineoption.width(10f)
                lineoption.color(Color.GREEN)
                lineoption.geodesic(true)
            }
            mMap.addPolyline(lineoption)
        }
    }

    fun decodePolyline(encoded: String): List<LatLng> {
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