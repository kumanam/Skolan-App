package com.example.skolanapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skolanapp.models.News
import com.example.skolanapp.R
import com.example.skolanapp.databinding.ItemNewsBinding

class NewsAdapter(
    private val news: List<News>
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(
        val binding: ItemNewsBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsViewHolder {

        val binding = ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: NewsViewHolder,
        position: Int
    ) {
        val newsItem = news[position]

        holder.binding.textNewsTitle.text = newsItem.title
        holder.binding.textNewsDescription.text = newsItem.description
        holder.binding.buttonReadMore.text =
            holder.itemView.context.getString(R.string.las_mer)

        var expanded = false

        holder.binding.buttonReadMore.setOnClickListener {

            expanded = !expanded

            if (expanded) {
                holder.binding.textNewsDescription.text = newsItem.fullText
                holder.binding.buttonReadMore.text =
                    holder.itemView.context.getString(R.string.visa_mindre)
            } else {
                holder.binding.textNewsDescription.text = newsItem.description
                holder.binding.buttonReadMore.text =
                    holder.itemView.context.getString(R.string.las_mer)
            }
        }
    }

    override fun getItemCount(): Int {
        return news.size
    }
}