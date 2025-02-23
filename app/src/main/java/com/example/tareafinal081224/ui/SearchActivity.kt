package com.example.tareafinal081224.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tareafinal081224.BaseActivity
import com.example.tareafinal081224.R
import com.example.tareafinal081224.databinding.ActivitySearchBinding
import java.util.Locale

class SearchActivity : BaseActivity() {
    private lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        startWebView()
        setlisteners()
    }

    private fun setlisteners() {
        binding.swipe.setOnRefreshListener {
            binding.webView.reload()
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                val search = p0.toString().trim().lowercase(Locale.ROOT)
                if (android.util.Patterns.WEB_URL.matcher(search).matches()) {
                    binding.webView.loadUrl(search)
                    return true
                }
                val url = "https://www.google.es/search?q=${search}"
                binding.webView.loadUrl(url)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })

        binding.nv.setNavigationItemSelectedListener {
            comprobarItem(it)
        }
    }

    private fun startWebView() {
        binding.webView.webViewClient = object : WebViewClient() {
            // Sobreescribir métodos para controlar el swiperfresh layout
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.swipe.isRefreshing = true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.swipe.isRefreshing = false
            }
        }

        binding.webView.webChromeClient = object : WebChromeClient() {

        }

        // Activar JavaScript
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl("https://www.themoviedb.org/")
    }

    // Controlar el botón de atrás para volver a la página anterior tantas veces como sea posible
    override fun onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

}