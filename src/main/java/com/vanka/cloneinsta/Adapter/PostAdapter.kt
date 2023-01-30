package com.vanka.cloneinsta.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vanka.cloneinsta.Model.PostModel
import com.vanka.cloneinsta.R


class PostAdapter(var context: Context,var list:ArrayList<PostModel>):RecyclerView.Adapter<PostAdapter.ViewHolder>(){
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    var image = 0
    var video = 1
    var check = -1
    var lil = arrayListOf<Uri>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


            return PostAdapter.ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
            )

    }

    @SuppressLint("CutPasteId")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(list[position].profile)
            .into(holder.itemView.findViewById(R.id.imageView2))

        holder.itemView.findViewById<TextView>(R.id.textView2).text =list[position].name
        holder.itemView.findViewById<TextView>(R.id.textView3).text =list[position].date
       holder.itemView.findViewById<RecyclerView>(R.id.rr).layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        holder.itemView.findViewById<RecyclerView>(R.id.rr).adapter = SlideAdapter(context,list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

}



