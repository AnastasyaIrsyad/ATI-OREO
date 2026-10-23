package com.example.ati_oreo.palmventory

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ati_oreo.R
import com.example.ati_oreo.databinding.ActivityRegistrasiBerhasilBinding

class RegistrasiBerhasilActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrasiBerhasilBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegistrasiBerhasilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Registrasi Berhasil"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        val nama = intent.getStringExtra("nama") ?: ""
        binding.tvPesan.text = "Akun atas nama $nama berhasil dibuat.\nSilakan masuk dengan username dan password Anda."

        // Kembali ke halaman Login (yang masih ada di bawah tumpukan halaman)
        binding.btnKeLogin.setOnClickListener { finish() }
    }

    // Tombol back pada toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}