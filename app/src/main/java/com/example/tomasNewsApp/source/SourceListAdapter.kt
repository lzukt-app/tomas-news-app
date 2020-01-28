package com.example.tomasNewsApp.source

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tomasNewsApp.R
import kotlinx.android.synthetic.main.activity_source.view.*

class SourceListAdapter(
    val onSelected: (SourceItem) -> Unit
) : RecyclerView.Adapter<SourceListAdapter.ViewHolder>() {

    private val list = mutableListOf<SourceItem>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.activity_source,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setItems(list: List<SourceItem>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(source: SourceItem) {
            itemView.title.text = source.title
            itemView.description.text = source.description
            itemView.setOnClickListener {
                onSelected.invoke(source)
            }
        }
    }
}
