package com.example.coffeefinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {

    lateinit var welcomeTextView: TextView
    lateinit var emailEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        welcomeTextView = findViewById(R.id.welcomeTextView)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        auth = Firebase.auth
        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //Log.d("!!!", "sign in successful")
                    val text = getString(R.string.sign_in_success)
                    val duration = Toast.LENGTH_SHORT
                    Toast.makeText(this, text, duration).show()
                    val intent = Intent(this, CoffeePlaceActivity::class.java)
                    startActivity(intent)
                } else {
                    //Log.d("!!!", "sign in failed ${task.exception}")
                    val text = getString(R.string.error_message)
                    val duration = Toast.LENGTH_SHORT
                    Toast.makeText(this, text, duration).show()
                }
            }

    }
}