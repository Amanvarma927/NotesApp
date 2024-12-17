package com.a1developers.loginandsignupwithfirebase

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.a1developers.loginandsignupwithfirebase.databinding.ActivityAddnoteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.jvm.internal.FunctionReference

class Addnote : AppCompatActivity() {

    private val binding: ActivityAddnoteBinding by lazy {
        ActivityAddnoteBinding.inflate(layoutInflater)
    }

    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        //Initiaalize Database
        databaseReference = FirebaseDatabase.getInstance().reference
        auth= FirebaseAuth.getInstance()

         binding.save.setOnClickListener{
         val title = binding.edtTitle.text.toString()
         val desc = binding.edtDes.text.toString()


             if (title.isEmpty() && desc.isEmpty())
             {
                 Toast.makeText(this,"Please Fill All the details",Toast.LENGTH_LONG).show()
             }else
             {
                 val currentuser = auth.currentUser
                 currentuser?.let { user->
                     val notekey = databaseReference.child("users").child(user.uid).child("notes").push().key


                     //note item instance
                     val noteitem = Noteitem(title,desc,notekey?:"")
                     if (notekey != null){
                         //ADD NOTES TO THE user note
                         databaseReference.child("users").child(user.uid).child("notes").child(notekey).setValue(noteitem)

                             .addOnCompleteListener{ task ->
                                 if (task.isSuccessful){
                                     Toast.makeText(this,"Note Save Successful",Toast.LENGTH_LONG).show()
                                     finish()
                                 }else{
                                     Toast.makeText(this,"Failed To Save Note",Toast.LENGTH_LONG).show()

                                 }
                             }
                     }
                 }
             }
         }

    }
}