package com.example.coffeefinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MoreInfoActivity : AppCompatActivity() {

    lateinit var nameTitleTextView: TextView
    lateinit var descriptionTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_info)

        nameTitleTextView = findViewById(R.id.nameTitleTextView)
        descriptionTextView = findViewById(R.id.descriptionTextView)
        val name = intent.getStringExtra("name")
        val location = intent.getStringExtra("location")
        val comment = intent.getStringExtra("comment")

        nameTitleTextView.text = "$name"
        descriptionTextView.text = "$name is located in $location. Our users have commented: \"$comment\""

        val button = findViewById<Button>(R.id.button2)
        button.setOnClickListener {
            finish()
        }



    }
}