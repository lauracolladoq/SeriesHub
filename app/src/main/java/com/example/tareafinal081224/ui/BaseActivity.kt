package com.example.tareafinal081224

import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.tareafinal081224.ui.ExplorerActivity
import com.example.tareafinal081224.ui.MainActivity
import com.example.tareafinal081224.ui.MapActivity
import com.example.tareafinal081224.ui.ReviewActivity
import com.example.tareafinal081224.ui.SearchActivity
import com.example.tareafinal081224.ui.SpinActivity

open class BaseActivity : AppCompatActivity() {
    fun checkMenuItem(itemId: MenuItem): Boolean {
        when (itemId.itemId) {
            R.id.item_profile -> {
                cargarActivity(MainActivity::class.java)
            }

            R.id.item_explorer -> {
                cargarActivity(ExplorerActivity::class.java)
            }

            R.id.item_maps -> {
                cargarActivity(MapActivity::class.java)
            }

            R.id.item_search -> {
                cargarActivity(SearchActivity::class.java)
            }

            R.id.item_reviews -> {
                cargarActivity(ReviewActivity::class.java)
            }

            R.id.item_exit -> {
                finishAffinity()
            }

            R.id.item_game -> {
                cargarActivity(SpinActivity::class.java)
            }

            else -> return false
        }
        return true
    }

    private fun cargarActivity(java: Class<*>) {
        val intent = Intent(this, java)
        startActivity(intent)
    }
}
