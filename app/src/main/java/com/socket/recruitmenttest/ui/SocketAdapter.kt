package com.socket.recruitmenttest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.socket.recruitmenttest.data.model.SocketModel
import com.socket.recruitmenttest.databinding.SocketRecyclerAdapterBinding

class SocketAdapter: RecyclerView.Adapter<SocketAdapter.SocketHolder>() {
    inner class SocketHolder(val binding: SocketRecyclerAdapterBinding): RecyclerView.ViewHolder(binding.root)
    private val diffUtil = object: DiffUtil.ItemCallback<SocketModel>() {
        override fun areItemsTheSame(oldItem: SocketModel, newItem: SocketModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SocketModel, newItem: SocketModel): Boolean {
            return oldItem.name == newItem.name
        }
    }
    private val list = AsyncListDiffer(this, diffUtil)

    var socketList: MutableList<SocketModel>
    get() = list.currentList
    set(value) = list.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SocketHolder {
        val binding = SocketRecyclerAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SocketHolder(binding)
    }

    override fun onBindViewHolder(holder: SocketHolder, position: Int) {
        holder.binding.socketList = socketList[position]
    }

    override fun getItemCount(): Int {
        return socketList.size
    }
}