package com.example.tomas_news_app

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_tutorial.*

class TutorialItemFragment : Fragment() {

    lateinit var tutorialText: String
    var page: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tutorialText = arguments!!.getString(KEY_LABEL) ?: ""
        page = arguments!!.getInt(KEY_PAGE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tutorial, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView.text = "$tutorialText $page"
        btnNext.setOnClickListener {
            (requireActivity() as TutorialActivity).showNext(page)
        }
    }

    companion object {
        private const val KEY_LABEL = "tutorial_text"
        private const val KEY_PAGE = "page"

        fun newInstance(tutorialText: String, page: Int): TutorialItemFragment {
            val arguments = Bundle()
            arguments.putString(KEY_LABEL, tutorialText)
            arguments.putInt(KEY_PAGE, page)
            val fragment = TutorialItemFragment()
            fragment.arguments = arguments
            return fragment
        }
    }
}
