package com.example.ati_oreo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ati_oreo.databinding.ActivityMainBinding
import com.example.ati_oreo.palmventory.DashboardActivity
import com.example.ati_oreo.palmventory.RegisterActivity
import com.example.ati_oreo.palmventory.WebViewActivity
import com.example.ati_oreo.tugas_snack.SnackActivity

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

        // Buka SharedPreferences "user_pref"
        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)

        // Tombol Masuk: login berhasil jika Email = Kata sandi (sesuai materi: Username = Password)
        binding.btnMasuk.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val sandi = binding.etPassword.text.toString()

            if (email.isNotEmpty() && email == sandi) {
                // Simpan status login dan username
                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", true)
                editor.putString("username", email)
                editor.apply()

                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
            } else {
                AlertDialog.Builder(this)
                    .setTitle("Login Gagal")
                    .setMessage("Silahkan coba lagi")
                    .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                    .show()
            }
        }

        // Daftar -> Buat Akun
        binding.tvDaftar.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.tvLupa.setOnClickListener {
            Toast.makeText(this, "Fitur lupa kata sandi belum tersedia", Toast.LENGTH_SHORT).show()
        }

        // Tombol baru: Web Palmventory
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
