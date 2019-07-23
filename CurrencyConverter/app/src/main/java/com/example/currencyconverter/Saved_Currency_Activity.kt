package com.example.currencyconverter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_saved__currency_.*

class Saved_Currency_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved__currency_)

        val myDatabase = this.openOrCreateDatabase("Currency", Context.MODE_PRIVATE,null)
        var database = Database("","","","")
        val set = database.getData(myDatabase)

        var arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,set.toMutableList())

        listView.adapter = arrayAdapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->

            val intent = Intent(applicationContext,Selected_Activity :: class.java)
            intent.putExtra("currency",set.elementAt(position))

            startActivity(intent)

        }
    }
}
