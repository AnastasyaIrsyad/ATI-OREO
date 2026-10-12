package com.example.ati_oreo

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.ati_oreo.databinding.ActivitySplashScreenBinding
import com.example.ati_oreo.palmventory.DashboardActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Halaman pertama saat aplikasi dibuka: tampil logo, lalu cek status login (isLogin)
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Buka SharedPreferences "user_pref"
        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)

        lifecycleScope.launch {
            delay(2000) // simulasi pengambilan data selama 2 detik

            // Cek isLogin: true -> Dashboard, false -> Login (MainActivity)
            val isLogin = sharedPref.getBoolean("isLogin", false)
            val intent = if (isLogin) {
                Intent(this@SplashScreenActivity, DashboardActivity::class.java)
            } else {
                Intent(this@SplashScreenActivity, MainActivity::class.java)
            }
            startActivity(intent)
            finish()
        }
    }
}
