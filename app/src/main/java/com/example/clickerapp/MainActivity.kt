package com.example.clickerapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.clickerapp.storage.ClickStorage

class MainActivity : AppCompatActivity() {
    private lateinit var clickCircle: ImageView
    private lateinit var statsButton: Button
    private lateinit var telegramButton: Button
    private lateinit var exitButton: Button
    private lateinit var levelImage: ImageView
    private lateinit var clickCounter: TextView
    private lateinit var toNextLevel: TextView
    private lateinit var title: TextView
    private lateinit var storage: ClickStorage
    private var totalClicks = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        storage = ClickStorage(this)
        totalClicks = storage.getTotalClicks()

        clickCircle = findViewById(R.id.clickCircle)
        statsButton = findViewById(R.id.statsButton)
        telegramButton = findViewById(R.id.telegramButton)
        exitButton = findViewById(R.id.exitButton)
        levelImage = findViewById(R.id.userLevelImage)
        clickCounter = findViewById(R.id.clickCounter)
        toNextLevel = findViewById(R.id.toNextLevel)
        title = findViewById(R.id.appTitle)

        updateUI()

        clickCircle.setOnClickListener {
            totalClicks++
            storage.saveClick()
            updateUI()
        }

        statsButton.setOnClickListener {
            startActivity(Intent(this, StatsActivity::class.java))
        }

        telegramButton.setOnClickListener {
            val url = "https://t.me/anek4ever"
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }

        exitButton.setOnClickListener {
            finishAffinity()
        }
    }

    private fun updateUI() {
        clickCounter.text = getString(R.string.total_clicks, totalClicks)
        val (resId, nextLevelClicks) = when {
            totalClicks > 1000 -> R.drawable.ic_level_5 to 0
            totalClicks > 500 -> R.drawable.ic_level_4 to 1000 - totalClicks
            totalClicks > 200 -> R.drawable.ic_level_3 to 501 - totalClicks
            totalClicks > 100 -> R.drawable.ic_level_2 to 201 - totalClicks
            else -> R.drawable.ic_level_1 to 101 - totalClicks
        }
        levelImage.setImageResource(resId)
        toNextLevel.text = if (nextLevelClicks > 0) {
            getString(R.string.next_level_in, nextLevelClicks)
        } else {
            getString(R.string.max_level)
        }
    }
}
