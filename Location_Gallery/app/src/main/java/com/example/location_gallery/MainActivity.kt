package com.example.location_gallery

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    var mAuth : FirebaseAuth ?= null
    var mAuthListener : FirebaseAuth.AuthStateListener ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        mAuthListener = FirebaseAuth.AuthStateListener {  }
    }

    fun SingUp(view : View){
        mAuth!!.createUserWithEmailAndPassword(emailText.text.toString(),passText.text.toString()).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(applicationContext,"User Created",Toast.LENGTH_LONG).show()
                val intent = Intent(applicationContext,Locations_Activity :: class.java)
                startActivity(intent)
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
        }
    }

    fun SingIn(view: View){
        mAuth!!.signInWithEmailAndPassword(emailText.text.toString(),passText.text.toString()).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(applicationContext,"Sucessful",Toast.LENGTH_LONG).show()
                val intent = Intent(applicationContext,Locations_Activity :: class.java)
                startActivity(intent)
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
        }
    }



}
