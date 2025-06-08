package com.example.greenfresh

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var registerLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        initViews()

        // Set up click listeners
        setupClickListeners()
    }

    private fun initViews() {
        loginButton = findViewById(R.id.btn_login)
        registerLink = findViewById(R.id.tv_register_link)
    }

    private fun setupClickListeners() {
        loginButton.setOnClickListener {
            // Handle login button click
            handleLogin()
        }

        registerLink.setOnClickListener {
            // Handle register link click
            navigateToRegister()
        }
    }

    private fun handleLogin() {
        // Navigate to login form activity
        val intent = Intent(this, LoginFormActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToRegister() {
        // Navigate to registration activity
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}