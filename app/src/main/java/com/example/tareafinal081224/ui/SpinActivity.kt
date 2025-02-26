package com.example.tareafinal081224.ui

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tareafinal081224.BaseActivity
import com.example.tareafinal081224.R
import com.example.tareafinal081224.databinding.ActivitySpinBinding
import com.example.tareafinal081224.ui.adapters.SeriesViewModel
import com.squareup.picasso.Picasso
import kotlin.math.sqrt

class SpinActivity : BaseActivity(), SensorEventListener {
    private lateinit var binding: ActivitySpinBinding

    // MVVM
    private val serieViewModel: SeriesViewModel by viewModels()

    // Sensor
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null

    // Tiempo desde la última vez que se detectó un cambio en el sensor
    private var lastUpdate: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySpinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Cargo las series populares
        serieViewModel.getPopulares()

        // Inicializar el sensor
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    override fun onResume() {
        super.onResume()
        // Registrar el listener del sensor
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        // Desregistrar el listener del sensor
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            // Calcular la aceleración
            val acceleration = sqrt((x * x + y * y + z * z).toDouble())

            val currentTime = System.currentTimeMillis()

            if (acceleration > 12 && currentTime - lastUpdate > 2000) { // 2 segundos de enfriamiento
                lastUpdate = currentTime
                val serie = serieViewModel.getRandomSerie()
                binding.tvSerieName.text = serie?.original_name
                Picasso.get().load("https://image.tmdb.org/t/p/w500${serie?.poster_path}")
                    .into(binding.ivPoster)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

}