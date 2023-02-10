package com.example.ujinsia

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val items: List<Item>, private val context: Context) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item, parent, false) as CardView
        return ViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardView.findViewById<TextView>(R.id.textView).text = items[position].name
        holder.cardView.findViewById<TextView>(R.id.textView7).text = items[position].description
        holder.cardView.findViewById<ImageView>(R.id.imageview).setImageResource(items[position].image)

        holder.cardView.setOnClickListener {
            val intent = Intent(context, items[position].activity)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = items.size
}