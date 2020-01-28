package com.example.tomasNewsApp.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tomasNewsApp.R
import com.example.tomasNewsApp.main.MainActivity
import com.example.tomasNewsApp.source.SourceItem
import kotlinx.android.synthetic.main.fragment_news_list.*
import kotlinx.android.synthetic.main.fragment_news_list.toolbar
import kotlinx.android.synthetic.main.fragment_source_list.recycler

class NewsListFragment : Fragment() {
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
    ): View? = inflater.inflate(R.layout.fragment_news_list, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        when (viewModel.chipid.value) {
            1 -> viewModel.onPopularTodayArticlesSelected()
            2 -> viewModel.onAllTimeArticlesSelected()
            else -> viewModel.onNewestArticlesSelected()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.layoutManager = LinearLayoutManager(requireContext())

        (requireActivity() as MainActivity).setSupportActionBar(toolbar)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        (requireActivity() as MainActivity).title = arguments!!.getString(KEY_SOURCE_TITLE)

        val adapter = NewsListAdapter(::onNewSelected, ::onMakeArticleFavorite)

        recycler.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner, Observer { newData ->
            adapter.setItems(newData)
        })

        toolbar.setNavigationOnClickListener {
            (requireActivity() as MainActivity).supportFragmentManager.popBackStack()
        }

        chip_popular_today.setOnClickListener {
            viewModel.chipID.value = POPULAR_TODAY_CHIP_ID
            viewModel.onCreate()
        }

        chip_popular_all_time.setOnClickListener {
            viewModel.chipID.value = POPULAR_ALL_TIME_CHIP_ID
            viewModel.onCreate()
        }

        chip_newest.setOnClickListener {
            viewModel.chipID.value = NEWEST_ARTICLE_CHIP_ID
            viewModel.onCreate()
        }

        swipe_refresh_news.setOnRefreshListener {
            Toast.makeText(context, "Refresh News list", Toast.LENGTH_SHORT).show()
            swipe_refresh_news.isRefreshing = false
            viewModel.onCreate()
        }
    }

    private fun onNewSelected(article: NewsItem) {
        (requireActivity() as MainActivity).showArticle(article)
    }

    private fun onMakeArticleFavorite(article: NewsItem) {
        viewModel.changeArticleFavoriteStatus(
            article
        )
        Toast.makeText(this.context, "Favorite", Toast.LENGTH_SHORT).show()
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