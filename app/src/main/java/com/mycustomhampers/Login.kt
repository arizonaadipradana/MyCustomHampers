package com.mycustomhampers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.e_store.R
import com.mycustomhampers.Services.Pop_Alert
import com.mycustomhampers.Services.SharedPreference
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentPage = "Login"
        setContentView(R.layout.login)

        val imageView:ImageView = findViewById(R.id.imageView)

        Glide.with(this)
            .load(R.drawable.logo)
            .into(imageView)

        auth = Firebase.auth

        val login_email = findViewById<EditText>(R.id.login_email)
        val login_password1 = findViewById<EditText>(R.id.login_password1)
        var login_register = findViewById<TextView>(R.id.login_register)
        var login_button = findViewById<TextView>(R.id.login_button)

        login_register.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        })

        login_button.setOnClickListener(View.OnClickListener {
            val email = login_email.text.toString()
            val password1 = login_password1.text.toString()

            var popAlert = Pop_Alert(this, this)

            if(email == "" || password1 == ""){
                popAlert.showAlert("Hufft!", "Harap isi data dengan benar", false, null)
            } else if (!Application().validateEmail(email)){
                popAlert.showAlert("Hufft!", "Email tidak valid", false, null)
            } else {
                auth.signInWithEmailAndPassword(email, password1)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "signInWithEmail:success")
                            val sp = SharedPreference(this)
                            sp.setPreference("isLoggedIn", "true")
                            val user_email = auth.currentUser?.email
                            sp.setPreference("user_email", user_email)
                            val intent = Intent(this, Products_Home::class.java)
                            popAlert.showAlert("Mantap!", "Login berhasil", true, intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            popAlert.showAlert("Hufft!", "Autentikasi gagal", false, null)
                        }
                    }
            }
        })
    }
}