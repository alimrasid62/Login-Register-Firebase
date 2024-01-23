package com.alimrasid.loginregister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class RegistActivity : AppCompatActivity() {

    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var buttonReg: Button
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
        setContentView(R.layout.activity_regist)

        auth = FirebaseAuth.getInstance()

        editTextEmail = findViewById(R.id.email)
        editTextPassword = findViewById(R.id.password)
        buttonReg = findViewById(R.id.btnRegister)
        progresBar = findViewById(R.id.progresBar)
        textView = findViewById(R.id.loginNow)

        textView.setOnClickListener(object : OnClickListener{
            override fun onClick(view: View) {
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

        })

        buttonReg.setOnClickListener(object : OnClickListener{
            override fun onClick(v: View?) {
                progresBar.visibility = View.VISIBLE
                var email: String
                var password: String
                email = editTextEmail.text.toString()
                password = editTextPassword.text.toString()

                if (email.isEmpty()){
                    Toast.makeText(this@RegistActivity,"Enter Email", Toast.LENGTH_SHORT).show()
                    return
                }
                if (password.isEmpty()){
                    Toast.makeText(this@RegistActivity,"Enter Email", Toast.LENGTH_SHORT).show()
                    return
                }

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener() { task ->
                        progresBar.visibility = View.VISIBLE
                        if (task.isSuccessful) {
                            Toast.makeText(
                                baseContext,
                                "Account Created :)",
                                Toast.LENGTH_SHORT,
                            ).show()
                        } else {
                            Toast.makeText(
                                baseContext,
                                "Authentication failed :(",
                                Toast.LENGTH_SHORT,
                            ).show()
                        }
                    }
            }

        })
    }

}