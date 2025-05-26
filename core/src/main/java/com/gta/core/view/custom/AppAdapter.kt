package com.gta.core.view.custom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gta.core.R


class AppAdapter(private val appList: List<AppItem>) : RecyclerView.Adapter<AppAdapter.ViewHolder>() {
    private var onItemClickListener: ((View, Int) -> Unit)? = null
    fun setOnItemClickListener(listener: (View, Int) -> Unit) {
        onItemClickListener = listener
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconImage: ImageView = itemView.findViewById(R.id.iconImage)
        val appName: TextView = itemView.findViewById(R.id.appName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_app, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appItem = appList[position]
        holder.iconImage.setImageResource(appItem.iconResId)
        holder.appName.text = appItem.name
        // 设置点击事件
        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(it, position)
        }
    }

    override fun getItemCount(): Int = appList.size
}