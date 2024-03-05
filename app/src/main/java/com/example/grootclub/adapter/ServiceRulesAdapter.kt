package com.example.grootclub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.grootclub.data.ServiceRulesData
import com.example.grootclub.databinding.ItemListDataBinding

class ServiceRulesAdapter(
    private val serviceRules: ArrayList<ServiceRulesData>,
) : RecyclerView.Adapter<ServiceRulesAdapter.ViewHolder>() {
    inner class CustomDiffCallback(
        private val oldList: List<ServiceRulesData>, private val newList: List<ServiceRulesData>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition]._id == newList[newItemPosition]._id
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }


    inner class ViewHolder(private val binding: ItemListDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ServiceRulesData) {
            binding.tvTitle.text = data.title
            binding.tvDetail.text = data.detail
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemListDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = serviceRules.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(serviceRules[position])
    }

    fun addData(newData: List<ServiceRulesData>) {
        val diffResult = DiffUtil.calculateDiff(CustomDiffCallback(serviceRules, newData))
        serviceRules.clear()
        serviceRules.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    fun clearData() {
        val diffResult = DiffUtil.calculateDiff(CustomDiffCallback(serviceRules, emptyList()))
        serviceRules.clear()
        diffResult.dispatchUpdatesTo(this)
    }

}