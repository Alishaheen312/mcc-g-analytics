package com.example.googleanalytics

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ass2.adapter
import com.example.ass2.model
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {


    var db = FirebaseFirestore.getInstance()
    var array = ArrayList<model>()
    var time1: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        db.collection("Categories").get().addOnSuccessListener {
            array.addAll(it.toObjects(model::class.java))
            val adapter = adapter(this, array)
            recyclerView.adapter = adapter
        }
            .addOnFailureListener {
                Log.e("get", it.message.toString())
            }

    }

    override fun onDestroy() {
        val alltime=System.currentTimeMillis()-time1
        val timeS= TimeUnit.MILLISECONDS.toSeconds(alltime)
        val time=HashMap<String, Any>()


        time["Time_Spend"]=timeS
        time["Userid"]="1"
        time["name"]= "Categories"
        db.collection("Time_Sp").add(time)
            .addOnFailureListener{
                Log.e("Tag Time ",it.message.toString())
            }


        super.onDestroy()

    }
}