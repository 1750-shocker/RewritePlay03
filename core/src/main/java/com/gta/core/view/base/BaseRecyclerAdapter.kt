package com.gta.core.view.base

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


abstract class BaseRecyclerAdapter<V : ViewDataBinding> :
    RecyclerView.Adapter<BaseRecyclerAdapter.BaseRecyclerHolder<V>>() {

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerHolder<V>

    override fun onBindViewHolder(holder: BaseRecyclerHolder<V>, position: Int) {
        onBaseBindViewHolder(position, holder.binding)
    }

    abstract fun onBaseBindViewHolder(position: Int, binding: V)

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    open class BaseRecyclerHolder<V : ViewDataBinding>(val binding: V) :
        RecyclerView.ViewHolder(binding.root)

}