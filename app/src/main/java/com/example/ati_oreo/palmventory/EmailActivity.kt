package com.example.ati_oreo.palmventory

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ati_oreo.R
import com.example.ati_oreo.databinding.ActivityEmailBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Register With Gmail"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        binding.btnLanjut.setOnClickListener {
            val email = binding.etEmail.text.toString().trim().lowercase()

            if (email.isEmpty()) {
                tampilkanError("Email tidak boleh kosong")
            } else if (!email.endsWith("@gmail.com") || email == "@gmail.com") {
                tampilkanError("Email harus menggunakan domain @gmail.com")
            } else {
                // Valid: ke halaman Registrasi, bawa email lewat Intent
                val intent = Intent(this, RegisterActivity::class.java)
                intent.putExtra("email", email)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun tampilkanError(pesan: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Email Tidak Valid")
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