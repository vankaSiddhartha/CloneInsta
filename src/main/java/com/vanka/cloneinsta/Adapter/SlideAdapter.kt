package com.vanka.cloneinsta.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vanka.cloneinsta.Model.PostModel
import com.vanka.cloneinsta.R

class SlideAdapter(var context: Context, var list: PostModel):RecyclerView.Adapter<SlideAdapter.ViewHolder>() {
    var q = ArrayList<String>()

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

    }
    var image = 0
    var video = 1
    var check = -1
    var lil = arrayListOf<Uri>()
    override fun getItemViewType(position: Int): Int {

        if (list.post1[position].toString().contains("video")||list.post1[position].toString().contains(".mp4")){
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
            return SlideAdapter.ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.video_list, parent, false)
            )
        }else{
            check =0
            return SlideAdapter.ViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.image_list, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        if (check == 1) {
            holder.itemView.findViewById<VideoView>(R.id.VdeoView)
                .setVideoURI(Uri.parse(list.post1[position]))
            val mediaController = MediaController(context)
            mediaController.setAnchorView(holder.itemView.findViewById<VideoView>(R.id.VdeoView))

            mediaController.setMediaPlayer(holder.itemView.findViewById<VideoView>(R.id.VdeoView))
//            holder.itemView.findViewById<VideoView>(R.id.VdeoView)
//                .setMediaController(mediaController);
            holder.itemView.findViewById<VideoView>(R.id.VdeoView).setOnPreparedListener {
                holder.itemView.findViewById<VideoView>(R.id.VdeoView).start()
            }
        }else{
                Glide.with(context).load(list.post1[position])
                    .into(holder.itemView.findViewById<ImageView>(R.id.img))

            }



    }

    override fun getItemCount(): Int {
        return list.post1.size

    }
}