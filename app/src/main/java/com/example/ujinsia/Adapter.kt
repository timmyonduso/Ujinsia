package com.example.ujinsia

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.ujinsia.Model

class Adapter(private var contxt: Context, private var resources:Int, private var items:List<Model>) :
    ArrayAdapter<Model>(contxt,resources,items) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater:LayoutInflater= LayoutInflater.from(contxt)
        val view:View=layoutInflater.inflate(resources,null)

        val imageView: ImageView = view.findViewById(R.id.image)
        val titleTextView: TextView =view.findViewById(R.id.textView1)
        val descriptionTextView:TextView=view.findViewById(R.id.textView2)

        val mItem: Model =items[position]
        imageView.setImageDrawable(contxt.resources.getDrawable(mItem.img))

        titleTextView.text=mItem.title
        descriptionTextView.text =mItem.description


        return view
    }

}