package com.example.tareafinal081224.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tareafinal081224.R
import com.example.tareafinal081224.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val responseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val datos = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                try {
                    val cuenta = datos.getResult(ApiException::class.java)
                    if (cuenta != null) {
                        val credenciales = GoogleAuthProvider.getCredential(cuenta.idToken, null)
                        FirebaseAuth.getInstance().signInWithCredential(credenciales)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    irMainActivity()
                                }
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT)
                                    .show()
                            }
                    }
                } catch (e: ApiException) {
                    Log.d("API Error: >>>>>>>>>", e.message.toString())
                }
            }
            if (it.resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "The user canceled the operation", Toast.LENGTH_SHORT).show();
            }
        }

    private lateinit var auth: FirebaseAuth
    private var email = ""
    private var password = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = Firebase.auth
        setListeners()
    }

    private fun setListeners() {
        // BOTONES DEL LOGIN -----------------------------------------------------------------------
        binding.btnReset.setOnClickListener {
            limpiar()
        }

        binding.btnLogin.setOnClickListener {
            login()
        }

        binding.btnRegister.setOnClickListener {
            registrarse()
        }
        binding.btnGoogle.setOnClickListener {
            loginGoogle()
        }

    }

    // FUNCIONALIDAD DEL LOGIN -----------------------------------------------------------------------
    private fun loginGoogle() {
        val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_client_id))
            .requestEmail()
            .build()
        val googleClient = GoogleSignIn.getClient(this, googleConf)

        // Para que no haga login automático al cerrar sesión
        googleClient.signOut()

        responseLauncher.launch(googleClient.signInIntent)
    }

    // FUNCIONALIDAD DEL LOGIN -----------------------------------------------------------------------
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
                    irMainActivity()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    private fun datosCorrectos(): Boolean {
        email = binding.etEmail.text.toString().trim()
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.setError("A valid email address is expected");
            return false
        }

        password = binding.etPassword.text.toString().trim()
        if (password.length < 5) {
            binding.etPassword.setError("Password must be at least 5 characters long");
            return false
        }
        return true
    }

    private fun limpiar() {
        binding.etPassword.setText("")
        binding.etEmail.setText("")
    }

    // Lanza el App Activity
    private fun irMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    // Sobreescribir on Start para que si el usuario está logeado se salte el login
    override fun onStart() {
        super.onStart()
        val usuario = auth.currentUser
        if (usuario != null) irMainActivity()
    }
}
