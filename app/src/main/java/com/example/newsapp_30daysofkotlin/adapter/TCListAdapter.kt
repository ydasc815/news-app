package com.example.newsapp_30daysofkotlin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp_30daysofkotlin.data.NewsData
import com.example.newsapp_30daysofkotlin.R
import kotlinx.android.synthetic.main.item_view.view.*

class TCListAdapter(c: Context, private val listItem: List<NewsData>) :
    RecyclerView.Adapter<TCListAdapter.ViewHolder>() {
    private val context: Context = c

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ViewHolder(
            itemView
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = listItem[position]
        holder.title.text = currentItem.title
        holder.pubTime.text = currentItem.publishTime
        holder.newsLink.text = currentItem.newslink
        Glide.with(context).load(currentItem.imgSrc).placeholder(R.drawable.ina)
            .into(holder.imgSource)

        holder.newsItem.setOnClickListener {
            val newsLink = holder.newsLink.text.toString()
            val bundle = bundleOf("nl" to newsLink)
            it.findNavController().navigate(R.id.action_techFragment_to_newsDetailsFragment, bundle)
        }
    }

    override fun getItemCount(): Int = listItem.size

    class ViewHolder(unitView: View) : RecyclerView.ViewHolder(unitView) {
        val imgSource: ImageView = unitView.news_img
        val title: TextView = unitView.news_title
        val pubTime: TextView = unitView.publish_time
        val newsLink: TextView = unitView.news_link
        val newsItem: CardView = unitView.news_item
    }
}