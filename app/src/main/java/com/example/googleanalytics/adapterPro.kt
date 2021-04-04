package com.example.ass2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.googleanalytics.MainActivity2
import com.example.googleanalytics.MainActivity3
import com.example.googleanalytics.R
import com.example.googleanalytics.modelProduct
import com.google.firebase.analytics.FirebaseAnalytics


class adapterPro(var activity: Activity, var array: ArrayList<modelProduct>) :
    RecyclerView.Adapter<adapterPro.MyViewHolder>() {


    var mFirebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(activity)


    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val name: TextView = item.findViewById(R.id.name)
        val img: ImageView = item.findViewById(R.id.img_re)
        val price: TextView = item.findViewById(R.id.price)
        val btn = item.findViewById<LinearLayout>(R.id.item_pro)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.item_recycler, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.setText(array[position].name)
        holder.price.setText(array[position].price)
        Glide.with(activity).load(array[position].img).into(holder.img)

        holder.btn.setOnClickListener {
            trackScreens(array[position].name)
            val intent = Intent(activity, MainActivity3::class.java)
            intent.putExtra("product_id", array[position].id)
            intent.putExtra("category_name", array[position].category)
            activity.startActivity(intent)

        }

    }

    override fun getItemCount(): Int {
        return array.size
    }


    fun trackScreens(productName: String?) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Event.SELECT_ITEM, productName)
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity")
        mFirebaseAnalytics!!.logEvent("select_product", bundle)
    }

}