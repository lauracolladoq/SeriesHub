package com.example.tareafinal081224.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tareafinal081224.BaseActivity
import com.example.tareafinal081224.R
import com.example.tareafinal081224.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    // Multimedia
    private lateinit var mediaController: MediaController
    var videoRute = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        videoRute = "android.resource://${packageName}/${R.raw.welcome}"


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = Firebase.auth
        binding.tvEmail.text = auth.currentUser?.email.toString()

        mediaController = MediaController(this)
        if (savedInstanceState != null) {
            videoRute = savedInstanceState.getString("VIDEO_RUTE", "")
            playVideo()
        }

        setListeners()
    }

    private fun playVideo() {
        var uri = Uri.parse(videoRute)
        try {
            binding.videoView.setVideoURI(uri)
            binding.videoView.setMediaController(mediaController)
            mediaController.setAnchorView(binding.videoView)
            binding.videoView.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setListeners() {
        binding.btnLogout.setOnClickListener {
            auth.signOut()
            finish()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.nv.setNavigationItemSelectedListener {
            checkMenuItem(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("VIDEO_RUTE", videoRute)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        videoRute = savedInstanceState.getString("VIDEO_RUTE", "")
    }

    override fun onPause() {
        super.onPause()
        if (binding.videoView.isPlaying) {
            binding.videoView.pause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (videoRute.isNotEmpty()) {
            playVideo()
        }
    }


}