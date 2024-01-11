package com.example.coffeefinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class CoffeePlaceActivity : AppCompatActivity() {

    lateinit var auth : FirebaseAuth
    lateinit var db: FirebaseFirestore

    //lista med coffee places
    var coffeePlaces = mutableListOf<CoffeePlace>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coffee_place)

        auth = Firebase.auth
        db = Firebase.firestore

        fetchData()

        val addItemButton = findViewById<Button>(R.id.addItemButton)
        addItemButton.setOnClickListener {
            val user = auth.currentUser
            if (user == null) {
                val text = getString(R.string.not_logged_in_error)
                val duration = Toast.LENGTH_LONG
                Toast.makeText(this, text, duration).show()
            } else {
                val intent = Intent(this, AddItemActivity::class.java)
                startActivity(intent)
            }
        }

        val goToMapButton = findViewById<Button>(R.id.goToMapButton)
        goToMapButton.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }



    }

    private fun fetchData() {
        var recyclerView = findViewById<RecyclerView>(R.id.coffeeRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = CoffeeRecycleAdapter(this, coffeePlaces)
        recyclerView.adapter = adapter

        val docRef = db.collection("coffeeplaces")
            .get()
            .addOnSuccessListener { result ->
                coffeePlaces.clear()
                for (document in result) {
                    Log.d("!!!", "${document.id} => ${document.data}")
                    val item = document.toObject<CoffeePlace>()
                    coffeePlaces.add(item)
                    adapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { exception ->
                Log.d("!!!", "Error getting documents: ", exception)
            }
    }

    override fun onResume() {
        super.onResume()
        fetchData()

    }


}