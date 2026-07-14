package com.group.coinquest

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DashboardActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase

    private lateinit var xpText: TextView
    private lateinit var streakText: TextView
    private lateinit var safeSpendText: TextView

    private lateinit var rvBudgets: RecyclerView
    private lateinit var rvTransactions: RecyclerView
    private lateinit var transactionAdapter: TransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dashboard)

        // Views
        xpText = findViewById(R.id.tvXP)
        streakText = findViewById(R.id.tvBadge)
        safeSpendText = findViewById(R.id.tvSafeToSpend)

        rvBudgets = findViewById(R.id.rvBudgets)
        rvTransactions = findViewById(R.id.rvCategories)

        val fab = findViewById<FloatingActionButton>(R.id.fabAddExpense)
        val btnAnalytics = findViewById<Button>(R.id.btnAnalytics)
        val btnCategories = findViewById<Button>(R.id.btnManageCategories)
        val btnAddBudget = findViewById<ImageButton>(R.id.btnAddBudget)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "coinquest_db"
        ).fallbackToDestructiveMigration()
            .build()

        val userId = getSharedPreferences("session", MODE_PRIVATE)
            .getInt("USER_ID", -1)
        
        loadBudgets(userId)


        // RecyclerView setup
        transactionAdapter = TransactionAdapter(emptyList())
        rvTransactions.layoutManager = LinearLayoutManager(this)
        rvTransactions.adapter = transactionAdapter

        rvBudgets.layoutManager = LinearLayoutManager(this)

        loadDashboard(userId)

        // ADD BUDGET
        btnAddBudget.setOnClickListener {
            startActivity(
                Intent(this, BudgetSettingsActivity::class.java)
            )
        }

        // OPEN ADD EXPENSE
        fab.setOnClickListener {
            startActivity(
                Intent(this, AddExpenseActivity::class.java)
            )
        }

        // OPEN ANALYTICS
        btnAnalytics.setOnClickListener {
            startActivity(
                Intent(this, AnalyticsActivity::class.java)
            )
        }

        // OPEN CATEGORIES
        btnCategories.setOnClickListener {
            startActivity(
                Intent(this, CategoryManagementActivity::class.java)
            )
        }
    }

    override fun onResume() {

        super.onResume()


        val userId =
            getSharedPreferences(
                "session",
                MODE_PRIVATE
            )
                .getInt(
                    "USER_ID",
                    -1
                )


        loadTransactions(userId)
        loadBudgets(userId)

    }
    private fun loadTransactions(userId:Int){

        lifecycleScope.launch {


            val transactions =
                db.transactionDao()
                    .getAll(userId)


            runOnUiThread {


                rvTransactions.adapter =
                    TransactionAdapter(
                        transactions
                    )

            }

        }

    }

    private fun loadDashboard(userId: Int) {

        lifecycleScope.launch {

            val transactions = db.transactionDao().getAll(userId)
            val budgets = db.budgetDao().getAll(userId)
            val progress = db.userProgressDao().getProgress(userId)

            val currentMonth = SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(Date())

            val income = transactions
                .filter { it.category == "Income" && it.date.startsWith(currentMonth) }
                .sumOf { it.amount }

            val expense = transactions
                .filter { it.category != "Income" && it.date.startsWith(currentMonth) }
                .sumOf { it.amount }

            val totalMaxBudget = budgets.sumOf { it.maxGoal }

            // SAFE TO SPEND CALCULATION (as per README)
            // (Max Monthly Budget - Total Spent This Month) / Days Remaining in Month
            
            val calendar = Calendar.getInstance()
            val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
            val daysRemaining = (daysInMonth - dayOfMonth) + 1

            val safeToSpend = if (totalMaxBudget > 0) {
                (totalMaxBudget - expense) / daysRemaining
            } else {
                income - expense
            }

            runOnUiThread {

                safeSpendText.text =
                    "Safe to Spend Today: R%.2f".format(safeToSpend)

                if (progress != null) {
                    xpText.text =
                        "Level ${progress.level}\n${progress.xp} XP"

                    streakText.text =
                        "Streak: ${progress.streak} Days"
                }

                transactionAdapter.updateData(transactions)
            }
        }
    }

    private fun loadBudgets(userId: Int) {

        lifecycleScope.launch {

            val budgets = db.budgetDao().getAll(userId)

            runOnUiThread {

                val adapter = BudgetAdapter(budgets)
                rvBudgets.adapter = adapter

            }
        }
    }
}