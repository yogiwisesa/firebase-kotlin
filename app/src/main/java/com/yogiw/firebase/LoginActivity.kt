package com.yogiw.firebase

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        if (mAuth.currentUser != null) startActivity(Intent(applicationContext, MainActivity::class.java))

        btnRegister.setOnClickListener {
            mAuth.createUserWithEmailAndPassword(edtEmail.text.toString(), edtPassword.text.toString())
                    .addOnCompleteListener(this){ task ->
                if (task.isSuccessful) startActivity(Intent(applicationContext, MainActivity::class.java))
                else Toast.makeText(applicationContext, "Register gagal: " + task.exception, Toast.LENGTH_LONG).show()
            }
        }

        btnLogin.setOnClickListener {
            mAuth.signInWithEmailAndPassword(edtEmail.text.toString(), edtPassword.text.toString())
                    .addOnCompleteListener(this){task ->
                if (task.isSuccessful) startActivity(Intent(applicationContext, MainActivity::class.java))
                else Toast.makeText(applicationContext, "Login gagal: " + task.exception, Toast.LENGTH_LONG).show()
            }
        }
    }
}
