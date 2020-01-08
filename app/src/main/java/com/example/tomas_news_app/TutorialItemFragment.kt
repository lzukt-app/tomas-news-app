package com.example.tomas_news_app

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_tutorial.*

class TutorialItemFragment : Fragment() {

    private var tutorialImage: Int = 0
    private lateinit var tutorialText: String
    var page: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tutorialImage = arguments!!.getInt(KEY_IMAGE)
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
        textView.text = "$tutorialText  \n page: $page"
        appCompatImageView.setImageResource(tutorialImage)
        btnNext.setOnClickListener {
            (requireActivity() as TutorialActivity).showNext(page)
        }
    }

    companion object {
        private const val KEY_IMAGE = "tutorial_image"
        private const val KEY_LABEL = "tutorial_text"
        private const val KEY_PAGE = "page"

        fun newInstance(tutorialImage: Int, tutorialText: String, page: Int): TutorialItemFragment {
            val arguments = Bundle()
            arguments.putInt(KEY_IMAGE, tutorialImage)
            arguments.putString(KEY_LABEL, tutorialText)
            arguments.putInt(KEY_PAGE, page)
            val fragment = TutorialItemFragment()
            fragment.arguments = arguments
            return fragment
        }
    }
}
