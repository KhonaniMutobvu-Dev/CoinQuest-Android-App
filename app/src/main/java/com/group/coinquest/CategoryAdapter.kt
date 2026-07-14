package com.group.coinquest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter(
    private var list: List<Category>
) : RecyclerView.Adapter<CategoryAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {

        val name = view.findViewById<TextView>(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)

        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        holder.name.text = list[position].name
    }

    override fun getItemCount() = list.size

    fun update(newList: List<Category>) {
        list = newList
        notifyDataSetChanged()
    }
}