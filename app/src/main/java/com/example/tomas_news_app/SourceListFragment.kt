package com.example.tomas_news_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_source_list.*

class SourceListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_source_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        val adapter = SourceListItemeAdapter()
        adapter.setItems(
            listOf(
                SourceActivity("title 1", "description"),
                SourceActivity("title 2", "description"),
                SourceActivity("title 3", "description"),
                SourceActivity("title 4", "description"),
                SourceActivity("title 5", "description")
            )
        )
        recycler.adapter = adapter
    }

    companion object {
        fun newInstance() = SourceListFragment()
    }
}
