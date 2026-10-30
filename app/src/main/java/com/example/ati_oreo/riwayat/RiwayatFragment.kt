package com.example.ati_oreo.riwayat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.ati_oreo.databinding.FragmentRiwayatBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class RiwayatFragment : Fragment() {
    private var _binding: FragmentRiwayatBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRiwayatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Riwayat"
        }

        binding.btnAjukan.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur ajukan barang belum dibuat", Toast.LENGTH_SHORT).show()
        }
        binding.btnPesan.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur buat pesanan belum dibuat", Toast.LENGTH_SHORT).show()
        }

        // Klik ikon kalender di kolom cari -> pilih tanggal -> tanggal masuk ke kolom
        binding.tilCari.setEndIconOnClickListener {
            tampilkanDatePicker { tanggal ->
                binding.etCari.setText(tanggal)
                binding.etCari.setSelection(tanggal.length)
            }
        }

        // Setiap isi kolom berubah (diketik atau dari kalender) -> saring kartu
        binding.etCari.doAfterTextChanged { teks ->
            cariRiwayat(teks.toString().trim())
        }
    }

    // Kalender; tanggal terpilih dikirim dalam format "21 Jul 2026" (sama dengan teks di kartu)
    private fun tampilkanDatePicker(onPilih: (String) -> Unit) {
        // Kalender dibuka di 21 Jul 2026 supaya mudah dites (ada datanya)
        val awal = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply {
            clear()
            set(2026, Calendar.JULY, 21)
        }.timeInMillis

        val picker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Pilih tanggal riwayat")
            .setSelection(awal)
            .build()

        picker.addOnPositiveButtonClickListener { millis ->
            val format = SimpleDateFormat("dd MMM yyyy", Locale("id", "ID"))
            format.timeZone = TimeZone.getTimeZone("UTC") // picker memakai waktu UTC
            onPilih(format.format(Date(millis)))
        }
        picker.show(parentFragmentManager, "datePicker")
    }

    // Tampilkan hanya kartu yang teksnya memuat kata kunci; kosong = tampilkan semua
    private fun cariRiwayat(keyword: String) {
        val container = binding.containerRiwayat
        var jumlah = 0

        for (i in 0 until container.childCount) {
            val kartu = container.getChildAt(i)
            if (kartu !is LinearLayout) continue

            val teks = kumpulkanTeks(kartu)
            val isKartu = teks.contains("RQ-") || teks.contains("PO-") // abaikan baris tombol
            if (!isKartu) continue

            if (keyword.isEmpty() || teks.contains(keyword, ignoreCase = true)) {
                kartu.visibility = View.VISIBLE
                jumlah++
            } else {
                kartu.visibility = View.GONE
            }
        }

        if (keyword.isEmpty()) {
            binding.tvHasilCari.visibility = View.GONE
        } else {
            binding.tvHasilCari.visibility = View.VISIBLE
            binding.tvHasilCari.text = if (jumlah > 0) {
                "Menampilkan $jumlah data untuk \"$keyword\""
            } else {
                "Tidak ada data untuk \"$keyword\""
            }
        }
    }

    // Gabungkan semua teks di dalam sebuah kartu (rekursif)
    private fun kumpulkanTeks(v: View): String {
        if (v is TextView) return v.text.toString() + " "
        if (v is ViewGroup) {
            return (0 until v.childCount).joinToString("") { kumpulkanTeks(v.getChildAt(it)) }
        }
        return ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}