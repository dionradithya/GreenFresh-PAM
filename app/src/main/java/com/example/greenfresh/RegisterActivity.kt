package com.example.greenfresh

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize views
        initViews()

        // Set up click listeners
        setupClickListeners()
    }

    private fun initViews() {
        emailEditText = findViewById(R.id.et_email)
        passwordEditText = findViewById(R.id.et_password)
        confirmPasswordEditText = findViewById(R.id.et_confirm_password)
        registerButton = findViewById(R.id.btn_register)
    }

    private fun setupClickListeners() {
        registerButton.setOnClickListener {
            performRegister()
        }
    }

    private fun performRegister() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val confirmPassword = confirmPasswordEditText.text.toString().trim()

        // Validate input
        if (!validateInput(email, password, confirmPassword)) {
            return
        }

        // TODO: Implement actual registration logic here
        // For now, simulate registration
        simulateRegister(email, password)
    }

    private fun validateInput(email: String, password: String, confirmPassword: String): Boolean {
        // Clear previous errors
        emailEditText.error = null
        passwordEditText.error = null
        confirmPasswordEditText.error = null

        // Check if email is empty
        if (TextUtils.isEmpty(email)) {
            emailEditText.error = "Email tidak boleh kosong"
            emailEditText.requestFocus()
            return false
        }

        // Check if email is valid
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = "Format email tidak valid"
            emailEditText.requestFocus()
            return false
        }

        // Check if password is empty
        if (TextUtils.isEmpty(password)) {
            passwordEditText.error = "Password tidak boleh kosong"
            passwordEditText.requestFocus()
            return false
        }

        // Check password length
        if (password.length < 6) {
            passwordEditText.error = "Password minimal 6 karakter"
            passwordEditText.requestFocus()
            return false
        }

        // Check if confirm password is empty
        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordEditText.error = "Konfirmasi password tidak boleh kosong"
            confirmPasswordEditText.requestFocus()
            return false
        }

        // Check if passwords match
        if (password != confirmPassword) {
            confirmPasswordEditText.error = "Password tidak cocok"
            confirmPasswordEditText.requestFocus()
            return false
        }

        // Check password strength (optional)
        if (!isPasswordStrong(password)) {
            passwordEditText.error = "Password harus mengandung huruf dan angka"
            passwordEditText.requestFocus()
            return false
        }

        return true
    }

    private fun isPasswordStrong(password: String): Boolean {
        // Check if password contains both letters and numbers
        val hasLetter = password.any { it.isLetter() }
        val hasDigit = password.any { it.isDigit() }
        return hasLetter && hasDigit
    }

    private fun simulateRegister(email: String, password: String) {
        // Show loading state
        registerButton.isEnabled = false
        registerButton.text = "Mendaftar..."

        // Simulate network call with delay
        registerButton.postDelayed({
            // Reset button state
            registerButton.isEnabled = true
            registerButton.text = "Register"

            // For demo purposes, always show success
            Toast.makeText(this, "Pendaftaran berhasil!", Toast.LENGTH_SHORT).show()

            // Navigate back to login or main activity
            val intent = Intent(this, LoginFormActivity::class.java)
            intent.putExtra("registered_email", email)
            startActivity(intent)
            finish()

        }, 2000) // 2 second delay to simulate network call
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // Optional: Add custom back button behavior
    }
}