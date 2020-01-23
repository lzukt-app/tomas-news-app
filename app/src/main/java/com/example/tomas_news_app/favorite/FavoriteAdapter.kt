package com.example.tomas_news_app.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tomas_news_app.R
import com.example.tomas_news_app.news.NewsItem
import com.example.tomas_news_app.utils.reFormatDate
import kotlinx.android.synthetic.main.activity_favorite.view.*

class FavoriteAdapter(
    val onSelected: (NewsItem) -> Unit,
    val onFavorite: (NewsItem) -> Unit

) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private val list = mutableListOf<NewsItem>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.activity_favorite,
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
            Glide.with(itemView)
                .load(source.urlToImage)
                .placeholder(R.drawable.news_img_0)
                .thumbnail(Glide.with(itemView).load(R.drawable.loading))
                .dontAnimate()
                .into(itemView.imageUrl)
            itemView.title.text = source.title
            itemView.description.text = source.description
            itemView.datetime.text = reFormatDate(source.publishedAt, "yyyy-MM-dd HH:mm:ss")
            itemView.button_make_favorite.apply {
                if (source.favorite) {
                    setBackgroundResource(R.drawable.star)
                } else {
                    setBackgroundResource(R.drawable.star_off)
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
