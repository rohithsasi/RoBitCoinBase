package com.example.robitcoin.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.robitcoin.R
import com.example.robitcoin.getString
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_webview.*
import kotlinx.android.synthetic.main.view_toolbar.*

class BlockChainWebViewActivity : AppCompatActivity() {

    private lateinit var state: WebViewState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        state = if (savedInstanceState == null) {
            intent.getParcelableExtra(WEB_VIEW_STATE) ?: WebViewState.WALLET
        } else {
            @Suppress("RemoveExplicitTypeArguments")
            savedInstanceState.getParcelable<WebViewState>(WEB_VIEW_STATE) as WebViewState
        }

        initAndLoadWebView(activity_web_view_holder, state)
        setSupportActionBar(bgf_toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)

            title =when (state) {
                Companion.WebViewState.WALLET ->  R.string.nav_drawer_wallet.getString()
                Companion.WebViewState.LEARNING -> R.string.nav_drawer_portal.getString()
                Companion.WebViewState.LOCK -> R.string.nav_drawer_lockbox.getString()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(WEB_VIEW_STATE, state)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initAndLoadWebView(webView: WebView, state: WebViewState) {
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.allowContentAccess = false
        webSettings.allowFileAccess = false
        webSettings.allowFileAccessFromFileURLs = false
        webSettings.allowUniversalAccessFromFileURLs = false

        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                Toast.makeText(
                    this@BlockChainWebViewActivity,
                    "No Internet. Please try again",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        webView.loadUrl(state.url)
    }

    companion object {
        const val WEB_VIEW_STATE = "web_view_state"
        fun navigateTo(activity: Context, webView: WebViewState) {
            activity.startActivity(Intent(activity, BlockChainWebViewActivity::class.java).apply {
                putExtra(WEB_VIEW_STATE, webView as Parcelable)
            })
        }

        @Parcelize
        enum class WebViewState(val url: String) : Parcelable {
            WALLET("https://www.blockchain.com/wallet"),
            LEARNING("https://www.blockchain.com/learning-portal/security"),
            LOCK("https://www.blockchain.com/lockbox")
        }
    }

}



