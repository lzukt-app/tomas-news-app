package com.example.tomas_news_app.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tomas_news_app.R
import com.example.tomas_news_app.main.MainActivity
import kotlinx.android.synthetic.main.fragment_source_list.*

class NewsListFragment : Fragment() {
    lateinit var viewModel: NewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(
            this,
            NewViewModelFactory(requireActivity().application)
        )
            .get(NewViewModel::class.java)
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
        recycler.adapter = adapter
        viewModel.data.observe(this, Observer { newData ->
            adapter.setItems(newData)
        })
    }
/*
    private fun onNewSelected(source: NewsItem) {
        //(requireActivity() as MainActivity).showNews(source)
    }
*/
    companion object {
        fun newInstance() = NewsListFragment()
    }

}