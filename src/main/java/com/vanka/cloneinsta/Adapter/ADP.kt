package com.vanka.cloneinsta.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.vanka.cloneinsta.R

class ADP(var list: ArrayList<Uri>, var context: Context):RecyclerView.Adapter<ADP.ViewHolder>() {
    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    var image = 0
    var video = 1
    var check = -1
    var lil = arrayListOf<Uri>()
    override fun getItemViewType(position: Int): Int {

      if (list[position].toString().contains("video")||list[position].toString().contains(".mp4")){
        //  Toast.makeText(context, "${list}", Toast.LENGTH_SHORT).show()
                return video

      }else{
       //  Toast.makeText(context, "${list}+image", Toast.LENGTH_SHORT).show()
          return image
      }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(viewType==video){
            check=1
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.video_list,parent,false))
        }else{
            check =0
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.image_list,parent,false))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       // Toast.makeText(context, "${list}", Toast.LENGTH_SHORT).show()

            try {
               //Toast.makeText(context, "${list[position]}", Toast.LENGTH_SHORT).show()
                holder.itemView.findViewById<VideoView>(R.id.VdeoView).setVideoURI(list[position])
                val mediaController = MediaController(context)
                mediaController.setAnchorView(holder.itemView.findViewById<VideoView>(R.id.VdeoView))

                mediaController.setMediaPlayer(holder.itemView.findViewById<VideoView>(R.id.VdeoView))
                holder.itemView.findViewById<VideoView>(R.id.VdeoView)
                    .setMediaController(mediaController);
                holder.itemView.findViewById<VideoView>(R.id.VdeoView).setOnPreparedListener {
                    holder.itemView.findViewById<VideoView>(R.id.VdeoView).start()


                }
            }catch (e:Exception){
                    holder.itemView.findViewById<ImageView>(R.id.img).setImageURI(list[position])

                }


        }



    override fun getItemCount(): Int {
        return list.size
    }
}