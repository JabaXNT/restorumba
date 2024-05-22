package com.example.restorumba

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class OrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val name: String? = intent.getStringExtra("name")
        val phone: String? = intent.getStringExtra("phone")
        val pass: String? = intent.getStringExtra("password")

        val linkToBasket: ImageButton = findViewById(R.id.link_to_basket)
        val linkToProfile: ImageButton = findViewById(R.id.link_to_profile)

        linkToBasket.setOnClickListener {
            val basketIntent = Intent(this, BasketActivity::class.java)
            startActivity(basketIntent)
            finish()
        }

        linkToProfile.setOnClickListener {
            val profIntent = Intent(this, ProfileActivity::class.java)
            profIntent.putExtra("phone", phone)
            profIntent.putExtra("name", name)
            profIntent.putExtra("password", pass)
            startActivity(profIntent)
            finish()
        }
    }
}