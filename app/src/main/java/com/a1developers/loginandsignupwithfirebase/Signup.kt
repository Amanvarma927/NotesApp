package com.a1developers.loginandsignupwithfirebase

import android.content.Intent
import android.os.Bundle
import android.telephony.ims.ImsMmTelManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.a1developers.loginandsignupwithfirebase.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class Signup : AppCompatActivity() {

    private val binding: ActivitySignupBinding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        //initialize firebase

        auth = FirebaseAuth.getInstance()



binding.loginbutton.setOnClickListener{

    startActivity(Intent(this,LoginActivity::class.java))
    finish()
}
        binding.register.setOnClickListener{

            val name = binding.name.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password1.text.toString()
            val confirmpassword = binding.confirmpassword.text.toString()


            //check if field is empty

            if (email.isEmpty() || name.isEmpty() || password.isEmpty() || confirmpassword.isEmpty()){

                Toast.makeText(this,"Please Fill All The Details", Toast.LENGTH_LONG).show()
            }
            else if (password != confirmpassword){

                Toast.makeText(this,"Confirm Password is wrong",Toast.LENGTH_LONG).show()
            }
            else{

                auth.createUserWithEmailAndPassword(email,password)

                    .addOnCompleteListener(this){task ->
                        if (task.isSuccessful){
                            Toast.makeText(this,"Registered Succesfully",Toast.LENGTH_LONG).show()
                            startActivity(Intent(this,LoginActivity::class.java))
                            finish()
                        }
                        else{
                            Toast.makeText(this,"Registration Failed: ${task.exception?.message}",Toast.LENGTH_LONG).show()
                        }
                    }
            }

        }

        }
    }
