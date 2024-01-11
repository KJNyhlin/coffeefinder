package com.example.coffeefinder

import android.content.Context
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.coffeefinder.databinding.ActivityMapsBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    lateinit var db: FirebaseFirestore

    //lista med coffee places
    var coffeePlaces = mutableListOf<CoffeePlace>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val button = findViewById<Button>(R.id.backFromMapsButton)
        button.setOnClickListener {
            finish()
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        fetchData()

        // Add a marker in Sydney and move the camera
        //val sydney = LatLng(-34.0, 151.0)
        //mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    fun fetchData() {
        db = Firebase.firestore
        val docRef = db.collection("coffeeplaces")
            .get()
            .addOnSuccessListener { result ->
                coffeePlaces.clear()
                for (document in result) {
                    Log.d("!!!", "${document.id} => ${document.data}")
                    val item = document.toObject<CoffeePlace>()
                    coffeePlaces.add(item)
                }
                createMarkers()  // Flyttat hit!
            }
            .addOnFailureListener { exception ->
                Log.d("!!!", "Error getting documents: ", exception)
            }
    }
    fun createMarkers() {



        var lat: Double = 0.0
        var lng: Double = 0.0
        var count = 0
        for (item in coffeePlaces) {
            // TODO kolla att lat och lng från listan är Doubles, annars konvertera

            var latStr = coffeePlaces[count].latitude
            var lngStr = coffeePlaces[count].longitude
            if (latStr.isNotEmpty() && lngStr.isNotEmpty()) {
                lat = latStr.toDouble()
                lng = lngStr.toDouble()
            } else {
                val text = getString(R.string.missing_string_error)
                val duration = Toast.LENGTH_SHORT
                Toast.makeText(this, text, duration).show()
            }
            if (lat is Double && lng is Double) {
                var place = LatLng(lat, lng)
                mMap.addMarker(
                    MarkerOptions()
                        .position(place)
                        .title(coffeePlaces[count].name)
                        .snippet(coffeePlaces[count].comment)
                )
            } else {
                val text = getString(R.string.error_message)
                val duration = Toast.LENGTH_SHORT
                Toast.makeText(this, text, duration).show()
            }
            count++
        }
    }

    fun getLocationByAddress(context: Context, strAddress: String): LatLng? {
        val coder = Geocoder(context)
        try {
            val address = coder.getFromLocationName(strAddress, 5) ?: return null
            val location = address.first()
            return LatLng(location.latitude, location.longitude)
        } catch (e: Exception) {
            Log.d("!!!", "$e, getLocationByAddress")
        }
        return null
    }
}