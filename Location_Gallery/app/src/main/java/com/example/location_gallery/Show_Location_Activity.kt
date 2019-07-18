package com.example.location_gallery

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_show__location_.*
class Show_Location_Activity : AppCompatActivity(), OnMapReadyCallback {


    private lateinit var mMap: GoogleMap

    var firebaseDatabase : FirebaseDatabase ?= null
    var firebaseStorage : FirebaseStorage ?= null
    var folderName =""
    var imgName =""

    var loc : LatLng ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show__location_)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map2) as SupportMapFragment
        mapFragment.getMapAsync(this)


        firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()


        var name = intent.getStringExtra("name")
        var lat = intent.getStringExtra("lat")
        var lon = intent.getStringExtra("lon")
        var comment = intent.getStringExtra("comm")
        var address = intent.getStringExtra("address")
        var url = intent.getStringExtra("url")
        var date = intent.getStringExtra("date")

        loc = LatLng(lat.toDouble(), lon.toDouble())
        imgName = intent.getStringExtra("imgName")
        folderName = intent.getStringExtra("folderName")
        imgName = intent.getStringExtra("imgName")


        nameView.text = name
        addressView.text = "Address: "+address
        commentView.text = comment+"\n ("+date+")"
        Picasso.get().load(url).into(imageView3)

    }

    override fun onMapReady(googleMap: GoogleMap?) {

        mMap = googleMap!!
        mMap!!.addMarker(MarkerOptions().position(loc!!).title(name))
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,13f))
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        var menuInflater = menuInflater
        menuInflater.inflate(R.menu.delete_location,menu)

        return super.onCreateOptionsMenu(menu)
    }



    fun deleteData(){
        var databaseReference = firebaseDatabase!!.getReference("Data/$folderName")
        var storageReference = firebaseStorage!!.getReference("images/$imgName.jpg")


        storageReference.delete().addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(applicationContext, "Location Deleted", Toast.LENGTH_LONG).show()
            }

        }.addOnFailureListener { exception ->
            Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
        }



        databaseReference.removeValue()

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == R.id.delete_loc) {
            deleteData()
            val intent = Intent(applicationContext,Locations_Activity :: class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }





}
