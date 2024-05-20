package com.example.restorumba

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EditProfileActivity : AppCompatActivity() {
    private var nameSaveKey: String = "name_save_key"
    private var phoneSaveKey: String = "phone_save_key"
    private var emailSaveKey: String = "email_save_key"
    private var passSaveKey: String = "pass_save_key"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val namePref = getSharedPreferences("Name", MODE_PRIVATE)
        val phonePref = getSharedPreferences("Phone", MODE_PRIVATE)
        val emailPref = getSharedPreferences("Email", MODE_PRIVATE)
        val passwordPref = getSharedPreferences("Password", MODE_PRIVATE)

        val saveButton: Button = findViewById(R.id.save_button)
        val homeButton: ImageButton = findViewById(R.id.link_to_order)
        val profilePicture: ImageButton = findViewById(R.id.profile_picture)
        val profileChooseMenu: ConstraintLayout = findViewById(R.id.menu)
        val close: ImageButton = findViewById(R.id.close)
        val name: EditText = findViewById(R.id.name)
        val phone: EditText = findViewById(R.id.phoneNumber)
        val email: EditText = findViewById(R.id.email)
        val password: EditText = findViewById(R.id.password)

        var isMenuOpen = false

        val prof1: ImageButton = findViewById(R.id.profile_picture_1)
        val prof2: ImageButton = findViewById(R.id.profile_picture_2)
        val prof3: ImageButton = findViewById(R.id.profile_picture_3)
        val prof4: ImageButton = findViewById(R.id.profile_picture_4)
        val prof5: ImageButton = findViewById(R.id.profile_picture_5)
        val prof6: ImageButton = findViewById(R.id.profile_picture_6)
        val prof7: ImageButton = findViewById(R.id.profile_picture_7)
        val prof8: ImageButton = findViewById(R.id.profile_picture_8)

        val profilePictures = listOf(prof1, prof2, prof3, prof4, prof5, prof6, prof7, prof8)
        for (chosenPic in profilePictures) {
            chosenPic.setOnClickListener {
                profileChooseMenu.visibility = View.GONE
                name.isEnabled = true
                phone.isEnabled = true
                email.isEnabled = true
                password.isEnabled = true
                homeButton.isEnabled = true
                isMenuOpen = false
                profilePicture.setBackgroundResource(R.drawable.profile_picture)
            }
        }

        profilePicture.setOnClickListener {
            if (isMenuOpen) {
                profileChooseMenu.visibility = View.GONE
                name.isEnabled = true
                phone.isEnabled = true
                email.isEnabled = true
                password.isEnabled = true
                homeButton.isEnabled = true
                isMenuOpen = false
            } else {
                profileChooseMenu.visibility = View.VISIBLE
                name.isEnabled = false
                phone.isEnabled = false
                email.isEnabled = false
                password.isEnabled = false
                homeButton.isEnabled = false
                isMenuOpen = true
            }
        }

        close.setOnClickListener {
            profileChooseMenu.visibility = View.GONE
            name.isEnabled = true
            phone.isEnabled = true
            email.isEnabled = true
            password.isEnabled = true
            homeButton.isEnabled = true
            isMenuOpen = false
        }

        homeButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        saveButton.setOnClickListener {
            val nameEdit: SharedPreferences.Editor = namePref.edit()
            nameEdit.putString(nameSaveKey, name.text.toString())
            val phoneEdit: SharedPreferences.Editor = phonePref.edit()
            phoneEdit.putString(phoneSaveKey, phone.text.toString())
            val emailEdit: SharedPreferences.Editor = emailPref.edit()
            emailEdit.putString(emailSaveKey, phone.text.toString())
            val passwordEdit: SharedPreferences.Editor = passwordPref.edit()
            passwordEdit.putString(passSaveKey, phone.text.toString())
            nameEdit.apply()
            phoneEdit.apply()
            emailEdit.apply()
            passwordEdit.apply()
        }

        name.setText(namePref.getString(nameSaveKey, ""))
        phone.setText(namePref.getString(phoneSaveKey, ""))
        email.setText(namePref.getString(emailSaveKey, ""))
        password.setText(namePref.getString(passSaveKey, ""))
    }
}