package com.example.coffeefinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddItemActivity : AppCompatActivity() {

    lateinit var textView: TextView
    lateinit var nameEditText: EditText
    lateinit var cityCountryEditText: EditText
    lateinit var addressEditText: EditText
    lateinit var latEditText: EditText
    lateinit var lngEditText: EditText
    lateinit var commentEditText: EditText
    lateinit var auth : FirebaseAuth
    val db = Firebase.firestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        textView = findViewById(R.id.addWelcomeTextView)
        nameEditText = findViewById(R.id.nameEditText)
        cityCountryEditText = findViewById(R.id.locationEditText)
        addressEditText = findViewById(R.id.addressEditText)
        latEditText = findViewById(R.id.latEditText)
        lngEditText = findViewById(R.id.lngEditText)
        commentEditText = findViewById(R.id.commentEditText)
        auth = Firebase.auth

        val addButton = findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener {
            saveItem()
        }

        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun saveItem() {
        if (nameEditText.text.toString() == "" || cityCountryEditText.text.toString() == "" ||
            addressEditText.text.toString() == "" || latEditText.text.isNullOrEmpty() ||
            lngEditText.text.isNullOrEmpty()) {
            val text = getString(R.string.fields_not_filled_error)
            val duration = Toast.LENGTH_SHORT
            Toast.makeText(this, text, duration).show()
            return
        }
        val name = nameEditText.text.toString()
        nameEditText.setText("")
        val location = cityCountryEditText.text.toString()
        cityCountryEditText.setText("")
        val address = addressEditText.text.toString()
        addressEditText.setText("")
        val lat = latEditText.text.toString()
        latEditText.setText("")
        val lng = lngEditText.text.toString()
        lngEditText.setText("")
        val comment = commentEditText.text.toString()
        commentEditText.setText("")

        val item = hashMapOf(
            "name" to name,
            "location" to location,
            "address" to address,
            "latitude" to lat,
            "longitude" to lng,
            "comment" to comment
        )

        db.collection("coffeeplaces").document(name)
            .set(item)
            .addOnSuccessListener { Log.d("!!!", "DocumentSnapshot successfully written!")
                val text = getString(R.string.add_success)
                val duration = Toast.LENGTH_SHORT
                Toast.makeText(this, text, duration).show()}
            .addOnFailureListener { e -> Log.w("!!!", "Error writing document", e) }

    }
}