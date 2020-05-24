package ibmro.ishqa.candidate.candidateItem

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import ibmro.ishqa.R
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        val url=intent.getStringExtra("CV")
        loadWebView()
    }
    private fun loadWebView() {
        val url=intent.getStringExtra("CV")
        webView.settings.loadsImagesAutomatically = true
        webView.settings.javaScriptEnabled = true
        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        //webView.loadUrl(url)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                view.loadUrl(url)
                return false
            }
        }
        webView.loadUrl(url)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
/*
package ibmro.ishqa.candidate

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ibmro.ishqa.R
import kotlinx.android.synthetic.main.activity_web_view.*
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient



class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        loadWebView()

    }

    private fun loadWebView() {
        val url=intent.getStringExtra("CV")
        webView.settings.loadsImagesAutomatically = true
        webView.settings.javaScriptEnabled = true
        webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        //webView.loadUrl(url)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                view.loadUrl(url)
                return false
            }
        }
        webView.loadUrl(url)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}

 */