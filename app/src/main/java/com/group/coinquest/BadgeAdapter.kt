package com.group.coinquest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BadgeAdapter(
    private var list: List<Achievement>
) : RecyclerView.Adapter<BadgeAdapter.VH>() {


    class VH(view: View) : RecyclerView.ViewHolder(view) {

        val emoji = view.findViewById<TextView>(R.id.tvBadgeEmoji)
        val title = view.findViewById<TextView>(R.id.tvBadgeTitle)
        val desc = view.findViewById<TextView>(R.id.tvBadgeDesc)
        val status = view.findViewById<TextView>(R.id.tvBadgeStatus)
        val xp = view.findViewById<TextView>(R.id.tvBadgeXp)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_badge, parent, false)

        return VH(view)
    }


    override fun onBindViewHolder(holder: VH, position: Int) {

        val item = list[position]

        holder.emoji.text = item.emoji
        holder.title.text = item.title
        holder.desc.text = item.description

        holder.status.text =
            if(item.isUnlocked) "Unlocked"
            else "Locked"

        holder.xp.text = "+${item.xpReward} XP"
    }


    override fun getItemCount() = list.size


    fun update(list: List<Achievement>) {
        this.list = list
        notifyDataSetChanged()
    }
}