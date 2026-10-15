package com.example.ati_oreo.palmventory

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ati_oreo.R
import com.example.ati_oreo.databinding.ActivityRegisterBinding

// Halaman Buat Akun (toolbar + back ke Login)
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Buat Akun"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        binding.btnBuatAkun.setOnClickListener {
            val nama = binding.etNama.text.toString().trim()
            val email = binding.etEmail.text.toString().trim().lowercase()
            val sandi = binding.etPassword.text.toString().trim()
            val ulang = binding.etKonfirmasi.text.toString().trim()
            val akunPref = getSharedPreferences("akun_pref", MODE_PRIVATE)

            if (nama.isEmpty() || email.isEmpty() || sandi.isEmpty()) {
                Toast.makeText(this, "Semua kolom wajib diisi", Toast.LENGTH_SHORT).show()
            } else if (sandi != ulang) {
                Toast.makeText(this, "Konfirmasi kata sandi tidak sama", Toast.LENGTH_SHORT).show()
            } else if (akunPref.contains("sandi_$email")) {
                Toast.makeText(this, "Email sudah terdaftar, silakan masuk", Toast.LENGTH_SHORT).show()
            } else {
                // Simpan akun (per email) supaya bisa dipakai login di halaman Login
                val editor = akunPref.edit()
                editor.putString("sandi_$email", sandi)
                editor.putString("nama_$email", nama)
                editor.apply()

                Toast.makeText(this, "Akun $nama berhasil dibuat, silakan masuk", Toast.LENGTH_SHORT).show()

                // Otomatis kembali ke halaman Login dengan email yang baru didaftarkan
                setResult(RESULT_OK, Intent().putExtra("email", email))
                finish()
            }
        }

        binding.tvMasuk.setOnClickListener { finish() }
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
