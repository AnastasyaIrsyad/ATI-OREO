package com.example.ati_oreo.akun

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.ati_oreo.databinding.FragmentAkunBinding
import com.example.ati_oreo.MainActivity

class AkunFragment : Fragment() {
    private var _binding: FragmentAkunBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAkunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Akun"
        }

        // Data akun yang sedang login
        val sharedPref = requireContext().getSharedPreferences("user_pref", MODE_PRIVATE)
        binding.tvNama.text = sharedPref.getString("nama", "Pengguna")
        binding.tvEmail.text = sharedPref.getString("username", "-")
        binding.tvInfoNama.text = sharedPref.getString("nama", "-")
        binding.tvInfoEmail.text = sharedPref.getString("username", "-")

        // Logout: tampilkan konfirmasi AlertDialog, jika "Ya" hapus data lalu kembali ke Login
        binding.btnKeluar.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setPositiveButton("Ya") { dialog, _ ->
                    val editor = sharedPref.edit()
                    editor.clear()
                    editor.apply()
                    dialog.dismiss()
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                .setNegativeButton("Tidak") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
