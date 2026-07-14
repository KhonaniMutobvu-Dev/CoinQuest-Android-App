package com.group.coinquest

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.Bitmap
import android.view.View
import android.content.pm.PackageManager
class AddExpenseActivity : AppCompatActivity() {


    private val CAMERA_REQUEST = 100

    private lateinit var receiptImage: ImageView

    private var receiptPath:String? = null
    private lateinit var db: AppDatabase

    private var startTime = ""
    private var endTime = ""

    private lateinit var categoryList: List<Category>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_expense)
        receiptImage =
            findViewById(
                R.id.ivReceiptPreview
            )


        val capture =
            findViewById<Button>(
                R.id.btnCapturePhoto
            )


        capture.setOnClickListener {


            if(
                checkSelfPermission(
                    android.Manifest.permission.CAMERA
                )
                != PackageManager.PERMISSION_GRANTED
            ){

                requestPermissions(
                    arrayOf(
                        android.Manifest.permission.CAMERA
                    ),
                    200
                )

            }
            else{


                val intent =
                    Intent(
                        MediaStore.ACTION_IMAGE_CAPTURE
                    )

                startActivityForResult(
                    intent,
                    CAMERA_REQUEST
                )

            }

        }

        val amount = findViewById<EditText>(R.id.etAmount)
        val description = findViewById<EditText>(R.id.etDescription)
        val categorySpinner = findViewById<Spinner>(R.id.spCategory)

        val startBtn = findViewById<Button>(R.id.btnStartTime)
        val endBtn = findViewById<Button>(R.id.btnEndTime)
        val saveBtn = findViewById<Button>(R.id.btnSaveExpense)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "coinquest_db"
        ).build()

        val userId = getSharedPreferences("session", MODE_PRIVATE)
            .getInt("USER_ID", -1)

        // 1. LOAD LIVE CATEGORIES + ADD DEFAULT INCOME/EXPENSE
        loadCategories(userId, categorySpinner)

        startBtn.setOnClickListener {
            pickTime { time ->
                startTime = time
                startBtn.text = time
            }
        }

        endBtn.setOnClickListener {
            pickTime { time ->
                endTime = time
                endBtn.text = time
            }
        }

        saveBtn.setOnClickListener {

            val amountValue = amount.text.toString().trim()
            val desc = description.text.toString().trim()

            if (amountValue.isEmpty() || desc.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedCategory =
                categorySpinner.selectedItem.toString()

            val todayDate = SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            ).format(Date())

            if (userId == -1) {
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {

                db.transactionDao().addTransaction(
                    Transaction(
                        userId = userId,
                        amount = amountValue.toDouble(),
                        description = desc,
                        category = selectedCategory,
                        date = todayDate,
                        startTime = startTime,
                        endTime = endTime,
                        receipt = null
                    )
                )

                addXpAndUpdateProgress(userId, 50)

                runOnUiThread {
                    Toast.makeText(
                        this@AddExpenseActivity,
                        "Saved successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()
                }
            }
        }
    }

    // 🔥 LIVE CATEGORIES + DEFAULT INCOME/EXPENSE
    private fun loadCategories(userId: Int, spinner: Spinner) {

        lifecycleScope.launch {

            val userCategories = db.categoryDao().getAll(userId)

            val finalList = mutableListOf<String>()

            // Always keep Income / Expense
            finalList.add("Income")
            finalList.add("Expense")

            // Add DB categories
            finalList.addAll(userCategories.map { it.name })

            runOnUiThread {

                val adapter = ArrayAdapter(
                    this@AddExpenseActivity,
                    android.R.layout.simple_spinner_dropdown_item,
                    finalList
                )

                spinner.adapter = adapter
            }
        }
    }

    private fun pickTime(callback: (String) -> Unit) {

        val cal = Calendar.getInstance()

        TimePickerDialog(
            this,
            { _, hour, minute ->
                val time = String.format("%02d:%02d", hour, minute)
                callback(time)
            },
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun addXpAndUpdateProgress(userId: Int, xpGain: Int) {

        lifecycleScope.launch {

            val progressDao = db.userProgressDao()

            val progress = progressDao.getProgress(userId)
                ?: UserProgress(userId = userId)

            val newXp = progress.xp + xpGain
            val newLevel = calculateLevel(newXp)

            progressDao.insert(
                progress.copy(
                    xp = newXp,
                    level = newLevel
                )
            )
        }
    }

    private fun calculateLevel(xp: Int): Int {
        return (xp / 1000) + 1
    }
    override fun onActivityResult(
        requestCode:Int,
        resultCode:Int,
        data:Intent?
    ){

        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )


        if(
            requestCode == CAMERA_REQUEST
            &&
            resultCode == RESULT_OK
        ){

            val image =
                data?.extras?.get("data")
                        as Bitmap


            receiptImage.setImageBitmap(image)

            receiptImage.visibility =
                View.VISIBLE


            receiptPath =
                "receipt_${System.currentTimeMillis()}"

        }

    }
}