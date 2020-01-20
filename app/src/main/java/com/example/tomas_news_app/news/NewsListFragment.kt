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
import com.example.tomas_news_app.source.SourceItem
import kotlinx.android.synthetic.main.fragment_news_list.*
import kotlinx.android.synthetic.main.fragment_news_list.toolbar
import kotlinx.android.synthetic.main.fragment_source_list.*
import kotlinx.android.synthetic.main.fragment_source_list.recycler

class NewsListFragment() : Fragment() {
    lateinit var sourceId: String
    lateinit var viewModel: NewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sourceId = arguments!!.getString(KEY_SOURCE_ID) ?: ""

        viewModel = ViewModelProviders.of(
            this,
            NewViewModelFactory(requireActivity().application, sourceId)
        )
            .get(NewViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.layoutManager = LinearLayoutManager(requireContext())

        (requireActivity() as MainActivity).setSupportActionBar(toolbar)
        (requireActivity() as MainActivity).actionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as MainActivity).title = arguments!!.getString(KEY_SOURCE_TITLE)

        val adapter = NewsListAdapter(::onNewSelected)
        recycler.adapter = adapter
        viewModel.data.observe(this, Observer { newData ->
            adapter.setItems(newData)
        })

        chip_popular_today.setOnClickListener {
            viewModel.onPopularTodayArticlesSelected()
        }

        chip_popular_all_time.setOnClickListener {
            viewModel.onAllTimeArticlesSelected()
        }

        chip_newest.setOnClickListener {
            viewModel.onNewestArticlesSelected()
        }
    }


    private fun onNewSelected(article: NewsItem) {
        (requireActivity() as MainActivity).showArticle(article)
    }


    companion object {

        private const val KEY_SOURCE_TITLE = "key_source_title"
        private const val KEY_SOURCE_ID = "key_source_id"

        fun newInstance(sourceItem: SourceItem): NewsListFragment {
            val arguments = Bundle()
            arguments.putString(KEY_SOURCE_TITLE, sourceItem.title)
            arguments.putString(KEY_SOURCE_ID, sourceItem.id)
            val fragment = NewsListFragment()
            fragment.arguments = arguments
            return fragment
        }
    }

}