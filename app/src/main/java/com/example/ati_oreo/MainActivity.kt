package com.example.ati_oreo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ati_oreo.databinding.ActivityMainBinding
import com.example.ati_oreo.palmventory.EmailActivity
import com.example.ati_oreo.palmventory.RegisterActivity
import com.example.ati_oreo.palmventory.WebViewActivity
import com.example.ati_oreo.tugas_snack.SnackActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

// Halaman Login Palmventory (tanpa toolbar). Dibuka dari Splash Screen jika belum login
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // SharedPreferences status login
        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
        // SharedPreferences data registrasi
        val akunPref = getSharedPreferences("akun_pref", MODE_PRIVATE)

        binding.btnMasuk.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            // Kondisi 1: username == password
            val kondisi1 = username.isNotEmpty() && username == password

            // Kondisi 2: username dan password sesuai data registrasi yang tersimpan
            val usernameTersimpan = akunPref.getString("username", null)
            val passwordTersimpan = akunPref.getString("password", null)
            val kondisi2 = usernameTersimpan != null &&
                    username == usernameTersimpan &&
                    password == passwordTersimpan

            if (kondisi1 || kondisi2) {
                // Login berhasil: simpan status login
                val nama = if (kondisi2) akunPref.getString("nama", username) else username
                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", true)
                editor.putString("username", username)
                editor.putString("nama", nama)
                editor.apply()

                startActivity(Intent(this, BaseActivity::class.java))
                finish()
                MaterialAlertDialogBuilder(this)
                    .setTitle("Login Gagal")
                    .setMessage("Username atau password salah")
                    .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                    .show()
            }
        }

        // Daftar -> Buat Akun
        binding.tvDaftar.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        // Register With Gmail -> halaman input email
        binding.btnRegisterGmail.setOnClickListener {
            startActivity(Intent(this, EmailActivity::class.java))
        }

        binding.tvLupa.setOnClickListener {
            Toast.makeText(this, "Fitur lupa kata sandi belum tersedia", Toast.LENGTH_SHORT).show()
        }

        // Tombol Web Palmventory
        binding.btnWebPalm.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("judul", "Web Palmventory")
            intent.putExtra("url", "https://palm-mauve.vercel.app")
            startActivity(intent)
        }

        // Project Snack
        binding.btnSnack.setOnClickListener {
            startActivity(Intent(this, SnackActivity::class.java))
        }
    }
}