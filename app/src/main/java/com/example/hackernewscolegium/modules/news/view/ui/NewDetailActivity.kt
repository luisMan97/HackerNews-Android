package com.example.hackernewscolegium.modules.news.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.example.hackernewscolegium.R
import kotlinx.android.synthetic.main.activity_new_detail.*

class NewDetailActivity : AppCompatActivity() {

    private lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_detail)
        url = intent.getStringExtra("URL") as String
        setupWebView()
    }

    private fun setupWebView() {
        wvNewDetail.webChromeClient = object  : WebChromeClient() {

        }

        wvNewDetail.webViewClient = object  : WebViewClient() {

        }

        val settings = wvNewDetail.settings
        settings.javaScriptEnabled = true
        wvNewDetail.loadUrl(url)
    }
}
