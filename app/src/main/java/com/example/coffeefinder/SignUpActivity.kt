package com.example.coffeefinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    lateinit var auth : FirebaseAuth
    lateinit var emailEditText: EditText
    lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        auth = Firebase.auth
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        val submitButton = findViewById<Button>(R.id.button)

        submitButton.setOnClickListener {
                    signUp()
        }

    }


    private fun signUp() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val text = getString(R.string.sign_up_success)
                    val duration = Toast.LENGTH_SHORT
                    Toast.makeText(this, text, duration).show()
                    val intent = Intent(this, CoffeePlaceActivity::class.java)
                    startActivity(intent)
                } else {
                    val text = getString(R.string.error_message)
                    val duration = Toast.LENGTH_SHORT
                    Toast.makeText(this, text, duration).show()
                }
            }
    }
}