package com.example.zamburecetas.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.zamburecetas.databinding.FragmentResetPasswordBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class ResetPassword : Fragment() {

    private var _binding: FragmentResetPasswordBinding? = null
    private val binding get() = _binding!!
    private val auth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        setupValidation()
        setupClickListeners()
        return binding.root
    }

    private fun setupValidation() {
        binding.btnSend.isEnabled = false
        binding.emailTiet.addTextChangedListener {
            binding.btnSend.isEnabled = it.toString().trim().isNotBlank()
        }
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSend.setOnClickListener {
            val email = binding.emailTiet.text.toString().trim()
            sendResetEmail(email)
        }
    }

    private fun sendResetEmail(email: String) {
        binding.btnSend.isEnabled = false
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                Snackbar.make(
                    binding.root,
                    "Enlace enviado a $email, revisa tu correo",
                    Snackbar.LENGTH_LONG
                ).show()
                findNavController().navigateUp()
            }
            .addOnFailureListener { e ->
                binding.btnSend.isEnabled = true
                Snackbar.make(
                    binding.root,
                    "Error: ${e.localizedMessage}",
                    Snackbar.LENGTH_LONG
                ).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}