package com.example.networking.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.networking.R
import com.example.networking.activity.MainActivity
import com.example.networking.model.Poster
import java.util.ArrayList

class PosterAdapter (var activity: MainActivity, var items: ArrayList<Poster>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun getItemCount(): Int {
        return items.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_poster_list, parent, false)
        return PosterViewHolderYes(view)

    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chat = items[position]
        if (holder is PosterViewHolderYes){
            val ll_poster = holder.ll_poster
            val tv_title = holder.tv_title
            val tv_body = holder.tv_body


            tv_title.text = chat.title.toUpperCase()
            tv_body.text = chat.body
            ll_poster.setOnClickListener {
                activity.dialogPoster(items[position])
                false
            }

        }
    }

    class PosterViewHolderYes(view: View) : RecyclerView.ViewHolder(view) {
        var ll_poster : LinearLayout = view.findViewById(R.id.ll_poster)
        var tv_title : TextView = view.findViewById(R.id.tv_title)
        var tv_body : TextView = view.findViewById(R.id.tv_body)

    }

}