package com.a1developers.loginandsignupwithfirebase

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a1developers.loginandsignupwithfirebase.databinding.ActivityAllNotesBinding
import com.a1developers.loginandsignupwithfirebase.databinding.DialogUpdateNoteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.oAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.unity3d.services.banners.UnityBannerSize

class AllNotes : AppCompatActivity(), NoteAdapter.OnClickListenerItem {

    private val binding: ActivityAllNotesBinding by lazy {
        ActivityAllNotesBinding.inflate(layoutInflater)
    }

    private lateinit var databaseReference: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)


        //Initialize Firebase database reference

        recyclerView = binding.notesrecyclerview
        recyclerView.layoutManager = LinearLayoutManager(this)

        databaseReference = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()


        val currentuser = auth.currentUser
        currentuser.let { user ->
            val noteReference =
                databaseReference.child("users").child(user?.uid.toString()).child("notes")
            noteReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val notelist = mutableListOf<Noteitem>()
                    for (noteSnapshot in snapshot.children) {
                        val note = noteSnapshot.getValue(Noteitem::class.java)
                        note?.let {
                            notelist.add(it)
                        }
                    }
                    notelist.reverse()
                    val adapter = NoteAdapter(notelist, this@AllNotes)
                    recyclerView.adapter = adapter
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
        }


    }

    override fun ondeleteclcik(noteId: String) {


        val currentuser = auth.currentUser
        currentuser?.let { user ->
            val noteReference = databaseReference.child("users").child(user.uid).child("notes")
            noteReference.child(noteId).removeValue()
        }
    }

    override fun onupdateclcik(noteId: String, currentTitle: String, currentdes: String) {

        val dialogbindig = DialogUpdateNoteBinding.inflate(LayoutInflater.from((this)))
        val dialog = AlertDialog.Builder(this).setView(dialogbindig.root)
            .setTitle("Update Notes")
            .setPositiveButton("Update") { dialog, _ ->
                val newtitle = dialogbindig.updatetitle.text.toString()
                val newdes = dialogbindig.updatedes.text.toString()
                updateNoteDatabase(noteId, newtitle, newdes)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        dialogbindig.updatetitle.setText(currentTitle)
        dialogbindig.updatedes.setText(currentdes)

        dialog.show()

    }


    private fun updateNoteDatabase(noteId: String, newtitle: String, newdes: String) {

        val currentuser = auth.currentUser
        currentuser?.let { user ->
            val noteReference = databaseReference.child("users").child(user.uid).child("notes")
            val updatenote = Noteitem(newtitle, newdes, noteId)
            noteReference.child(noteId).setValue(updatenote)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Note Updated Successfully", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "Note Update Failed", Toast.LENGTH_LONG).show()
                    }
                }

        }


    }
}

