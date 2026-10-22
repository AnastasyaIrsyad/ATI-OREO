package com.example.ati_oreo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.ati_oreo.akun.AkunFragment
import com.example.ati_oreo.barang.BarangFragment
import com.example.ati_oreo.beranda.BerandaFragment
import com.example.ati_oreo.databinding.ActivityBaseBinding
import com.example.ati_oreo.laporan.LaporanFragment
import com.example.ati_oreo.riwayat.RiwayatFragment

// Halaman utama setelah login: wadah Fragment + BottomNavigation
class BaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBaseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // padding bawah 0: jarak bawah diatur BottomNavigationView sendiri
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // Fragment pertama yang tampil
        replaceFragment(BerandaFragment())

        // Klik menu bottom navigation -> ganti Fragment
        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.beranda -> {
                    replaceFragment(BerandaFragment())
                    true
                }
                R.id.barang -> {
                    replaceFragment(BarangFragment())
                    true
                }
                R.id.riwayat -> {
                    replaceFragment(RiwayatFragment())
                    true
                }
                R.id.laporan -> {
                    replaceFragment(LaporanFragment())
                    true
                }
                R.id.akun -> {
                    replaceFragment(AkunFragment())
                    true
                }

                else -> false // return false jika item tidak ada yang di klik
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            //.addToBackStack(null) -> dinonaktifkan agar saat back langsung keluar aplikasi
            .commit()
    }
}
