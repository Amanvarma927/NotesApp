package com.a1developers.loginandsignupwithfirebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.a1developers.loginandsignupwithfirebase.databinding.ActivityLoginBinding
import com.a1developers.loginandsignupwithfirebase.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth

    override fun onStart() {
        super.onStart()

        val currentuser: FirebaseUser? = auth.currentUser
        if (currentuser != null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        //initialize Firebase

        auth = FirebaseAuth.getInstance()

        //user previously logged in



        binding.loginbutton.setOnClickListener{
            val email= binding.email.text.toString()
            val password= binding.password.text.toString()


            if (email.isEmpty() || password.isEmpty()){

                Toast.makeText(this,"Please Enter All the Details",Toast.LENGTH_LONG).show()
            }else{
                auth.signInWithEmailAndPassword(email,password)

                    .addOnCompleteListener{task ->
                        if (task.isSuccessful){
                            Toast.makeText(this,"Logged In Successfully",Toast.LENGTH_LONG).show()
                            startActivity(Intent(this,MainActivity::class.java))
                            finish()
                        }
                        else {Toast.makeText(this,"Login Failed: ${task.exception?.message}",Toast.LENGTH_LONG).show()
                    }
            }
        }

        binding.signupbutton.setOnClickListener{
            startActivity(Intent(this,Signup::class.java))
        }

        }}
    }
