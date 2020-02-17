package com.example.tomasNewsApp.main

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.core.net.ConnectivityManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.tomasNewsApp.R
import com.example.tomasNewsApp.about.AboutFragment
import com.example.tomasNewsApp.article.ArticleFragment
import com.example.tomasNewsApp.favorite.FavoriteFragment
import com.example.tomasNewsApp.news.NewsItem
import com.example.tomasNewsApp.news.NewsListFragment
import com.example.tomasNewsApp.source.SourceItem
import com.example.tomasNewsApp.source.SourceListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val LOCATION_PERMISSION = 23

class MainActivity : AppCompatActivity() {
    //    lateinit var disposable: Disposable

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val connectivityManager = getSystemService<ConnectivityManager>()!!
            val info =
                ConnectivityManagerCompat.getNetworkInfoFromBroadcast(connectivityManager, intent)
            if (info != null && info.isAvailable && info.isConnected) {
                Toast.makeText(this@MainActivity, "connected", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "disconnected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerReceiver(broadcastReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        LocalBroadcastManager.getInstance(this).registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                assert(intent.getStringExtra("extra") == "value")
            }
        }, IntentFilter("myAction"))

        LocalBroadcastManager.getInstance(this)
            .sendBroadcast(Intent("myAction").apply { putExtra("extra", "value") })

        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION
            )
        } else {
            Toast.makeText(this, "always denied", Toast.LENGTH_SHORT).show()
        }
        navView.setOnNavigationItemSelectedListener { onBottomNavigationEvent(it) }
//        startActivityForResult()
        if (savedInstanceState == null) {
            this.showSource(false)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "granted", Toast.LENGTH_SHORT).show()
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // oh come on
                    Toast.makeText(this, "denied", Toast.LENGTH_SHORT).show()
                    requestPermissions(
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        LOCATION_PERMISSION
                    )
                } else {
                    // explain how to get toi settings
                    Toast.makeText(this, "always denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun onBottomNavigationEvent(it: MenuItem): Boolean {
        return when (it.itemId) {
            R.id.bottom_navigation_source -> {
                this.showSource()
                true
            }
            R.id.bottom_navigation_favorite -> {
                this.showFavorite()
                true
            }
            else -> {
                this.showAbout()
                true
            }
        }
    }

    private fun showSource(addToBackStack: Boolean = true) {
        supportFragmentManager.beginTransaction().apply {
            if (addToBackStack) addToBackStack(null)
            replace(
                R.id.container,
                SourceListFragment.newInstance()
            )
        }.commit()
    }

    fun showNews(source: SourceItem) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container, NewsListFragment.newInstance(source))
            .commit()
    }

    fun showArticle(article: NewsItem) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(
                R.id.container, ArticleFragment.newInstance(article)
            )
            .commit()
    }

    fun showFavorite() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container, FavoriteFragment.newInstance())
            .commit()
    }

    private fun showAbout() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.container, AboutFragment.newInstance())
            .commit()
    }

    companion object {
        fun createIntent(context: Context) = Intent(context, MainActivity::class.java)
    }
}
