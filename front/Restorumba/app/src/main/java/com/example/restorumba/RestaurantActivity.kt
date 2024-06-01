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

class RestaurantActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_restaurant)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val linkToOrder: ImageButton = findViewById(R.id.link_to_order)
        val save: Button = findViewById(R.id.save)

        val name: String? = intent.getStringExtra("restaurant_name")

        val chosenName: TextView = findViewById(R.id.chosen_name)
        chosenName.text = name

        val tyb: ConstraintLayout = findViewById(R.id.tyb)

        tyb.setOnClickListener {
            chosenName.text = "Tom Yum Bar"
        }

        linkToOrder.setOnClickListener {
            val orderIntent = Intent(this, OrderActivity::class.java)
            orderIntent.putExtra("restaurant_name", chosenName.text)
            startActivity(orderIntent)
            finish()
        }

        save.setOnClickListener {
            val orderIntent = Intent(this, OrderActivity::class.java)
            orderIntent.putExtra("restaurant_name", chosenName.text)
            orderIntent.putExtra("c1", "Супы")
            orderIntent.putExtra("c2", "Роллы")
            orderIntent.putExtra("c3", "Десерты")
            orderIntent.putExtra("c4", "Салаты")
            startActivity(orderIntent)
            finish()
        }

        linkToOrder.setOnClickListener {
            val orderIntent = Intent(this, OrderActivity::class.java)
            startActivity(orderIntent)
            finish()
        }
    }
}