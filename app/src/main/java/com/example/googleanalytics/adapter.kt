package com.example.ass2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.googleanalytics.MainActivity2
import com.example.googleanalytics.R
import com.google.firebase.analytics.FirebaseAnalytics


class adapter(var activity: Activity, var array: ArrayList<model>) :
    RecyclerView.Adapter<adapter.MyViewHolder>() {


    var mFirebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(activity)


    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val name: TextView = item.findViewById(R.id.name)
        val img: ImageView = item.findViewById(R.id.img)
        val btn = item.findViewById<LinearLayout>(R.id.item_click)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.recycler, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.setText(array[position].name)
        Glide.with(activity).load(array[position].img).into(holder.img)

        holder.btn.setOnClickListener {
            trackScreens(array[position].name)
            val intent = Intent(activity, MainActivity2::class.java)
            intent.putExtra("category_name", array[position].name)
            activity.startActivity(intent)

        }

    }

    override fun getItemCount(): Int {
        return array.size
    }


    fun trackScreens(screenName: String?) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity")
        mFirebaseAnalytics!!.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

}