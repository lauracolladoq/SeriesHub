package com.example.tareafinal081224

import android.content.Intent
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.tareafinal081224.ui.ExplorerActivity
import com.example.tareafinal081224.ui.GroupChatActivity
import com.example.tareafinal081224.ui.MainActivity
import com.example.tareafinal081224.ui.MapActivity
import com.example.tareafinal081224.ui.ReviewActivity
import com.example.tareafinal081224.ui.SearchActivity
import com.example.tareafinal081224.ui.SpinActivity

open class BaseActivity : AppCompatActivity() {
    fun checkMenuItem(itemId: MenuItem): Boolean {
        when (itemId.itemId) {
            R.id.item_profile -> {
                launchActivity(MainActivity::class.java)
            }

            R.id.item_explorer -> {
                launchActivity(ExplorerActivity::class.java)
            }

            R.id.item_maps -> {
                launchActivity(MapActivity::class.java)
            }

            R.id.item_search -> {
                launchActivity(SearchActivity::class.java)
            }

            R.id.item_reviews -> {
                launchActivity(ReviewActivity::class.java)
            }

            R.id.item_exit -> {
                finishAffinity()
            }

            R.id.item_game -> {
                launchActivity(SpinActivity::class.java)
            }

            R.id.item_group_chat -> {
                launchActivity(GroupChatActivity::class.java)
            }

            else -> return false
        }
        return true
    }

    private fun launchActivity(java: Class<*>) {
        val intent = Intent(this, java)
        startActivity(intent)
    }
}
