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
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import kotlin.concurrent.thread

class RegActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reg)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nameInput: EditText = findViewById(R.id.nameInput)
        val phoneInput: EditText = findViewById(R.id.phoneInput)
        val passwordInput: EditText = findViewById(R.id.passwordInput)

        val button: Button = findViewById(R.id.button)
        val linkToLogin: TextView = findViewById(R.id.link_to_login)

        linkToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        val apiService = APIService()

        button.setOnClickListener {
            thread {

            }
            /*val name = nameInput.text.toString().trim()
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
                intent.putExtra("name", name)
                intent.putExtra("password", password)
                startActivity(intent)
                finish()
            }*/
        }
    }
}