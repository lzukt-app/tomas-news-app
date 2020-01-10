package com.example.tomas_news_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tomas_news_app.tutorial.TutorialActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this, TutorialActivity::class.java))

        recycler.layoutManager = LinearLayoutManager(this)

        val adapter = SourceListItemeAdapter()
        adapter.setItems(listOf(Source("title"), Source("title")))
        recycler.adapter = adapter
    }
}
