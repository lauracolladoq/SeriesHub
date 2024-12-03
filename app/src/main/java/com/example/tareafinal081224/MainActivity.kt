package com.example.tareafinal081224

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tareafinal081224.databinding.ActivityMainBinding
import com.example.tareafinal081224.fragments.FragmentExplorer
import com.example.tareafinal081224.fragments.FragmentFavorites
import com.example.tareafinal081224.fragments.FragmentProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

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
        cargarFragment(FragmentProfile())

    }

    // NAVIGATION MENU -----------------------------------------------------------------------------
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navigation_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_exit -> {
                // Finalizar todas las actividades de la app
                finishAffinity()
            }

            R.id.item_profile -> {
                cargarFragment(FragmentProfile())
            }
            R.id.item_explorer -> {
                cargarFragment(FragmentExplorer())
            }

            R.id.item_favorites -> {
                cargarFragment(FragmentFavorites())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Cargar fragmentos pasados por parámetro
    private fun cargarFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fcv, fragment)
            commit()
        }
    }
}