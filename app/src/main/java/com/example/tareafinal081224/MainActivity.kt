package com.example.tareafinal081224

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tareafinal081224.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    // ------------------------------ Autenticaciones ------------------------------
    private lateinit var auth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ------------------------------ Autenticaciones ------------------------------
        auth = Firebase.auth
        setListeners()
    }

    private fun setListeners() {
        // Botones del Login
        binding.btnReset.setOnClickListener {
            limpiar()
        }

        binding.btnLogin.setOnClickListener {
            login()
        }

        binding.btnRegister.setOnClickListener {
            registrarse()
        }
    }

    private fun registrarse() {
        if (!datosCorrectos()) return

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    login()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    private fun login() {
        if (!datosCorrectos()) return
        // Datos correctos -> logeamos al usuario
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    irActivityApp()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    private fun datosCorrectos(): Boolean {
        email = binding.etEmail.text.toString().trim()
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "A valid email address was expected"
            return false
        }

        password = binding.etPassword.text.toString().trim()
        if (password.length < 5) {
            binding.etPassword.error = "The password must be at least 5 characters long"
            return false
        }
        return true
    }

    private fun limpiar() {
        binding.etPassword.setText("")
        binding.etEmail.setText("")
    }

    // Lanza el App Activity
    private fun irActivityApp() {
        startActivity(Intent(this, AppActivity::class.java))
    }

    // Sobreescribir on Start para que si el usuario está logeado se salte el login
    override fun onStart() {
        super.onStart()
        val usuario = auth.currentUser
        if (usuario != null) irActivityApp()
    }
}