package com.example.location_gallery

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_view.view.*

class LocationClass(private val name: ArrayList<String>,
                    private val url: ArrayList<String>,
                    private val date: ArrayList<String>,
                    private val context: Activity) : ArrayAdapter<String>(context,R.layout.custom_view,name){


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = context.layoutInflater

        var customView = layoutInflater.inflate(R.layout.custom_view,null,true)

        customView.nameText.text = name[position]+"\n ("+date[position]+")"
        Picasso.get().load(url[position]).into(customView.imageView2)
        return customView
    }
}