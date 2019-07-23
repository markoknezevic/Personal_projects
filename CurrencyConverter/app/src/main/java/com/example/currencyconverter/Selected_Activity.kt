package com.example.currencyconverter

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_selected_.*
import java.text.SimpleDateFormat
import java.util.*

class Selected_Activity : AppCompatActivity() {

    var database : Database ?= null
    var myDatabase : SQLiteDatabase ?= null
    var str : List<String> ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_)


        myDatabase = this.openOrCreateDatabase("Currency", Context.MODE_PRIVATE,null)



        val sdf = SimpleDateFormat("dd M yyyy")
        val date = sdf.format(Date()).toString()

        calendarView.setDate(SimpleDateFormat("dd M yyyy").parse(date).time,true,true)



        val currency = intent.getStringExtra("currency")
        textView.text = currency

        str = currency.split(" ")

        database = Database(str!![0],str!![1],"0",date)
        database!!.getDatabyDate(myDatabase!!)


        setView(date)



        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->


            val date = dayOfMonth.toString()+" "+(month+1).toString()+" "+year.toString()
            resultView.text = "Result"
            valueText.setText("")
            setView(date)
        }


        valueText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {


                val va2 = database!!.rate.toDouble()
                if(s!!.isEmpty()){
                    resultView.text = "Result"
                }else{
                    val va1 = s.toString().toDouble()
                    resultView.text = (Math.round(va1 * va2*100.0)/100.0).toString()
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })



    }



    fun setView(date: String){

        database = Database(str!![0],str!![1],"0",date)
        database!!.getDatabyDate(myDatabase!!)
        if(database!!.rate == "0.0"){
            textView2.text = "1 -> No Record"
        }else{
            textView2.text = "1 -> "+database!!.rate
        }


    }






    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.selected_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }






    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item!!.itemId == R.id.delete){
            database!!.deleteData(myDatabase!!)

            val intent = Intent(applicationContext,Saved_Currency_Activity :: class.java)
            startActivity(intent)
        }


        if(item.itemId == R.id.delete_date){
            database!!.deleteDate(myDatabase!!)
            textView2.text = "1 -> No Record"

        }

        return super.onOptionsItemSelected(item)
    }
}
