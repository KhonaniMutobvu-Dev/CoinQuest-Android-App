package com.group.coinquest

import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch

class BudgetSettingsActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase

    private var maxGoal = 5000.0
    private var minGoal = 1000.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_settings)

        val sbMax = findViewById<SeekBar>(R.id.sbMaxGoal)
        val sbMin = findViewById<SeekBar>(R.id.sbMinGoal)
        val tvMax = findViewById<TextView>(R.id.tvMaxLabel)
        val tvMin = findViewById<TextView>(R.id.tvMinLabel)
        val btnSave = findViewById<Button>(R.id.btnSaveGoals)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "coinquest_db"
        ).build()

        sbMax.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                maxGoal = progress.toDouble()
                tvMax.text = "Maximum Monthly Goal: R${progress}"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        sbMin.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                minGoal = progress.toDouble()
                tvMin.text = "Minimum Monthly Goal: R${progress}"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        btnSave.setOnClickListener {

            val userId = getSharedPreferences("session", MODE_PRIVATE)
                .getInt("USER_ID", -1)

            lifecycleScope.launch {

                // Example default category budget (you can extend to multiple categories later)
                db.budgetDao().insert(
                    Budget(
                        userId = userId,
                        categoryName = "General",
                        minGoal = minGoal,
                        maxGoal = maxGoal
                    )
                )

                runOnUiThread {
                    Toast.makeText(
                        this@BudgetSettingsActivity,
                        "Budget saved",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
        }
    }
}