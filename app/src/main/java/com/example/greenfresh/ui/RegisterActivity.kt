package com.example.greenfresh.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.greenfresh.R
import com.example.greenfresh.ValidationUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class RegisterActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerButton: Button

    // Firebase Auth instance
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

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

        // Validate input using ValidationUtils
        if (!validateAllInputs()) {
            return
        }

        // Register user with Firebase
        registerWithFirebase(email, password)
    }

    private fun validateAllInputs(): Boolean {
        val isEmailValid = ValidationUtils.validateEmail(emailEditText)
        val isPasswordValid = ValidationUtils.validatePassword(passwordEditText)
        val isConfirmPasswordValid = ValidationUtils.validateConfirmPassword(
            passwordEditText,
            confirmPasswordEditText
        )

        return isEmailValid && isPasswordValid && isConfirmPasswordValid
    }

    private fun registerWithFirebase(email: String, password: String) {
        setLoadingState(true)

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                setLoadingState(false)

                if (task.isSuccessful) {
                    // Registration successful
                    val user = auth.currentUser
                    Toast.makeText(
                        this,
                        "Pendaftaran berhasil! Selamat datang ${user?.email}",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Send email verification (optional)
                    sendEmailVerification()

                    // Navigate to login or main activity
                    navigateToLogin(email)

                } else {
                    // Registration failed
                    handleRegistrationError(task.exception)
                }
            }
    }

    private fun sendEmailVerification() {
        auth.currentUser?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Email verifikasi telah dikirim ke ${auth.currentUser?.email}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun handleRegistrationError(exception: Exception?) {
        val errorMessage = when (exception) {
            is FirebaseAuthWeakPasswordException ->
                "Password terlalu lemah. ${exception.reason}"

            is FirebaseAuthInvalidCredentialsException ->
                "Format email tidak valid"

            is FirebaseAuthUserCollisionException ->
                "Email sudah terdaftar. Silakan gunakan email lain atau login"

            else ->
                "Pendaftaran gagal: ${exception?.message ?: "Terjadi kesalahan"}"
        }

        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()

        if (exception is FirebaseAuthUserCollisionException) {
            ValidationUtils.setError(emailEditText, "Email sudah terdaftar")
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        registerButton.isEnabled = !isLoading
        registerButton.text = if (isLoading) "Mendaftar..." else "Register"
        emailEditText.isEnabled = !isLoading
        passwordEditText.isEnabled = !isLoading
        confirmPasswordEditText.isEnabled = !isLoading
    }

    private fun navigateToLogin(email: String) {
        val intent = Intent(this, LoginFormActivity::class.java)
        intent.putExtra("registered_email", email)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}