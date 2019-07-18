package com.example.location_gallery

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add__location_.*
import java.util.*

var image : Uri ?= null
var name : String  ?= null
var com : String ?= null
var dateString : String ?= null

class Add_Location_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add__location_)
    }

    fun selectImage(view : View){

        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),2)

        }else{
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

            startActivityForResult(intent,1)

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == 2){

            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent,1)

            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1 && data != null && resultCode == Activity.RESULT_OK){


            image = data.data
            try {

                val imageS = MediaStore.Images.Media.getBitmap(this.contentResolver,image)

                imageView.setImageBitmap(imageS)

            }catch (e : Exception){
                e.printStackTrace()
            }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    fun checkData() : Boolean{

        if(nameText.text.isEmpty() == false && commentText.text.isEmpty() == false && dateString != null && image != null){
            return true
        }else{
            return false
        }

    }


    fun next(view:View){

        if(checkData()) {
            name = nameText.text.toString()
            com = commentText.text.toString()

            val intent = Intent(applicationContext, MapsActivity::class.java)
            startActivity(intent)
        }else{
            Toast.makeText(applicationContext,"All fields are mandatory.",Toast.LENGTH_LONG).show()
        }
    }


    fun pickDate(view : View){


        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        val dpd = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

            dateString = dayOfMonth.toString()+". "+(month+1).toString()+". "+year.toString()+"."

        },year,month,day)
        dpd.show()

    }
}
