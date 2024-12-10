package com.example.salonlive

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private var videoWebView: WebView? = null
    private var courseWebView: WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initView()
        webViewSetting()
    }

    private fun initView() {
        videoWebView = findViewById(R.id.video_web_view);
        courseWebView = findViewById(R.id.course_web_view);
    }

    fun webViewSetting() {
        courseWebViewSetting()
        videoWebViewSetting()
    }
    //直播视频webView设置
    fun videoWebViewSetting() {
        // 启用JavaScript支持
        val settings = videoWebView?.getSettings()
        settings?.javaScriptEnabled = true
        // 确保链接在WebView内打开而不是浏览器
        videoWebView?.setWebViewClient(WebViewClient())
        // 加载指定的URL
        videoWebView?.loadUrl("http://class.jiaoyanyun.com/fe2/main.html?stream_name=fe2")
    }

    //课件webView设置
    fun courseWebViewSetting() {
        // 启用JavaScript支持
        val settings = courseWebView?.getSettings()
        settings?.javaScriptEnabled = true
        // 确保链接在WebView内打开而不是浏览器
        courseWebView?.setWebViewClient(WebViewClient())
        // 加载指定的URL
        courseWebView?.loadUrl("https://www.xiaohongshu.com")
    }

    override fun onBackPressed() {
        if (videoWebView!!.canGoBack()) {
            videoWebView!!.goBack()
        } else {
            super.onBackPressed()
        }
    }
}