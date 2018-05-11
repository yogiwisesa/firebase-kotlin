package com.yogiw.firebase

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot



class MainActivity : AppCompatActivity() {

    lateinit var db: FirebaseDatabase
    lateinit var ref: DatabaseReference
    lateinit var mAuth: FirebaseAuth
    lateinit var adapter: NoteAdapter

    val notes: MutableList<NoteClass> =  mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance()
        ref = db.getReference("root").child(mAuth.uid)

        adapter = NoteAdapter(applicationContext, notes)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(applicationContext)


        shimmerContainer.startShimmerAnimation()
        load()
        swpRefresh.setOnRefreshListener {
            // Kalo Refresh ngapain
            shimmerContainer.startShimmerAnimation()
            notes.clear()
            adapter.notifyDataSetChanged()
            load()
        }

        Toast.makeText(applicationContext, mAuth.uid + "\n" + mAuth.currentUser?.email, Toast.LENGTH_LONG).show()
        btnLogout.setOnClickListener {
            mAuth.signOut()
            finish()
        }

        btnSimpan.setOnClickListener {
            val note = NoteClass(edtTitle.text.toString(), edtDescription.text.toString())

            ref.push().setValue(note)
        }
    }

    fun load(){

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                notes.clear()
                for (snapshot in dataSnapshot.children){
                    val value = snapshot.getValue(NoteClass::class.java)
                    if (value !=null){
                        notes.add(value)
                    }
                }
                adapter.notifyDataSetChanged()
                shimmerContainer.stopShimmerAnimation()
                shimmerContainer.visibility = View.GONE
                 swpRefresh.isRefreshing = false
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.i("Halooo", "Failed to read value.", error.toException())
            }
        })
    }
}
