package com.example.tomas_news_app.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tomas_news_app.R
import kotlinx.android.synthetic.main.fragment_source_list.*

class NewsListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        val adapter = NewsListAdapter()
        adapter.setItems(
            listOf(
                NewsItem(
                    0,
                    "title 1",
                    "description",
                    "datetime"
                ),
                NewsItem(
                    0,
                    "title 2",
                    "description",
                    "datetime"
                )
            )
        )
        recycler.adapter = adapter
    }

    companion object {
        fun newInstance() = NewsListFragment()
    }

}