package com.example.googleanalytics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit

class MainActivity3 : AppCompatActivity() {
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    var db = FirebaseFirestore.getInstance()
    var time1:Long=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val name: TextView = findViewById(R.id.name1)
        val img: ImageView = findViewById(R.id.img1)
        val price: TextView = findViewById(R.id.price1)
        val des: TextView = findViewById(R.id.des)



        time1 =System.currentTimeMillis()
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        val product_id = intent.getStringExtra("product_id").toString()
        val category_name = intent.getStringExtra("category_name").toString()
        db.collection("Categories").document(category_name).collection("products").document(product_id).get().addOnSuccessListener {
            name.text = it.get("name").toString()
            Glide.with(applicationContext).load(it.get("img")).into(img)
            price.text = it.get("price").toString()
            des.text = it.get("des").toString()


        }.addOnFailureListener{
            Log.e("get",it.message.toString())
        }
        trackScreens("Product details")

    }

    fun trackScreens(productName: String?) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Event.SELECT_ITEM, productName)
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity")
        mFirebaseAnalytics!!.logEvent("select_product", bundle)
    }

    override fun onDestroy() {
        val alltime=System.currentTimeMillis()-time1
        val timeS= TimeUnit.MILLISECONDS.toSeconds(alltime)
        val time=HashMap<String, Any>()


        time["Time_Spend"]=timeS
        time["Userid"]="1"
        time["name"]="Product details"
        db.collection("Time_Sp").add(time)
            .addOnFailureListener{
                Log.e("Tag Time ",it.message.toString())
            }


        super.onDestroy()
    }
}
