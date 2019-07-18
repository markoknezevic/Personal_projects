package com.example.location_gallery

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_locations_.*
import java.util.*
import kotlin.collections.ArrayList

class Locations_Activity : AppCompatActivity() {

    val name: ArrayList<String> = ArrayList<String>()
    val dateString : ArrayList<String> = ArrayList<String>()
    val email: ArrayList<String> = ArrayList<String>()
    val comment: ArrayList<String> = ArrayList<String>()
    val address: ArrayList<String> = ArrayList<String>()
    val lat: ArrayList<String> = ArrayList<String>()
    val lon: ArrayList<String> = ArrayList<String>()
    val url: ArrayList<String> = ArrayList<String>()
    val imgName: ArrayList<String> = ArrayList<String>()
    val folderName: ArrayList<String> = ArrayList<String>()
    var mAuth : FirebaseAuth ?= null
    var firabaseDatabase : FirebaseDatabase ?= null
    var adapter : LocationClass ?= null
    var pickedDateString : String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locations_)
        mAuth = FirebaseAuth.getInstance()
        adapter = LocationClass(name,url,dateString,this)
        firabaseDatabase = FirebaseDatabase.getInstance()
        listView.adapter = adapter

        getData()


        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            val intent = Intent(applicationContext,Show_Location_Activity :: class.java)
            intent.putExtra("name",name[position])
            intent.putExtra("lat",lat[position])
            intent.putExtra("lon",lon[position])
            intent.putExtra("imgName",imgName[position])
            intent.putExtra("comm",comment[position])
            intent.putExtra("address",address[position])
            intent.putExtra("url",url[position])
            intent.putExtra("folderName",folderName[position])
            intent.putExtra("date",dateString[position])


            startActivity(intent)
        }
    }

    fun getData(){

        val newRef = firabaseDatabase!!.getReference("Data")


        newRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                adapter!!.clear()
                name!!.clear()
                email!!.clear()
                comment.clear()
                address.clear()
                lat.clear()
                lon.clear()
                url.clear()
                imgName.clear()
                folderName.clear()
                dateString.clear()

                for(i in p0.children){
                    val hashMap = i.value as HashMap<String,String>

                    if(hashMap.size > 0 && hashMap["email"] == mAuth!!.currentUser!!.email && (pickedDateString == hashMap["date"] || pickedDateString == null)){


                        if(hashMap["name"] != null)
                            name.add(hashMap["name"]!!)

                        if(hashMap["URL"] != null)
                            url.add(hashMap["URL"]!!)

                        if(hashMap["email"] != null)
                            email.add(hashMap["email"]!!)

                        if(hashMap["LAT"] != null)
                            lat.add(hashMap["LAT"]!!)

                        if(hashMap["LON"] != null)
                            lon.add(hashMap["LON"]!!)

                        if(hashMap["address"] != null)
                            address.add(hashMap["address"]!!)

                        if(hashMap["comment"] != null)
                            comment.add(hashMap["comment"]!!)

                        if(hashMap["imageName"] != null)
                            imgName.add(hashMap["imageName"]!!)

                        if(hashMap["folderName"] != null)
                            folderName.add(hashMap["folderName"]!!)

                        if(hashMap["date"] != null)
                            dateString.add(hashMap["date"]!!)

                        adapter!!.notifyDataSetChanged()
                    }
                }

            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.add_location,menu)

        return super.onCreateOptionsMenu(menu)
    }


    fun datePicker  (){
        var c = Calendar.getInstance()

        var day = c.get(Calendar.DAY_OF_MONTH)
        var month = c.get(Calendar.MONTH)
        var year = c.get(Calendar.YEAR)

        val dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            pickedDateString = dayOfMonth.toString()+". "+(month+1).toString()+". "+year.toString()+"."
            getData()
        },year,month,day)


        dpd.show()


    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item?.itemId == R.id.add_location){

            val intent = Intent(applicationContext,Add_Location_Activity :: class.java)
            startActivity(intent)
        }

        if(item?.itemId == R.id.date){

            datePicker()


        }

        if(item?.itemId == R.id.allLocations){
            pickedDateString = null
            getData()

        }

        return super.onOptionsItemSelected(item)
    }
}
