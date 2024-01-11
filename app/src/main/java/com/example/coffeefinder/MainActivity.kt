package com.example.coffeefinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var titleTextView : TextView
    lateinit var appDescriptionTextView : TextView
    lateinit var signUpQuestion : TextView
    lateinit var signUpPrompt : TextView
    lateinit var continueAsGuestTextView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        titleTextView = findViewById(R.id.titleTextView)
        appDescriptionTextView = findViewById(R.id.appDescriptionTextView)
        val signInButton = findViewById<Button>(R.id.signInButton)
        signUpQuestion = findViewById(R.id.signUpQuestion)
        signUpPrompt = findViewById(R.id.signUpPrompt)
        continueAsGuestTextView = findViewById(R.id.continueAsGuestTextView)

        signInButton.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        signUpPrompt.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        continueAsGuestTextView.setOnClickListener {
            //TODO add prompt "are you sure"
            val intent = Intent(this, CoffeePlaceActivity::class.java)
            startActivity(intent)
        }

    }
}