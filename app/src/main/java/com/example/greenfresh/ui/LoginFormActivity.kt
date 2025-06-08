package com.example.greenfresh.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.greenfresh.R

class LoginFormActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_form)

        // Initialize views
        initViews()

        // Set up click listeners
        setupClickListeners()

        // Check if email was passed from registration
        checkForRegisteredEmail()
    }

    private fun checkForRegisteredEmail() {
        val registeredEmail = intent.getStringExtra("registered_email")
        if (!registeredEmail.isNullOrEmpty()) {
            emailEditText.setText(registeredEmail)
            Toast.makeText(this, "Silakan login dengan akun yang baru didaftarkan", Toast.LENGTH_LONG).show()
        }
    }

    private fun initViews() {
        emailEditText = findViewById(R.id.et_email)
        passwordEditText = findViewById(R.id.et_password)
        loginButton = findViewById(R.id.btn_login)
    }

    private fun setupClickListeners() {
        loginButton.setOnClickListener {
            performLogin()
        }
    }

    private fun performLogin() {
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()

        // Validate input
        if (!validateInput(email, password)) {
            return
        }

        // TODO: Implement actual login logic here
        // For now, simulate login
        simulateLogin(email, password)
    }

    private fun validateInput(email: String, password: String): Boolean {
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

        return true
    }

    private fun simulateLogin(email: String, password: String) {
        loginButton.isEnabled = false
        loginButton.text = "Loading..."

        loginButton.postDelayed({
            loginButton.isEnabled = true
            loginButton.text = "Login"

            if (email.isNotEmpty() && password.isNotEmpty()) {
                Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show()

                // Navigate to HomeActivity and clear the back stack
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()

                Toast.makeText(this, "Selamat datang, $email", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Login gagal. Periksa email dan password Anda.", Toast.LENGTH_SHORT).show()
            }
        }, 1500)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // Optional: Add custom back button behavior
    }
}