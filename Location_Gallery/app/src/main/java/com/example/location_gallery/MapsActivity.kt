package com.example.location_gallery

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var locationManager : LocationManager ?= null
    var locationListener : LocationListener ?= null
    var address =""
    var lat : Double ?= null
    var lon : Double ?= null
    var mAuth : FirebaseAuth ?= null
    var firebaseDatabase : FirebaseDatabase ?= null
    var myRef : DatabaseReference ?= null
    var myStorageRef : StorageReference ?= null
    var fusedLocationClient : FusedLocationProviderClient ?= null//OVO


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)//OVO


        mAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        myRef = firebaseDatabase!!.reference
        myStorageRef = FirebaseStorage.getInstance().reference


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapLongClickListener(myListener)
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager




        locationListener = object : LocationListener{

            override fun onLocationChanged(location: Location?) {
                if(location != null){
                    mMap.clear()
                    var userLoc = LatLng(location.latitude,location.longitude)

                    mMap.addMarker(MarkerOptions().position(userLoc).title("Your Location"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLoc,13f))
                  //  locationManager!!.removeUpdates(locationListener)
                }
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

            }

            override fun onProviderEnabled(provider: String?) {

            }

            override fun onProviderDisabled(provider: String?) {

            }

        }

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),2)

        }else{

            fusedLocationClient!!.lastLocation.addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val loc = task.result
                    val pos = LatLng(loc!!.latitude, loc!!.longitude)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos,15f))
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
            }

        }

    }




    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == 2 && grantResults.size > 0){
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                val loc = fusedLocationClient!!.lastLocation.addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        val loc = task.result
                        val pos = LatLng(loc!!.latitude, loc!!.longitude)
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos,15f))
                    }
                }.addOnFailureListener { exception ->
                    Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
                }


            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    val myListener = object : GoogleMap.OnMapLongClickListener{
        override fun onMapLongClick(p0: LatLng?) {
            mMap.clear()

            val position = LatLng(p0!!.latitude,p0!!.longitude)
            lat = p0!!.latitude
            lon = p0!!.longitude
            val geocoder = Geocoder(applicationContext, Locale.getDefault())
            val addressList = geocoder.getFromLocation(position.latitude,position.longitude,1)
            address = ""
            if(addressList.size > 0){
                if(addressList[0].thoroughfare != null)
                    address+=addressList[0].thoroughfare.toString()+" "

                if(addressList[0].subThoroughfare != null)
                    address+=addressList[0].subThoroughfare.toString()
            }
            if(address == "")
                    address ="No Address"

            mMap.addMarker(MarkerOptions().position(position).title(address))
        }

    }






    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.save_location,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == R.id.save_loc){
            if(lat != null && lon != null) {
                saveData()
            }else{
                Toast.makeText(applicationContext,"Long press to pick location",Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun saveData(){
        val uuid = UUID.randomUUID()
        val imageName = "images/$uuid.jpg"

        val storageReference = myStorageRef!!.child(imageName)

        storageReference.putFile(image!!).addOnSuccessListener (this){
            val newRef = FirebaseStorage.getInstance().getReference(imageName)

            newRef.downloadUrl.addOnSuccessListener { uri ->
                val downloadUrl = uri.toString()
                val user = mAuth!!.currentUser
                val emailF = user!!.email
                val commF = com
                val nameF = name


                val uuid2 = UUID.randomUUID()
                val uuidString = uuid2.toString()


                 myRef!!.child("Data").child(uuidString).child("name").setValue(nameF)
                myRef!!.child("Data").child(uuidString).child("date").setValue(dateString)
                myRef!!.child("Data").child(uuidString).child("imageName").setValue(uuid.toString())
                myRef!!.child("Data").child(uuidString).child("folderName").setValue(uuidString)
                 myRef!!.child("Data").child(uuidString).child("email").setValue(emailF)
                 myRef!!.child("Data").child(uuidString).child("comment").setValue(commF)
                 myRef!!.child("Data").child(uuidString).child("address").setValue(address)
                 myRef!!.child("Data").child(uuidString).child("LAT").setValue(lat.toString())
                 myRef!!.child("Data").child(uuidString).child("LON").setValue(lon.toString())
                 myRef!!.child("Data").child(uuidString).child("URL").setValue(downloadUrl)


            }


        }.addOnFailureListener(){exception ->
            Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
        }.addOnCompleteListener(){task ->
            if(task.isSuccessful) {
                Toast.makeText(applicationContext, "Location Saved", Toast.LENGTH_LONG).show()
                val intent = Intent(applicationContext,Locations_Activity :: class.java)
                startActivity(intent)
            }
        }
    }
}
