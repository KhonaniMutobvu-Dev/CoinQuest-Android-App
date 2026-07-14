package com.group.coinquest

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.launch
import java.util.Calendar

class TransactionsActivity : AppCompatActivity() {


    private lateinit var db: AppDatabase
    private lateinit var adapter: TransactionAdapter

    private var startDate = ""
    private var endDate = ""
    private var userId = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_transactions)


        val rv = findViewById<RecyclerView>(R.id.rvTransactions)
        val noData = findViewById<TextView>(R.id.tvNoTransactions)

        val btnStart = findViewById<Button>(R.id.btnFilterStart)
        val btnEnd = findViewById<Button>(R.id.btnFilterEnd)
        val btnApply = findViewById<Button>(R.id.btnApplyFilter)
        val btnClear = findViewById<Button>(R.id.btnClearFilter)


        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "coinquest_db"
        ).build()


        userId = getSharedPreferences("session", MODE_PRIVATE)
            .getInt("USER_ID", -1)


        adapter = TransactionAdapter(emptyList())

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter


        loadAll(noData)


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
            filter(noData)
        }


        btnClear.setOnClickListener {
            startDate = ""
            endDate = ""

            btnStart.text = "From Date"
            btnEnd.text = "To Date"

            loadAll(noData)
        }

    }


    private fun loadAll(noData: TextView) {

        lifecycleScope.launch {

            val list = db.transactionDao().getAll(userId)

            runOnUiThread {
                updateUI(list, noData)
            }
        }
    }


    private fun filter(noData: TextView) {

        if(startDate.isEmpty() || endDate.isEmpty()) {
            Toast.makeText(this, "Select dates", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {

            val list = db.transactionDao()
                .filterByDate(userId, startDate, endDate)

            runOnUiThread {
                updateUI(list, noData)
            }
        }
    }


    private fun updateUI(list: List<Transaction>, noData: TextView) {

        if(list.isEmpty()) {
            noData.visibility = View.VISIBLE
        } else {
            noData.visibility = View.GONE
        }

        adapter.updateData(list)
    }


    private fun pickDate(callback: (String) -> Unit) {

        val cal = Calendar.getInstance()

        DatePickerDialog(
            this,
            { _, year, month, day ->

                val date =
                    String.format("%04d-%02d-%02d", year, month + 1, day)

                callback(date)

            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}