package com.group.coinquest

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.launch

class AchievementsActivity : AppCompatActivity() {


    private lateinit var db: AppDatabase
    private lateinit var adapter: BadgeAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_achievements)


        val rv = findViewById<RecyclerView>(R.id.rvBadges)
        val xpText = findViewById<TextView>(R.id.tvXpLevel)
        val streakText = findViewById<TextView>(R.id.tvStreak)
        val progressBar = findViewById<ProgressBar>(R.id.pbXp)


        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "coinquest_db"
        ).build()


        val userId = getSharedPreferences("session", MODE_PRIVATE)
            .getInt("USER_ID", -1)


        adapter = BadgeAdapter(emptyList())
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter


        loadData(userId, xpText, streakText, progressBar)
    }


    private fun loadData(
        userId: Int,
        xpText: TextView,
        streakText: TextView,
        progressBar: ProgressBar
    ) {

        lifecycleScope.launch {

            val progress = db.userProgressDao().getProgress(userId)

            val achievements = db.achievementDao().getUserAchievements(userId)

            runOnUiThread {

                if (progress != null) {

                    xpText.text =
                        "Level ${progress.level} • ${progress.xp} XP"

                    streakText.text =
                        "🔥 Streak: ${progress.streak} Days"

                    progressBar.progress =
                        progress.xp % 1000
                }

                adapter.update(achievements)
            }
        }
    }
}