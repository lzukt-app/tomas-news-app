package com.example.tomas_news_app

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_tutorial.*

class TutorialItemFragment : Fragment() {
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
        val config = arguments!!.getParcelable<TutorialScreenConfig>(KEY_CONFIG)!!
        textView.text = "${config.tutorialText}  \n page: ${config.page + 1}"
        btnNext.text = "${config.tutorialButton}"
        appCompatImageView.setImageResource(config.tutorialImage)
        btnNext.setOnClickListener {
            (requireActivity() as TutorialActivity).showNext(config.page +1)
        }
    }

    companion object {
        private const val KEY_CONFIG = "tutorial_config"

        fun newInstance(config: TutorialScreenConfig): TutorialItemFragment {
            val arguments = Bundle()
            arguments.putParcelable(KEY_CONFIG, config)
            val fragment = TutorialItemFragment()
            fragment.arguments = arguments
            return fragment
        }
    }
}
