package com.example.ujinsia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class FurtherAid : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect)

        val listView= findViewById<ListView>(R.id.listView)
        val list= mutableListOf<Model>()


        //adding images
        list.add(Model("Children","",R.drawable.ic_baseline_local_police_24))
        list.add(Model("Teachers","",R.drawable.ic_baseline_contacts_24))
        list.add(Model("Community member","",R.drawable.ic_baseline_contacts_24))



        listView.adapter= Adapter(this,R.layout.row,list)
        listView.setOnItemClickListener { parent: AdapterView<*>, view: View, position:Int, id:Long ->

            if (position== 0) {
                val intent = Intent(this, StudentReportActivity::class.java)
                startActivity(intent)
            }
            if (position==1){
                val intent = Intent(this, TeachersReportActivity::class.java)
                startActivity(intent)
            }
            if (position==2){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

        }
    }
}