package com.group.coinquest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransactionAdapter(
    private var list: List<Transaction>
) : RecyclerView.Adapter<TransactionAdapter.VH>() {


    class VH(view: View) : RecyclerView.ViewHolder(view) {

        val title = view.findViewById<TextView>(R.id.tvItemTitle)
        val amount = view.findViewById<TextView>(R.id.tvItemAmount)
        val date = view.findViewById<TextView>(R.id.tvItemDate)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)

        return VH(view)
    }


    override fun onBindViewHolder(holder: VH, position: Int) {

        val item = list[position]

        holder.title.text = item.description

        holder.amount.text =
            if(item.category == "Income")
                "+ R${item.amount}"
            else
                "- R${item.amount}"

        holder.date.text = item.date
    }


    override fun getItemCount() = list.size


    fun updateData(newList: List<Transaction>) {
        list = newList
        notifyDataSetChanged()
    }
}