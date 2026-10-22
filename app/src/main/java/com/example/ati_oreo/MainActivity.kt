package com.example.ati_oreo

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ati_oreo.databinding.ActivityMainBinding
import com.example.ati_oreo.palmventory.RegisterActivity
import com.example.ati_oreo.palmventory.WebViewActivity
import com.example.ati_oreo.tugas_snack.SnackActivity

// Halaman Login Palmventory (tanpa toolbar). Dibuka dari Splash Screen jika belum login
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // Setelah daftar berhasil, kembali ke Login dan email langsung terisi
    private val registerLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                binding.etEmail.setText(result.data?.getStringExtra("email") ?: "")
                binding.etPassword.setText("")
                binding.etPassword.requestFocus()
            }
        }

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

        // Daftar akun yang dibuat di halaman Buat Akun
        val akunPref = getSharedPreferences("akun_pref", MODE_PRIVATE)

        // Tombol Masuk: cek email + password dengan akun yang sudah didaftarkan
        binding.btnMasuk.setOnClickListener {
            val email = binding.etEmail.text.toString().trim().lowercase()
            val password = binding.etPassword.text.toString().trim()

            val belumAdaAkun = akunPref.all.keys.none { it.startsWith("sandi_") }
            val sandiTersimpan = akunPref.getString("sandi_$email", null)

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan kata sandi wajib diisi", Toast.LENGTH_SHORT).show()
            } else if (belumAdaAkun) {
                tampilkanDialogGagal("Akun belum tersedia. Silakan daftar terlebih dahulu")
            } else if (sandiTersimpan == null || sandiTersimpan != password) {
                tampilkanDialogGagal("Email atau password yang dimasukkan salah")
            } else {
                // Simpan status login dan nama akun
                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", true)
                editor.putString("username", email)
                editor.putString("nama", akunPref.getString("nama_$email", email))
                editor.apply()

                startActivity(Intent(this, BaseActivity::class.java))
                finish()
            }
        }

        // Daftar -> Buat Akun
        binding.tvDaftar.setOnClickListener {
            registerLauncher.launch(Intent(this, RegisterActivity::class.java))
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

    private fun tampilkanDialogGagal(pesan: String) {
        AlertDialog.Builder(this)
            .setTitle("Login Gagal")
            .setMessage(pesan)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
