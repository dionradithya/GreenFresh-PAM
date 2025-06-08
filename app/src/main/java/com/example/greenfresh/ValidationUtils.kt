package com.example.greenfresh

import android.text.TextUtils
import android.util.Patterns
import android.widget.EditText

object ValidationUtils {

    /**
     * Validate email format
     */
    fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    /**
     * Validate password strength
     * - Minimum 6 characters
     * - Must contain letters and numbers
     */
    fun isValidPassword(password: String): Boolean {
        if (password.length < 6) return false

        val hasLetter = password.any { it.isLetter() }
        val hasDigit = password.any { it.isDigit() }

        return hasLetter && hasDigit
    }

    /**
     * Check if passwords match
     */
    fun doPasswordsMatch(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    /**
     * Set error message on EditText
     */
    fun setError(editText: EditText, message: String) {
        editText.error = message
        editText.requestFocus()
    }

    /**
     * Clear error from EditText
     */
    fun clearError(editText: EditText) {
        editText.error = null
    }

    /**
     * Validate email and show error if invalid
     */
    fun validateEmail(emailEditText: EditText): Boolean {
        val email = emailEditText.text.toString().trim()

        return when {
            TextUtils.isEmpty(email) -> {
                setError(emailEditText, "Email tidak boleh kosong")
                false
            }
            !isValidEmail(email) -> {
                setError(emailEditText, "Format email tidak valid")
                false
            }
            else -> {
                clearError(emailEditText)
                true
            }
        }
    }

    /**
     * Validate password and show error if invalid
     */
    fun validatePassword(passwordEditText: EditText): Boolean {
        val password = passwordEditText.text.toString().trim()

        return when {
            TextUtils.isEmpty(password) -> {
                setError(passwordEditText, "Password tidak boleh kosong")
                false
            }
            password.length < 6 -> {
                setError(passwordEditText, "Password minimal 6 karakter")
                false
            }
            !isValidPassword(password) -> {
                setError(passwordEditText, "Password harus mengandung huruf dan angka")
                false
            }
            else -> {
                clearError(passwordEditText)
                true
            }
        }
    }

    /**
     * Validate confirm password
     */
    fun validateConfirmPassword(
        passwordEditText: EditText,
        confirmPasswordEditText: EditText
    ): Boolean {
        val password = passwordEditText.text.toString().trim()
        val confirmPassword = confirmPasswordEditText.text.toString().trim()

        return when {
            TextUtils.isEmpty(confirmPassword) -> {
                setError(confirmPasswordEditText, "Konfirmasi password tidak boleh kosong")
                false
            }
            !doPasswordsMatch(password, confirmPassword) -> {
                setError(confirmPasswordEditText, "Password tidak cocok")
                false
            }
            else -> {
                clearError(confirmPasswordEditText)
                true
            }
        }
    }
}