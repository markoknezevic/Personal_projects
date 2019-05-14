package com.example.instagramfirebase

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_view.view.*

class PostClass(private val userEmail: ArrayList<String>,
                private val userImage: ArrayList<String>,
                private val userComment : ArrayList<String>,
                private val context: Activity)
                : ArrayAdapter<String>(context,R.layout.custom_view,userEmail){


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater = context.layoutInflater

        val customView = layoutInflater.inflate(R.layout.custom_view,null,true)
        customView.customUsename.text = userEmail[position]
        customView.customComment.text = userComment[position]

        Picasso.with(context).load(userImage[position]).into(customView.customImage)

        return customView
    }
}