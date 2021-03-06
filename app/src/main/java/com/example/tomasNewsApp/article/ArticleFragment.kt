package com.example.tomasNewsApp.article

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.tomasNewsApp.R
import com.example.tomasNewsApp.main.MainActivity
import com.example.tomasNewsApp.news.NewsItem
import com.example.tomasNewsApp.utils.reFormatDate
import kotlinx.android.synthetic.main.activity_article.view.*
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.android.synthetic.main.fragment_article.view.*

class ArticleFragment : Fragment() {
    lateinit var viewModel: ArticleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val article = arguments!!.getParcelable<NewsItem>(KEY_ARTICLE)

        viewModel = ViewModelProviders.of(
            this,
            ArticleViewModelFactory(requireActivity().application, article)
        )
            .get(ArticleViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_article, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).setSupportActionBar(toolbar)
        (requireActivity() as MainActivity).actionBar?.setDisplayHomeAsUpEnabled(true)
        val article = arguments!!.getParcelable<NewsItem>(KEY_ARTICLE)
        (requireActivity() as MainActivity).title = article?.sourceId

        viewModel.data.observe(viewLifecycleOwner, Observer { newData ->
            Glide.with(view)
                .load((newData as NewsItem).urlToImage)
                .dontAnimate()
                .into(view.article_imageUrl)
            view.article_title.text = newData.title
            view.article_description.text = newData.description
            view.article_author.text = newData.author
            view.article_date.text = reFormatDate(newData.publishedAt, "yyyy-MM-dd")
            view.article_readFull.setOnClickListener {
                val uri: Uri =
                    Uri.parse(newData.url) // missing 'http://' will cause crashed
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
        })
    }

    companion object {
        const val KEY_ARTICLE = "articleObject"

        fun newInstance(
            article: NewsItem
        ): ArticleFragment {
            val arguments = Bundle()
            arguments.putParcelable(KEY_ARTICLE, article)
            val fragment = ArticleFragment()
            fragment.arguments = arguments
            return fragment
        }
    }
}
