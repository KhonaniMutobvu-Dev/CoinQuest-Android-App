package com.group.coinquest



import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.launch

class CategoryManagementActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var adapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_category_management)

        val etName = findViewById<EditText>(R.id.etCategoryName)
        val btnAdd = findViewById<Button>(R.id.btnAddCategory)
        val rv = findViewById<RecyclerView>(R.id.rvCategories)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "coinquest_db"
        ).build()

        val userId = getSharedPreferences("session", MODE_PRIVATE)
            .getInt("USER_ID", -1)

        adapter = CategoryAdapter(emptyList())

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        loadCategories(userId)

        btnAdd.setOnClickListener {

            val name = etName.text.toString().trim()

            if (name.isEmpty()) {
                Toast.makeText(this, "Enter category name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {

                db.categoryDao().insert(
                    Category(
                        userId = userId,
                        name = name
                    )
                )

                runOnUiThread {
                    etName.text.clear()
                    loadCategories(userId)

                    Toast.makeText(
                        this@CategoryManagementActivity,
                        "Category added",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun loadCategories(userId: Int) {

        lifecycleScope.launch {

            val list = db.categoryDao().getAll(userId)

            runOnUiThread {
                adapter.update(list)
            }
        }
    }
}