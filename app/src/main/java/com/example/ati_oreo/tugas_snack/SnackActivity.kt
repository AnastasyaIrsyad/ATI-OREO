package com.example.ati_oreo.tugas_snack

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ati_oreo.R
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.ati_oreo.databinding.ActivitySnackBinding
import kotlin.jvm.java

class SnackActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySnackBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // setContentView(R.layout.activity_snack)
        binding = ActivitySnackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Toolbar + tombol kembali ke halaman sebelumnya (Login)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Tugas Snack"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        binding.btnSubmit.setOnClickListener {
            val intent = Intent(this, GiziActivity::class.java)
            startActivity(intent)
            val jumlah = binding.inputNama.text

            Toast.makeText(this, "Wah kamu makan $jumlah oreonya:)", Toast.LENGTH_SHORT).show()
        }
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
