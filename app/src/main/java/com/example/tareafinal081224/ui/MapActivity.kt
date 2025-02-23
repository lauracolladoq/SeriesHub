package com.example.tareafinal081224.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.example.tareafinal081224.BaseActivity
import com.example.tareafinal081224.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : BaseActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private val LOCATION_CODE = 1000
    private val locationPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
                ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {
                manageLocation()
            } else {
                Toast.makeText(this, "The user denied the permissions.", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_map)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        startFragment()
    }

    private fun startFragment() {
        val fragment = SupportMapFragment()
        fragment.getMapAsync(this)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fm_maps, fragment)
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0
        map.uiSettings.isZoomControlsEnabled = true
        setPlaceholder(LatLng(36.86240231503707, -2.4347452730147663))
        manageLocation()
    }

    private fun manageLocation() {
        // :: para saber si la variable está inicializada y evitar errores de NullPointerException
        if (!::map.isInitialized) return
        // Comprobar permisos
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
            map.uiSettings.isMyLocationButtonEnabled = true
        } else {
            askPermissions()
        }
    }

    private fun askPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) ||
            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            showExplanation()
        } else {
            selectPermissions()
        }
    }

    private fun selectPermissions() {
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun showExplanation() {
        AlertDialog.Builder(this)
            .setTitle("Location permissions")
            .setMessage("You need to allow location permissions to use this app.")
            .setPositiveButton("Accept") { dialog, _ ->
                startActivity(Intent(Settings.ACTION_APPLICATION_SETTINGS))
            }
            .setCancelable(false)
            .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
            .create()
            .dismiss()
    }

    private fun setPlaceholder(coordenadas: LatLng) {
        val marker = MarkerOptions().position(coordenadas).title("Yelmo Cines")
        map.addMarker(marker)
        showAnimation(coordenadas, 15f)
    }

    private fun showAnimation(coordenadas: LatLng, zoom: Float) {
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordenadas, zoom),
            5000,
            null
        )
    }

    override fun onRestart() {
        super.onRestart()
        manageLocation()
    }

}