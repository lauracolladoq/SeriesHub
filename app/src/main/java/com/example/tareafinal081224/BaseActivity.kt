package com.example.tareafinal081224

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.navigation_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_exit -> {
                finishAffinity()
            }

            R.id.item_profile -> {
                cargarActivity(MainActivity::class.java)
            }

            R.id.item_explorer -> {
                cargarActivity(ExplorerActivity::class.java)
            }

            R.id.item_reviews -> {
                cargarActivity(ReviewActivity::class.java)
            }

            R.id.item_search -> {
                cargarActivity(SearchActivity::class.java)
            }

            R.id.item_maps -> {
                cargarActivity(MapActivity::class.java)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun cargarActivity(java: Class<*>) {
        val intent = Intent(this, java)
        startActivity(intent)
    }
}