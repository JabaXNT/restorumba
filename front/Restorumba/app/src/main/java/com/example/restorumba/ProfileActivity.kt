package com.example.restorumba

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val name: String? = intent.getStringExtra("name")
        val phone: String? = intent.getStringExtra("phone")
        val password: String? = intent.getStringExtra("password")

        val userName: TextView = findViewById(R.id.name)
        val userPhone: TextView = findViewById(R.id.phone)

        if (name != null) {
            userName.text = name
        }
        if (phone != null) {
            userPhone.text = phone
        }

        val linkToOrder: ImageButton = findViewById(R.id.link_to_order)
        val linkToEdit: ImageButton = findViewById(R.id.link_to_edit)
        val linkToOrderHistory: ConstraintLayout = findViewById(R.id.link_to_order_history)
        val linkToSettings: ConstraintLayout = findViewById(R.id.link_to_settings)
        val linkToPayingOptions: ConstraintLayout = findViewById(R.id.link_to_paying_options)
        val logoutButton: Button = findViewById(R.id.logout_button)

        linkToOrder.setOnClickListener {
            val intent = Intent(this, OrderActivity::class.java)
            startActivity(intent)
            finish()
        }

        linkToEdit.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("phone", phone)
            intent.putExtra("password", password)
            startActivity(intent)
            finish()
        }

        linkToOrderHistory.setOnClickListener {
            val intent = Intent(this, OrderHistoryActivity::class.java)
            startActivity(intent)
            finish()
        }

        linkToSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            finish()
        }

        linkToPayingOptions.setOnClickListener {
            val intent = Intent(this, PayingOptionsActivity::class.java)
            startActivity(intent)
            finish()
        }

        logoutButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}