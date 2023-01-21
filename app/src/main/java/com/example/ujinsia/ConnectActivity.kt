package com.example.ujinsia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.net.Uri
import android.content.Intent


class ConnectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect)

        val listView= findViewById<ListView>(R.id.listView)
        val list= mutableListOf<Model>()


        //adding images
        list.add(Model("Call police","",R.drawable.ic_baseline_local_police_24))
        list.add(Model("Contact chief","",R.drawable.ic_baseline_contacts_24))
        list.add(Model("Get help from certified professional","",R.drawable.ic_baseline_person_pin_24))


        listView.adapter= Adapter(this,R.layout.row,list)
        listView.setOnItemClickListener { parent: AdapterView<*>, view: View, position:Int, id:Long ->

            if (position==0){
                val phone = "911"

                val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))

                startActivity(intent)
            }
            if (position==1){
                val phone = "911"

                val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))

                startActivity(intent)
            }
            if (position==2){
                val phone = "911"

                val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))

                startActivity(intent)
            }
            if (position==3){
                val phone = "911"

                val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))

                startActivity(intent)
            }
            if (position==4){
                val phone = "911"

                val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))

                startActivity(intent)
            }



        }}}

