package com.example.tomasNewsApp.landing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.tomasNewsApp.main.MainActivity
import com.example.tomasNewsApp.main.MainViewModel
import com.example.tomasNewsApp.main.MainViewModelFactory
import com.example.tomasNewsApp.tutorial.TutorialActivity

class SplashActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(
            this,
            MainViewModelFactory(application)
        )
            .get(MainViewModel::class.java)

        viewModel.showTutorial.observe(this, Observer { shouldShowTutorial ->
            startActivity(MainActivity.createIntent(this))
            if (shouldShowTutorial) {
                startActivity(Intent(this, TutorialActivity::class.java))
            }
            finish()
        })
    }
}
