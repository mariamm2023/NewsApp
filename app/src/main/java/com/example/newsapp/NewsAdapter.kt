package com.example.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.model.ArticlesItem

class NewsAdapter(var items:List<ArticlesItem?>?):RecyclerView.Adapter<NewsAdapter.viewHolder>() {
    class viewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val title:TextView=itemView.findViewById(R.id.title)
        val auther:TextView=itemView.findViewById(R.id.auther)
        val datetime:TextView=itemView.findViewById(R.id.date_time)
        val image:ImageView=itemView.findViewById(R.id.image)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
       val view=LayoutInflater.from(parent.context).inflate(R.layout.itemnews,parent,false)
        return viewHolder(view)
    }

    override fun getItemCount(): Int {
        return items?.size?:0
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
       val item=items?.get(position)
        holder.auther.setText(item?.author)
        holder.title.setText(item?.title)
        holder.datetime.setText(item?.publishedAt)
        Glide.with(holder.itemView).load(item?.urlToImage).into(holder.image)


    }
    fun changedata(data:List<ArticlesItem?>?) {
        items=data
        notifyDataSetChanged()


    }
}