package com.example.salonlive

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.salonlive.fragment.ChatFragment
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class MainActivity : AppCompatActivity() {

    private var videoWebView: WebView? = null
    private var courseWebView: WebView? = null

    private lateinit var client: OkHttpClient
    private lateinit var webSocket: WebSocket
    private lateinit var chatFragment: ChatFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initializeWebSocket()
        initView()
        webViewSetting()

        //

        chatFragment = supportFragmentManager.findFragmentById(R.id.my_fragment) as ChatFragment
    }

    private fun initializeWebSocket() {
        client = OkHttpClient()
        val request = Request.Builder()
            .url("ws://10.8.251.177:30873/chat/101")
            .build()
        val listener = EchoWebSocketListener()
        webSocket = client.newWebSocket(request, listener)

        // Get reference to ChatFragment
    }

    private fun initView() {
        videoWebView = findViewById(R.id.video_web_view)
        courseWebView = findViewById(R.id.course_web_view)
    }

    fun webViewSetting() {
        courseWebViewSetting()
        videoWebViewSetting()
    }

    //直播视频webView设置
    fun videoWebViewSetting() {
        // 启用JavaScript支持
        val settings = videoWebView?.settings
        settings?.javaScriptEnabled = true
        // 确保链接在WebView内打开而不是浏览器
        videoWebView?.webViewClient = WebViewClient()
        // 加载指定的URL
        videoWebView?.loadUrl("https://blog.csdn.net/sadkhkuhkjasga/article/details/106902496")
    }

    //课件webView设置
    fun courseWebViewSetting() {
        // 启用JavaScript支持
        val settings = courseWebView?.settings
        settings?.javaScriptEnabled = true
        // 确保链接在WebView内打开而不是浏览器
        courseWebView?.webViewClient = WebViewClient()
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

    override fun onDestroy() {
        super.onDestroy()
        webSocket.close(1000, "Goodbye!")
    }

    private inner class EchoWebSocketListener : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            webSocket.send("欢迎同学们来到直播间")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            runOnUiThread {
                // Handle the received message
                if (text != """{"c":4,"si":"server","sn":"server","t":"chat_count"}""") {
                    // Handle the received message
                    println("Receiving : $text")
                    chatFragment.addMessage(text)
                }
            }
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            runOnUiThread {
                // Handle the received bytes
                println("Receiving bytes : ${bytes.hex()}")
            }
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            webSocket.close(1000, null)
            runOnUiThread {
                println("Closing : $code / $reason")
            }
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            runOnUiThread {
                println("Error : ${t.message}")
            }
        }
    }
}
