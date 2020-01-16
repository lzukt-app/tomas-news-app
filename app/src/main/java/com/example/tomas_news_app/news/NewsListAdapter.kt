package com.example.tomas_news_app.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tomas_news_app.R
import kotlinx.android.synthetic.main.activity_news.view.*
import kotlinx.android.synthetic.main.activity_source.view.description
import kotlinx.android.synthetic.main.activity_source.view.title

class NewsListAdapter(
    //val onSelected: (NewsItem) -> Unit
) : RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    private val list = mutableListOf<NewsItem>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.activity_news,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setItems(list: List<NewsItem>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(source: NewsItem) {
            //itemView.appCompatImageView.setImageResource(R.drawable.news_img_2)
            //DownLoadImageTask(itemView.imageUrl).execute(source.urlToImage)
            Glide.with(itemView).load(source.urlToImage).into(itemView.imageUrl)
            itemView.title.text = source.title
            itemView.description.text = source.description
            itemView.datetime.text = source.datetime
            /*itemView.setOnClickListener {
                onSelected.invoke(source)
            }*/
        }
    }


}