package com.vanka.cloneinsta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.FirebaseDatabaseKtxRegistrar
import com.google.firebase.ktx.Firebase
import com.vanka.cloneinsta.Adapter.PostAdapter
import com.vanka.cloneinsta.Model.PostModel


class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        var add = findViewById<FloatingActionButton>(R.id.add)

        var rv = findViewById<RecyclerView>(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this)
        add.setOnClickListener {
          startActivity(  Intent(this,UploadActivity::class.java))
        }
                getData()
    }

    private fun getData() {
        FirebaseDatabase.getInstance().getReference("Post")
            .addValueEventListener(object: ValueEventListener{
                var list = ArrayList<ArrayList<PostModel>>()
                var l = ArrayList<PostModel>()
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children){
                        try {


                            var getData = data.getValue(PostModel::class.java)

                            l.add(getData!!)
                        }catch (e:Exception){

                        }
                    }
                    findViewById<RecyclerView>(R.id.rv).adapter = PostAdapter(this@MainActivity2,l)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }


}