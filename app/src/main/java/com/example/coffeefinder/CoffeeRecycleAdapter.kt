package com.example.coffeefinder

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CoffeeRecycleAdapter(val context: Context, val coffeePlaces: List<CoffeePlace>) : RecyclerView.Adapter<CoffeeRecycleAdapter.ViewHolder>() {


    interface OnItemClickListener {
        fun onItemClick(coffeePlaceListItem: CoffeePlace) {

        }
    }

    var layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.list_item, parent, false)

        return ViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return coffeePlaces.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coffeePlace = coffeePlaces[position]

        holder.nameTextView.text = coffeePlace.name
        //TODO lös hur jag refererar till en bildfil
        //holder.imageView.setImageResource(coffeePlace.image.getImageId());
        holder.locationTextView.text = coffeePlace.location
        holder.addressTextView.text = coffeePlace.address
        holder.commentTextView.text = coffeePlace.comment
        holder.index = position

    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)
        var imageView = itemView.findViewById<ImageView>(R.id.imageView)
        var locationTextView = itemView.findViewById<TextView>(R.id.locationTextView)
        var addressTextView = itemView.findViewById<TextView>(R.id.addressTextView)
        var commentTextView = itemView.findViewById<TextView>(R.id.commentTextView)
        var index = 0
        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, MoreInfoActivity::class.java)
                intent.putExtra("index", index)
                intent.putExtra("name", coffeePlaces[index].name)
                intent.putExtra("location", coffeePlaces[index].location)
                intent.putExtra("comment", coffeePlaces[index].comment)

                itemView.context.startActivity(intent)
            }
        }
    }
}