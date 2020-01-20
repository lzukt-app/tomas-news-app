package com.example.tomas_news_app.source

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

class SourceListFragment() : Fragment() {
    lateinit var viewModel: SourceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(
            this,
            SourceViewModelFactory(requireActivity().application)
        )
            .get(SourceViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_source_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.layoutManager = LinearLayoutManager(requireContext())

        (requireActivity() as MainActivity).setSupportActionBar(toolbar)
        (requireActivity() as MainActivity).actionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as MainActivity).title = getString(R.string.toolbar_title_source_list)

        val adapter = SourceListAdapter(::onSourceSelected)
        recycler.adapter = adapter
        viewModel.data.observe(this, Observer { newData ->
            adapter.setItems(newData)
        })

        toolbar.setOnClickListener {
            viewModel.sortSourceList()
        }
    }

    private fun onSourceSelected(source: SourceItem) {
        (requireActivity() as MainActivity).showNews(source)
    }

    companion object {
        fun newInstance(): SourceListFragment {
            val arguments = Bundle()
            val fragment = SourceListFragment()
            fragment.arguments = arguments
            return fragment
        }
    }
}
