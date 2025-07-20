package com.example.dualmusicapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioManager
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import com.spotify.sdk.android.auth.LoginActivity

class MainActivity : AppCompatActivity() {
    
    companion object {
        private const val TAG = "MainActivity"
        private const val CLIENT_ID = "c456142fb93a42929545aa059ddd0347"
        private const val REDIRECT_URI = "dualmusicapp://callback"
        private const val AUTH_TOKEN_REQUEST_CODE = 0x10
        private const val PERMISSION_REQUEST_CODE = 0x11
    }
    
    // UI Elements
    private lateinit var youtubeWebView: WebView
    private lateinit var youtubePlayButton: Button
    private lateinit var youtubePauseButton: Button
    private lateinit var youtubeVolumeSlider: SeekBar
    private lateinit var spotifyStatusText: TextView
    private lateinit var spotifyConnectButton: Button
    private lateinit var spotifyPlayButton: Button
    private lateinit var spotifyPauseButton: Button
    private lateinit var spotifyVolumeSlider: SeekBar
    private lateinit var playAllButton: Button
    private lateinit var pauseAllButton: Button
    private lateinit var stopAllButton: Button
    
    // Spotify
    private var isSpotifyConnected = false
    private var isSpotifyPlaying = false
    
    // YouTube
    private var isYouTubePlaying = false
    private val youtubeVideoId = "dQw4w9WgXcQ" // Rick Roll as default example
    
    // Audio Manager
    private lateinit var audioManager: AudioManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        initializeViews()
        setupAudioManager()
        setupYouTubeWebView()
        setupSpotify()
        setupVolumeControls()
        setupGlobalControls()
        checkPermissions()
    }
    
    private fun initializeViews() {
        youtubeWebView = findViewById(R.id.youtubeWebView)
        youtubePlayButton = findViewById(R.id.youtubePlayButton)
        youtubePauseButton = findViewById(R.id.youtubePauseButton)
        youtubeVolumeSlider = findViewById(R.id.youtubeVolumeSlider)
        spotifyStatusText = findViewById(R.id.spotifyStatusText)
        spotifyConnectButton = findViewById(R.id.spotifyConnectButton)
        spotifyPlayButton = findViewById(R.id.spotifyPlayButton)
        spotifyPauseButton = findViewById(R.id.spotifyPauseButton)
        spotifyVolumeSlider = findViewById(R.id.spotifyVolumeSlider)
        playAllButton = findViewById(R.id.playAllButton)
        pauseAllButton = findViewById(R.id.pauseAllButton)
        stopAllButton = findViewById(R.id.stopAllButton)
    }
    
    private fun setupAudioManager() {
        audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
    }
    
    private fun setupYouTubeWebView() {
        youtubeWebView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            mediaPlaybackRequiresUserGesture = false
        }
        
        youtubeWebView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Log.d(TAG, "YouTube WebView loaded")
            }
        }
        
        // Load a YouTube video (hardcoded example)
        val embedUrl = "https://www.youtube.com/embed/$youtubeVideoId?autoplay=0&controls=1&rel=0"
        youtubeWebView.loadUrl(embedUrl)
        
        youtubePlayButton.setOnClickListener {
            playYouTube()
        }
        
        youtubePauseButton.setOnClickListener {
            pauseYouTube()
        }
    }
    
    private fun setupSpotify() {
        spotifyConnectButton.setOnClickListener {
            connectToSpotify()
        }
        
        spotifyPlayButton.setOnClickListener {
            playSpotifyPodcast()
        }
        
        spotifyPauseButton.setOnClickListener {
            pauseSpotify()
        }
    }
    
    private fun setupVolumeControls() {
        youtubeVolumeSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Note: WebView volume control is limited, this is more for UI feedback
                Log.d(TAG, "YouTube volume: $progress")
            }
            
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        
        spotifyVolumeSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Note: Spotify volume control requires App Remote SDK
                Log.d(TAG, "Spotify volume: $progress")
            }
            
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }
    
    private fun setupGlobalControls() {
        playAllButton.setOnClickListener {
            playAll()
        }
        
        pauseAllButton.setOnClickListener {
            pauseAll()
        }
        
        stopAllButton.setOnClickListener {
            stopAll()
        }
    }
    
    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) 
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.INTERNET),
                PERMISSION_REQUEST_CODE
            )
        }
    }
    
    private fun connectToSpotify() {
        val builder = AuthorizationRequest.Builder(
            CLIENT_ID,
            AuthorizationResponse.Type.TOKEN,
            REDIRECT_URI
        )
        
        builder.setScopes(arrayOf("streaming", "user-read-playback-state", "user-modify-playback-state"))
        val request = builder.build()
        
        AuthorizationClient.openLoginActivity(this, AUTH_TOKEN_REQUEST_CODE, request)
    }
    
    private fun playYouTube() {
        // Execute JavaScript to play the video
        youtubeWebView.evaluateJavascript(
            "document.querySelector('iframe').contentWindow.postMessage('{\"event\":\"command\",\"func\":\"playVideo\",\"args\":\"\"}', '*');",
            null
        )
        isYouTubePlaying = true
        Toast.makeText(this, "YouTube playing", Toast.LENGTH_SHORT).show()
    }
    
    private fun pauseYouTube() {
        // Execute JavaScript to pause the video
        youtubeWebView.evaluateJavascript(
            "document.querySelector('iframe').contentWindow.postMessage('{\"event\":\"command\",\"func\":\"pauseVideo\",\"args\":\"\"}', '*');",
            null
        )
        isYouTubePlaying = false
        Toast.makeText(this, "YouTube paused", Toast.LENGTH_SHORT).show()
    }
    
    private fun playSpotifyPodcast() {
        if (!isSpotifyConnected) {
            Toast.makeText(this, "Please connect to Spotify first", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Note: Direct playback requires Spotify App Remote SDK
        // For now, we'll just simulate the action
        isSpotifyPlaying = true
        Toast.makeText(this, "Spotify podcast playing (simulated)", Toast.LENGTH_SHORT).show()
    }
    
    private fun pauseSpotify() {
        if (isSpotifyConnected) {
            isSpotifyPlaying = false
            Toast.makeText(this, "Spotify paused (simulated)", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun playAll() {
        playYouTube()
        playSpotifyPodcast()
    }
    
    private fun pauseAll() {
        pauseYouTube()
        pauseSpotify()
    }
    
    private fun stopAll() {
        pauseYouTube()
        pauseSpotify()
        // Reset WebView
        youtubeWebView.loadUrl("about:blank")
        val embedUrl = "https://www.youtube.com/embed/$youtubeVideoId?autoplay=0&controls=1&rel=0"
        youtubeWebView.loadUrl(embedUrl)
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        
        if (requestCode == AUTH_TOKEN_REQUEST_CODE) {
            val response = AuthorizationClient.getResponse(resultCode, data)
            when (response.type) {
                AuthorizationResponse.Type.TOKEN -> {
                    val accessToken = response.accessToken
                    Log.d(TAG, "Got access token: $accessToken")
                    // For now, just mark as connected
                    isSpotifyConnected = true
                    spotifyStatusText.text = "Connected to Spotify (Auth only)"
                    spotifyPlayButton.isEnabled = true
                    spotifyPauseButton.isEnabled = true
                    spotifyConnectButton.text = "Connected"
                    spotifyConnectButton.isEnabled = false
                    
                    Log.d(TAG, "Connected to Spotify (Auth only)")
                    Toast.makeText(this, "Connected to Spotify (Auth only)", Toast.LENGTH_SHORT).show()
                }
                AuthorizationResponse.Type.ERROR -> {
                    Log.e(TAG, "Auth error: ${response.error}")
                    Toast.makeText(this, "Spotify authentication failed", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Log.d(TAG, "Auth cancelled")
                }
            }
        }
    }
} 