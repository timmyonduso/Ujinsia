package com.example.ujinsia

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class GetAidActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect)

        val listView= findViewById<ListView>(R.id.listView)
        val list= mutableListOf<Model>()


        //adding images
        list.add(Model("Submit detailed report","",R.drawable.ic_baseline_local_police_24))
        list.add(Model("Connect with certified professional","",R.drawable.ic_baseline_contacts_24))


        listView.adapter= Adapter(this,R.layout.row,list)
        listView.setOnItemClickListener { parent: AdapterView<*>, view: View, position:Int, id:Long ->

            if (position== 0) {
                val intent = Intent(this, DivulgeActivity::class.java)
                startActivity(intent)

            }
            if (position==1){
                val phone = "911"

                val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))

                startActivity(intent)
            }

        }
    }
}