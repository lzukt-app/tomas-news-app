package com.example.tomas_news_app.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tomas_news_app.BuildConfig
import com.example.tomas_news_app.R
import kotlinx.android.synthetic.main.fragment_about.*

class AboutFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = "about fragment title"

        version.text = "${getString(R.string.version)} ${BuildConfig.VERSION_NAME}"
        other.text = "${getString(R.string.developer)} ${getString(
            R.string.company
        )}"

    }

    companion object {
        fun newInstance(): AboutFragment {
            val arguments = Bundle()
            val fragment = AboutFragment()
            fragment.arguments = arguments
            return fragment
        }
    }

}

