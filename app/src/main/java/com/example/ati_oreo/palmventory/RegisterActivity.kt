package com.example.ati_oreo.palmventory

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ati_oreo.R
import com.example.ati_oreo.databinding.ActivityRegisterBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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

        // Email dari Register With Gmail: terisi otomatis dan tidak bisa diubah
        val emailGmail = intent.getStringExtra("email")
        if (emailGmail != null) {
            binding.etEmail.setText(emailGmail)
            binding.etEmail.isEnabled = false
        }

        binding.btnBuatAkun.setOnClickListener {
            val nama = binding.etNama.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            // Validasi
            if (nama.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                tampilkanError("Semua field wajib diisi")
            } else if (password.length < 6) {
                tampilkanError("Password minimal 6 karakter")
            } else if (username.contains(" ")) {
                tampilkanError("Username tidak boleh mengandung spasi")
            } else {
                // Lolos validasi: simpan seluruh data registrasi ke SharedPreferences
                val akunPref = getSharedPreferences("akun_pref", MODE_PRIVATE)
                val editor = akunPref.edit()
                editor.putString("nama", nama)
                editor.putString("email", email)
                editor.putString("username", username)
                editor.putString("password", password)
                editor.apply()

                // Tampilkan halaman informasi "Registrasi Berhasil"
                val intent = Intent(this, RegistrasiBerhasilActivity::class.java)
                intent.putExtra("nama", nama)
                startActivity(intent)
                finish()
            }
        }

        binding.tvMasuk.setOnClickListener { finish() }
    }

    private fun tampilkanError(pesan: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Registrasi Gagal")
            .setMessage(pesan)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
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