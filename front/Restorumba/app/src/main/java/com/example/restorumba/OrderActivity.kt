package com.example.restorumba

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class OrderActivity : AppCompatActivity() {
    private lateinit var dish: Array<out Any>

    private val tomYum = arrayOf(
        "Том ям с курицей",
        "400₽",
        "Большая порция самого знаменитого супа с курицей и оригинальными пряностями.",
        R.mipmap.ic_tomyum
    )

    private val kari = arrayOf(
        "Карри GREEN",
        "498₽",
        "Традиционное азиатское острое блюдо, приготовленное на пасте из разнообразных специй и сливках с кокосовым молоком.",
        R.mipmap.ic_kari
    )

    private val chicken = arrayOf(
        "Курица терияки",
        "468₽",
        "Сочная нежная курица, обжаренная в сладком соусе терияки с кунжутом. Подаётся с основой на выбор.",
        R.mipmap.ic_chicken
    )

    private val cheesecake = arrayOf(
        "Чизкейк",
        "328₽",
        "Нежный творожный десерт. На выбор предлагается: манго, классический.",
        R.mipmap.ic_cheesecake
    )

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
        val restaurantName: String? = intent.getStringExtra("restaurant_name")
        val c1 = intent.getStringExtra("c1")
        val c2 = intent.getStringExtra("c2")
        val c3 = intent.getStringExtra("c3")
        val c4 = intent.getStringExtra("c4")


        val linkToBasket: ImageButton = findViewById(R.id.link_to_basket)
        val linkToRestaurant: TextView = findViewById(R.id.link_to_restaurant)
        val linkToProfile: ImageButton = findViewById(R.id.link_to_profile)
        val shakeButton: Button = findViewById(R.id.shake)
        val cat1 = findViewById<TextView>(R.id.c1)
        val cat2 = findViewById<TextView>(R.id.c2)
        val cat3 = findViewById<TextView>(R.id.c3)
        val cat4 = findViewById<TextView>(R.id.c4)


        if (restaurantName != null) {
            linkToRestaurant.text = restaurantName
        }

        if (c1 != null) {
            cat1.text = c1
            cat2.text = c2
            cat3.text = c3
            cat4.text = c4
        }

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

        linkToRestaurant.setOnClickListener {
            val restaurantIntent = Intent(this, RestaurantActivity::class.java)
            restaurantIntent.putExtra("restaurant_name", linkToRestaurant.text)
            startActivity(restaurantIntent)
            finish()
        }

        val dishName: TextView = findViewById(R.id.dish_name)
        val dishPrice: TextView = findViewById(R.id.dish_price)
        val dishDescription: TextView = findViewById(R.id.dish_description)
        val dishPhoto: ImageView = findViewById(R.id.dish_photo)

        var menu = mutableListOf(tomYum, chicken, kari)

        var flag = false

        cat3.setOnClickListener {
            flag = true
            cat3.setTextColor(getColor(R.color.red))
        }

        shakeButton.setOnClickListener {
            if (flag) {
                dish = cheesecake
                dishName.text = dish[0].toString()
                dishPrice.text = dish[1].toString()
                dishDescription.text = dish[2].toString()
                dishPhoto.setBackgroundResource(dish[3] as Int)
            }
            else {
                val randomNum = (0..2).random()
                dish = menu[randomNum]
                dishName.text = dish[0].toString()
                dishPrice.text = dish[1].toString()
                dishDescription.text = dish[2].toString()
                dishPhoto.setBackgroundResource(dish[3] as Int)
            }
        }
    }
}