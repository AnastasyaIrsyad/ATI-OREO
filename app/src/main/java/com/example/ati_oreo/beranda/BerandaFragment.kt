package com.example.ati_oreo.beranda

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dewakoding.androidchartjs.util.ChartType
import com.example.ati_oreo.R
import com.example.ati_oreo.databinding.FragmentBerandaBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.ati_oreo.palmventory.ProfilActivity

class BerandaFragment : Fragment() {
    private var _binding: FragmentBerandaBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Nama akun yang sedang login (disimpan saat login)
        val sharedPref = requireContext().getSharedPreferences("user_pref", MODE_PRIVATE)
        val nama = sharedPref.getString("nama", null)
            ?: (sharedPref.getString("username", "Tasya") ?: "Tasya")
                .substringBefore("@").replaceFirstChar { it.uppercase() }
        binding.tvNama.text = nama // teks nama TIDAK bisa diklik

        // Klik foto profil -> halaman Profil
        binding.ivFoto.setOnClickListener {
            startActivity(Intent(requireContext(), ProfilActivity::class.java))
        }

        // Lihat stok barang -> pindah ke menu Barang pada bottom navigation
        binding.btnLihatStok.setOnClickListener {
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_view)
                .selectedItemId = R.id.barang
        }
        // ===== DATA DUMMY (satu sumber untuk kartu dan chart) =====
        val namaBarang = listOf(
            "Pupuk NPK 01", "Pupuk NPK 02", "Pupuk Urea",
            "Pestisida Glamular", "Cangkul", "Palu", "Sarung Tangan"
        )
        val stokBarang = listOf(155, 5, 120, 4, 80, 106, 130)

        // Kartu ringkasan
        binding.tvTotalPengguna.text = namaBarang.size.toString()
        binding.tvTotalStok.text = stokBarang.sum().toString()

        // Chart
        binding.androidChart1.setChart(
            ChartType.PIE,
            namaBarang.toTypedArray(),
            stokBarang.toTypedArray(),
            "Stok barang (unit)"
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
