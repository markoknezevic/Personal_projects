package com.example.currencyconverter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

var rate = 0.0

class MainActivity : AppCompatActivity() {




    var myDatabase :  SQLiteDatabase ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)






        /////////////////////////////////////////////////////////////////////////////
        fromSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                fromNumber.setText("")
                toNumber.setText("Result")
            }

        }

        toSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                fromNumber.setText("")
                toNumber.setText("Result")
            }

        }

        ////////////////////////////////////////////////////////////////////////////////




        try {


            fromNumber.addTextChangedListener(object : TextWatcher{
                override fun afterTextChanged(s: Editable?) {

                    convert(s.toString())
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

            })

        }catch (e : Exception){

        }

    }






    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }





    override fun onOptionsItemSelected(item: MenuItem?): Boolean {


        if(item!!.itemId == R.id.save){
            val sdf = SimpleDateFormat("dd M yyyy")

            val current = sdf.format(Date()).toString()
            val currency1 = fromSpinner.selectedItem.toString()
            val currency2 = toSpinner.selectedItem.toString()

            myDatabase = this.openOrCreateDatabase("Currency", Context.MODE_PRIVATE,null)


            val database = Database(currency1,currency2, rate.toString(),current)
            database.saveData(myDatabase!!)

            myDatabase!!.close()
            Toast.makeText(applicationContext,"Data Saved",Toast.LENGTH_LONG).show()


        }



        if(item.itemId == R.id.view){

            val intent = Intent(applicationContext,Saved_Currency_Activity :: class.java)
            startActivity(intent)

        }
        return super.onOptionsItemSelected(item)
    }


fun copyToClipboard(view : View){
    var clipboardManager : ClipboardManager ?= null
    clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("text",toNumber.text.toString())
    clipboardManager.primaryClip = clip

    Toast.makeText(applicationContext,"Text copied",Toast.LENGTH_LONG).show()
}





    fun convert(s : String){


        val str1 = fromSpinner.selectedItem.toString()
        val str2 = toSpinner.selectedItem.toString()

        var url = StringBuilder("https://free.currconv.com/api/v7/convert?q=&compact=ultra&apiKey=a6d115528647a38d9aab")
        url.insert(43,str1+"_"+str2)
        val downloadData = Download(str1+"_"+str2)
        downloadData.execute(url.toString())

        if(fromNumber.text.isEmpty()) {
            toNumber.text = "Result"
        }else{
            val cur1 = s.toString().toDouble()
            toNumber.text = (Math.round(rate * cur1*100.0)/100.0).toString()
        }

    }







    fun invert(view: View){
        val index = fromSpinner.selectedItemPosition
        fromSpinner.setSelection(toSpinner.selectedItemPosition)
        toSpinner.setSelection(index)


    }



}
