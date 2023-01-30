package com.vanka.cloneinsta

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.FirebaseDatabaseKtxRegistrar
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.vanka.cloneinsta.Adapter.ADP
import com.vanka.cloneinsta.Adapter.PostAdapter
import com.vanka.cloneinsta.Model.PostModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class UploadActivity : AppCompatActivity() {
    var i = 0

    var list: ArrayList<Uri> = arrayListOf()
    var list1: ArrayList<String> = arrayListOf()

    var getContentBro = registerForActivityResult(ActivityResultContracts.GetContent()) {
        list.add(it!!)
        uploadImg(it)
        findViewById<RecyclerView>(R.id.rvM).adapter = ADP(list, this)

    }


    private fun uploadImg(imgUri: Uri) {

        findViewById<ProgressBar>(R.id.progressBar2).visibility = View.VISIBLE

        var uuid = UUID.randomUUID().toString()

        if (imgUri.toString().contains("video") || imgUri.toString().contains(".mp4")) {
            FirebaseStorage.getInstance().getReference("video")
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child(uuid).putFile(imgUri).addOnSuccessListener {
                    FirebaseStorage.getInstance().getReference("video")
                        .child(FirebaseAuth.getInstance().currentUser!!.uid)
                        .child(uuid).downloadUrl.addOnSuccessListener { url ->
                            list1.add(url.toString())
                        }
                }
        } else {
            FirebaseStorage.getInstance().getReference("image")
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child(uuid).putFile(imgUri).addOnSuccessListener {
                    FirebaseStorage.getInstance().getReference("image")
                        .child(FirebaseAuth.getInstance().currentUser!!.uid)
                        .child(uuid).downloadUrl.addOnSuccessListener { url ->
                            list1.add(url.toString())
                        }
                }

        }

        findViewById<Button>(R.id.postUpload).setOnClickListener {

            if (i == list1.size) {
                findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
                findViewById<ProgressBar>(R.id.progressBar).max = 1000
                var pg = 600
                ObjectAnimator.ofInt(  findViewById<ProgressBar>(R.id.progressBar),"progress",pg)
                    .setDuration(1000)
                    .start()
                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                val currentDate = sdf.format(Date())
                var data = PostModel(
                    list1,
                    FirebaseAuth.getInstance().currentUser!!.displayName,
                    FirebaseAuth.getInstance().currentUser!!.photoUrl.toString(),
                    currentDate
                )



                    FirebaseDatabase.getInstance().getReference("Post")
                        .push().setValue(data).addOnSuccessListener {
                            findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
                            startActivity(Intent(this,MainActivity2::class.java))
                        }




            }else{
                Toast.makeText(this, "Please wait", Toast.LENGTH_SHORT).show()
            }


        }
    }




    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        findViewById<ProgressBar>(R.id.progressBar2).visibility = View.GONE
 findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE

        findViewById<RecyclerView>(R.id.rvM).layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        var add = findViewById<FloatingActionButton>(R.id.add)
        var addImg = findViewById<FloatingActionButton>(R.id.addImage)
        var addVideo = findViewById<FloatingActionButton>(R.id.addVideo)


        addImg.visibility = View.GONE
        addVideo.visibility = View.GONE
        add.setOnClickListener {
            if (addImg.isVisible) {

                addImg.visibility = View.GONE
                addVideo.visibility = View.GONE
            } else {

                addImg.visibility = View.VISIBLE
                addVideo.visibility = View.VISIBLE
            }
        }

        addImg.setOnClickListener {
            getContentBro.launch("image/*")
            i++

        }
        addVideo.setOnClickListener {
            getContentBro.launch("video/*")
            i++


        }





    }
}




