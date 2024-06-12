package com.example.musicplayer

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.squareup.picasso.Picasso

class MyAdapter(val context : Activity , val data : List<Data>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemview = LayoutInflater.from(context).inflate(R.layout.list,parent,false)
        return MyViewHolder(itemview)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentData = data[position]

        holder.songname.text = currentData.title
        holder.artist.text = currentData.artist.name
        Picasso.get().load(currentData.album.cover).into(holder.img);

        holder.go.setOnClickListener {
            val intent = Intent(context,PlayMusic::class.java)
            intent.putExtra("song",currentData.preview)
            intent.putExtra("artist",currentData.title)
            intent.putExtra("image",currentData.album.cover)
            context.startActivity(intent)
        }
    }

    class MyViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview) {

        val img : ImageView
        val songname : TextView
        val artist : TextView
        val go : ImageView

        init{
            img = itemview.findViewById(R.id.img)
            songname = itemview.findViewById(R.id.songname)
            artist = itemview.findViewById(R.id.artist)
            go = itemview.findViewById(R.id.go)

        }

    }

}