package com.thefuturestic.imageandtextretrive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    internal lateinit var dirImg: ImageView
    internal lateinit var dirName: TextView
    internal lateinit var dirMsg: TextView
    internal lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dirImg= findViewById(R.id.dir_pic)
        dirName = findViewById(R.id.dir_name)
        dirMsg = findViewById(R.id.dir_msg)


        databaseReference = FirebaseDatabase.getInstance().getReference("DirectorMessage")

        //images and share link
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {
                    if (dataSnapshot.exists()) {
                        val url = dataSnapshot.child("img").value!!.toString()
                        val message = dataSnapshot.child("msg").value!!.toString()
                        val name = dataSnapshot.child("name").value!!.toString()

                        Glide.with(dirImg.context)
                                .load(url)
                                .apply(
                                        RequestOptions()
                                                .placeholder(R.drawable.loading_animation)
                                                .error(R.drawable.ic_broken_image))
                                .into(dirImg)
                        dirName.setText(name)
                        dirMsg.setText(message)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }
}
