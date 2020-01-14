package com.example.tomas_news_app.source

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tomas_news_app.MainActivity
import com.example.tomas_news_app.R
import kotlinx.android.synthetic.main.fragment_source_list.*

class SourceListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        val adapter =
            SourceListAdapter(::onSourceSelected)
        adapter.setItems(
            listOf(
                SourceItem(
                    "title 1",
                    "description"
                ),
                SourceItem(
                    "title 2",
                    "description"
                ),
                SourceItem(
                    "title 3",
                    "description"
                ),
                SourceItem(
                    "title 4",
                    "description"
                ),
                SourceItem(
                    "title 5",
                    "description"
                )
            )
        )
        recycler.adapter = adapter
    }

    private fun onSourceSelected(source: SourceItem) {
        (requireActivity() as MainActivity).showNews(source)
    }

    companion object {
        fun newInstance() =
            SourceListFragment()
    }
}
