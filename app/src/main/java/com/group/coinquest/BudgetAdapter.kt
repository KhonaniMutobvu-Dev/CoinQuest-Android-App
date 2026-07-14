package com.group.coinquest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BudgetAdapter(
    private val list: List<Budget>
) : RecyclerView.Adapter<BudgetAdapter.BudgetVH>() {

    class BudgetVH(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.tvCatName)
        val percent = view.findViewById<TextView>(R.id.tvCatPercent)
        val progress = view.findViewById<ProgressBar>(R.id.pbCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category_progress, parent, false)
        return BudgetVH(view)
    }

    override fun onBindViewHolder(holder: BudgetVH, position: Int) {

        val item = list[position]

        holder.name.text = item.categoryName

        val percent = ((item.minGoal / item.maxGoal) * 100).toInt()

        holder.percent.text = "$percent%"

        holder.progress.progress = percent
    }

    override fun getItemCount() = list.size
}