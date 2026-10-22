package com.example.ati_oreo.riwayat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ati_oreo.databinding.FragmentRiwayatBinding

class RiwayatFragment : Fragment() {
    private var _binding: FragmentRiwayatBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
