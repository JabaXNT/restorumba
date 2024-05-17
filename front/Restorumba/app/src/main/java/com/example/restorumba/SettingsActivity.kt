package com.example.restorumba

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text
import java.lang.reflect.Modifier

class SettingsActivity : AppCompatActivity() {

    lateinit var switchMode: SwitchCompat;
    var nightMode: Boolean = false;
    lateinit var sharedPreferences: SharedPreferences;
    lateinit var editor: SharedPreferences.Editor;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val back: Button = findViewById(R.id.back)
        val switchMode: androidx.appcompat.widget.SwitchCompat = findViewById(R.id.dark_mode_switch)
        val vibrationSwitch: androidx.appcompat.widget.SwitchCompat = findViewById(R.id.vibration_switch)

        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("nightMode", false);

        if (nightMode) {
            switchMode.isChecked
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        switchMode.setOnClickListener {
            if (nightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor = sharedPreferences.edit()
                editor.putBoolean("nightMode", false);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor = sharedPreferences.edit()
                editor.putBoolean("nightMode", true);
            }
            editor.apply();
        }

        val deleteButton: TextView = findViewById(R.id.delete_button)
        val deleteMenu: ConstraintLayout = findViewById(R.id.delete_menu)
        val deleteSubmit: TextView = findViewById(R.id.delete_submit)
        val closeMenu: ImageButton = findViewById(R.id.close_menu)

        vibrationSwitch.setOnClickListener {
            Toast.makeText(this, "TODO", Toast.LENGTH_SHORT).show()
        }

        back.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        deleteButton.setOnClickListener {
            deleteMenu.visibility = View.VISIBLE
            switchMode.isEnabled = false
            vibrationSwitch.isEnabled = false
            back.isEnabled = false
        }

        deleteSubmit.setOnClickListener {
            deleteMenu.visibility = View.GONE
            switchMode.isEnabled = true
            vibrationSwitch.isEnabled = true
            back.isEnabled = true
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        closeMenu.setOnClickListener {
            deleteMenu.visibility = View.GONE
            switchMode.isEnabled = true
            vibrationSwitch.isEnabled = true
            back.isEnabled = true
        }
    }
}