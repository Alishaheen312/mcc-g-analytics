package com.example.googleanalytics

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ass2.adapterPro
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit


class MainActivity2 : AppCompatActivity() {

    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    var db = FirebaseFirestore.getInstance()
    var array=ArrayList<modelProduct>()
    var time1:Long=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val category_name = intent.getStringExtra("category_name")
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        val recyclerView: RecyclerView =findViewById(R.id.recyclerView2)

        recyclerView.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        if (category_name != null) {
            db.collection("Categories").document(category_name).collection("products").get().addOnSuccessListener {
                for (i in it){
                    array.add(
                        modelProduct(
                            i.id, i.get("name").toString(), i.get("img").toString(), i.get("price").toString(),category_name
                        )
                    )
                    Log.e("data_DB",modelProduct(
                        i.id, i.get("name").toString(), i.get("img").toString(), i.get("price").toString(),category_name
                    ).toString())
                }

                val adapter= adapterPro(this, array)
                recyclerView.adapter=adapter
            }
                .addOnFailureListener{
                    Log.e("get", it.message.toString())
                }
        }

    }


    override fun onDestroy() {
        val alltime=System.currentTimeMillis()-time1
        val timeS= TimeUnit.MILLISECONDS.toSeconds(alltime)
        val time=HashMap<String, Any>()


        time["Time_Spend"]=timeS
        time["Userid"]="1"
        time["name"]= "Product"
        db.collection("Time_Sp").add(time)
            .addOnFailureListener{
                Log.e("Tag Time ",it.message.toString())
            }


        super.onDestroy()
    }
}