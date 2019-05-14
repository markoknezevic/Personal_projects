package com.example.instagramfirebase

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_feed.*

class FeedActivity : AppCompatActivity() {

    var userEmailFirebase : ArrayList<String> = ArrayList<String>()
    var userCommentFirebase : ArrayList<String> = ArrayList<String>()
    var userImageFirebase : ArrayList<String> = ArrayList<String>()

    var firebaseDatabase : FirebaseDatabase ?= null
    //var myRef : DatabaseReference ?= null
    var adapter : PostClass ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        firebaseDatabase = FirebaseDatabase.getInstance()
       // myRef = firebaseDatabase!!.getReference()

        adapter = PostClass(userEmailFirebase,userImageFirebase,userCommentFirebase,this)
        listView.adapter = adapter
        getDataFirebase()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.add_post,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item!!.itemId == R.id.add_post){
            val intent = Intent(applicationContext,UploadActivity :: class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }

    fun getDataFirebase(){
        val newRefrence = firebaseDatabase!!.getReference("Posts")
        newRefrence.addValueEventListener(object : ValueEventListener {


            override fun onDataChange(p0: DataSnapshot) {

                adapter!!.clear()
                userImageFirebase.clear()
                userCommentFirebase.clear()
                userEmailFirebase.clear()

                for( snapshot in p0.children){
                    val hashMap = snapshot.value as HashMap<String,String>
                        if(hashMap.size > 0){
                            val email = hashMap["useremail"]
                            val comment = hashMap["comment"]
                            val image = hashMap["downloadurl"]

                            if(email != null)
                                userEmailFirebase.add(email)

                            if(comment != null)
                                userCommentFirebase.add(comment)

                            if(image != null)
                                userImageFirebase.add(image)
                        }
                    adapter!!.notifyDataSetChanged()
                }
            }


            override fun onCancelled(p0: DatabaseError) {

            }



        })


    }
}
