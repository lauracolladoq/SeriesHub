package com.example.tareafinal081224

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tareafinal081224.databinding.ActivityAppBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAppBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth
        binding.tvEmail.text = auth.currentUser?.email.toString()
        setListeners()
    }

    private fun setListeners() {
        binding.btnSalir.setOnClickListener {
            finishAffinity()
        }
        binding.btnCerrar.setOnClickListener {
            auth.signOut()
            finish()
        }
    }
}