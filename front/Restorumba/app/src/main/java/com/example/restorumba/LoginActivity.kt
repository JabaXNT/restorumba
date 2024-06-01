package com.example.restorumba

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text
import java.net.URL

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val button: Button = findViewById(R.id.button)
        val linkToReg: TextView = findViewById(R.id.link_to_reg)

        val phoneInput: EditText = findViewById(R.id.phoneInput)
        val passwordInput: EditText = findViewById(R.id.passwordInput)

        linkToReg.setOnClickListener {
            val intent = Intent(this, RegActivity::class.java)
            startActivity(intent)
            finish()
        }

        button.setOnClickListener {
            val password = passwordInput.text.toString().trim()
            val phone = phoneInput.text.toString().trim()
            val emptyText = getResources().getString(R.string.empty_fields);
            val wrongText = getResources().getString(R.string.wrong_fields);

            if (password == "" || phone == "") {
                Toast.makeText(this, emptyText, Toast.LENGTH_SHORT).show()
            } else if (phone.length > 13) {
                Toast.makeText(this, wrongText, Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, OrderActivity::class.java)
                intent.putExtra("phone", phone)
                intent.putExtra("password", password)
                startActivity(intent)
                finish()
            }
        }
    }
}