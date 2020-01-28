package com.example.tomasNewsApp.source

import android.os.Bundle
import android.os.Handler
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
import kotlinx.android.synthetic.main.fragment_source_list.*
import kotlin.concurrent.thread

class SourceListFragment : Fragment() {
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_source_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.layoutManager = LinearLayoutManager(requireContext())

        (requireActivity() as MainActivity).setSupportActionBar(toolbar)
        (requireActivity() as MainActivity).actionBar?.setDisplayHomeAsUpEnabled(true)
        (requireActivity() as MainActivity).title = getString(R.string.toolbar_title_source_list)

        val adapter = SourceListAdapter(::onSourceSelected)
        recycler.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner, Observer { newData ->
            adapter.setItems(newData)
        })

        toolbar.setOnClickListener {
            viewModel.sortID.value = viewModel.sortid.value == false
            viewModel.sortSourceList()
        }

        val handler = Handler() // this line need to be executed on main thread
        thread {
            // io-thread
            handler.post {
                // main-thread
            }
        }

        swipe_refresh_source.setOnRefreshListener {
            Toast.makeText(context, "Refresh Source list", Toast.LENGTH_SHORT).show()
            swipe_refresh_source.isRefreshing = false
            viewModel.onCreate()
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
