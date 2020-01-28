package com.example.tomasNewsApp.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tomasNewsApp.R
import com.example.tomasNewsApp.utils.reFormatDate
import kotlinx.android.synthetic.main.activity_news.view.*
import kotlinx.android.synthetic.main.activity_source.view.description
import kotlinx.android.synthetic.main.activity_source.view.title

class NewsListAdapter(
    val onSelected: (NewsItem) -> Unit,
    val onFavorite: (NewsItem) -> Unit

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

    override fun getItemCount(): Int = list.size

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
            Glide.with(itemView)
                .load(source.urlToImage)
                .placeholder(R.mipmap.news_img_0)
                .thumbnail(Glide.with(itemView).load(R.mipmap.loading))
                .dontAnimate()
                .into(itemView.imageUrl)
            itemView.title.text = source.title
            itemView.description.text = source.description
            itemView.datetime.text = reFormatDate(source.publishedAt, "yyyy-MM-dd HH:mm:ss")
            itemView.button_make_favorite.apply {
                if (source.favorite) {
                    setBackgroundResource(R.mipmap.star)
                } else {
                    setBackgroundResource(R.mipmap.star_off)
                }
            }
            itemView.setOnClickListener {
                onSelected.invoke(source)
            }

            itemView.button_make_favorite.setOnClickListener {
                onFavorite(source)
            }
        }
    }
}