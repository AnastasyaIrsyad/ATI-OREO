package com.example.ati_oreo.palmventory

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ati_oreo.R
import com.example.ati_oreo.databinding.ActivityDashboardBinding

// Halaman Dashboard (halaman utama setelah login, TANPA toolbar)
class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Padding bawah = 0: jarak bawah sudah diatur BottomNavigationView sendiri,
            // supaya ikon menu tidak terlalu jauh dari bawah layar
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // Buka SharedPreferences "user_pref"
        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)

        // Nama akun yang sedang login (disimpan saat login)
        val nama = sharedPref.getString("nama", null)
            ?: (sharedPref.getString("username", "Tasya") ?: "Tasya")
                .substringBefore("@").replaceFirstChar { it.uppercase() }
        binding.tvNama.text = nama // teks nama TIDAK bisa diklik

        // Klik foto profil -> halaman Profil
        binding.ivFoto.setOnClickListener {
            startActivity(Intent(this, ProfilActivity::class.java))
        }

        // Bottom navigation: hanya Beranda yang aktif, menu lain (termasuk Akun) belum dibuat
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_beranda -> true
                else -> {
                    Toast.makeText(this, "Fitur ${item.title} belum dibuat", Toast.LENGTH_SHORT).show()
                    false // tetap di Beranda
                }
            }
        }

        binding.btnLihatStok.setOnClickListener {
            Toast.makeText(this, "Halaman stok barang menyusul", Toast.LENGTH_SHORT).show()
        }
    }
}
