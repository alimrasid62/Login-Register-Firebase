package com.alimrasid.loginregister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var buttonLogin: Button
    private lateinit var auth : FirebaseAuth
    private lateinit var progresBar : ProgressBar
    private lateinit var textView : TextView

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        editTextEmail = findViewById(R.id.emailLogin)
        editTextPassword = findViewById(R.id.passwordLogin)
        buttonLogin = findViewById(R.id.btnLogin)
        progresBar = findViewById(R.id.progresBar)
        textView = findViewById(R.id.registNow)

        textView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val intent = Intent(applicationContext, RegistActivity::class.java)
                startActivity(intent)
                finish()
            }

        })

        buttonLogin.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                progresBar.visibility = View.VISIBLE
                var email: String
                var password: String
                email = editTextEmail.text.toString()
                password = editTextPassword.text.toString()

                if (email.isEmpty()){
                    Toast.makeText(this@LoginActivity,"Enter Email", Toast.LENGTH_SHORT).show()
                    return
                }
                if (password.isEmpty()){
                    Toast.makeText(this@LoginActivity,"Enter Email", Toast.LENGTH_SHORT).show()
                    return
                }

                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener() { task ->
                        progresBar.visibility = View.VISIBLE
                        if (task.isSuccessful) {
                            Toast.makeText(
                                baseContext,
                                "Login Berhasil :)",
                                Toast.LENGTH_SHORT,
                            ).show()
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                            finish()

                        } else {
                            Toast.makeText(
                                baseContext,
                                "Login failed :)",
                                Toast.LENGTH_SHORT,
                            ).show()

                        }
                    }
            }

        })
    }
}