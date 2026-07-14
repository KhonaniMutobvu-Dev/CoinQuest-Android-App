package com.group.coinquest

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import kotlinx.coroutines.launch
import java.util.*

class AnalyticsActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase

    private lateinit var barChart: BarChart
    private lateinit var pieChart: PieChart
    private lateinit var totalText: TextView

    private var startDate = ""
    private var endDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_analytics)

        barChart = findViewById(R.id.barChart)
        pieChart = findViewById(R.id.pieChart)
        totalText = findViewById(R.id.tvTotal)

        val btnStart = findViewById<Button>(R.id.btnStartDate)
        val btnEnd = findViewById<Button>(R.id.btnEndDate)
        val btnApply = findViewById<Button>(R.id.btnApply)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "coinquest_db"
        ).build()

        val userId = getSharedPreferences("session", MODE_PRIVATE)
            .getInt("USER_ID", -1)

        loadData(userId)

        btnStart.setOnClickListener {
            pickDate { date ->
                startDate = date
                btnStart.text = date
            }
        }

        btnEnd.setOnClickListener {
            pickDate { date ->
                endDate = date
                btnEnd.text = date
            }
        }

        btnApply.setOnClickListener {
            loadData(userId)
        }
    }

    private fun loadData(userId: Int) {

        lifecycleScope.launch {

            val transactions = db.transactionDao().getAll(userId)

            val filtered = if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
                transactions.filter {
                    it.date in startDate..endDate
                }
            } else transactions

            val income = filtered.filter { it.category == "Income" }
                .sumOf { it.amount }

            val expense = filtered.filter { it.category == "Expense" }
                .sumOf { it.amount }

            val totalSpent = expense

            val categoryMap = filtered
                .filter { it.category == "Expense" }
                .groupBy { it.description }
                .mapValues { it.value.sumOf { t -> t.amount } }

            runOnUiThread {

                totalText.text =
                    "Total Spent: R%.2f".format(totalSpent)

                setupPieChart(income, expense)
                setupBarChart(categoryMap)
            }
        }
    }

    private fun setupPieChart(income: Double, expense: Double) {

        val entries = ArrayList<PieEntry>()

        entries.add(PieEntry(income.toFloat(), "Income"))
        entries.add(PieEntry(expense.toFloat(), "Expense"))

        val dataSet = PieDataSet(entries, "Overview")

        val data = PieData(dataSet)

        pieChart.data = data
        pieChart.invalidate()
    }

    private fun setupBarChart(dataMap: Map<String, Double>) {

        val entries = ArrayList<BarEntry>()
        val labels = ArrayList<String>()

        var index = 0f

        dataMap.forEach { (key, value) ->

            entries.add(BarEntry(index, value.toFloat()))
            labels.add(key)
            index++
        }

        val dataSet = BarDataSet(entries, "Expenses by Category")

        val data = BarData(dataSet)

        barChart.data = data
        barChart.invalidate()
    }

    private fun pickDate(callback: (String) -> Unit) {

        val cal = Calendar.getInstance()

        DatePickerDialog(
            this,
            { _, y, m, d ->
                val date = String.format("%04d-%02d-%02d", y, m + 1, d)
                callback(date)
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}