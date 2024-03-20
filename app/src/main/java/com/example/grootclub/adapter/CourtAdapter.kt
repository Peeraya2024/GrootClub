package com.example.grootclub.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.grootclub.R
import com.example.grootclub.data.TimeTableBookingModelItem
import com.example.grootclub.databinding.ItemSportBinding

class CourtAdapter(
    private var court: MutableList<TimeTableBookingModelItem>,
    var onItemSelect: ((data: TimeTableBookingModelItem) -> Unit)? = null
) : RecyclerView.Adapter<CourtAdapter.ViewHolder>() {

    private var selectedItemPosition: Int = -1
    private val selectedItems = mutableListOf<TimeTableBookingModelItem>()

    class ViewHolder(private val binding: ItemSportBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(court: TimeTableBookingModelItem) {
            binding.tvCourt.text = court.courtNumber.toString()
        }
    }

    inner class CustomDiffCallback(
        private val oldList: List<TimeTableBookingModelItem>,
        private val newList: List<TimeTableBookingModelItem>
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSportBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(court[position])

        if (selectedItemPosition == position) {
            holder.itemView.setBackgroundResource(R.drawable.background_gradient)
            if (!selectedItems.contains(court[position])) {
                holder.itemView.setBackgroundResource(android.R.color.white)
            }
        } else {
            holder.itemView.setBackgroundResource(android.R.color.white)
        }

        holder.itemView.setOnClickListener {
            court[position].isButtonSelected = !court[position].isButtonSelected
            // ตรวจสอบว่ารายการที่ถูกคลิกถูกเลือกไว้แล้วหรือไม่
            val previouslySelectedItemPosition = selectedItemPosition
            selectedItemPosition = holder.adapterPosition

            // อัปเดต UI สำหรับรายการที่เลือกไว้ก่อนหน้านี้
            if (previouslySelectedItemPosition != -1) {
                notifyItemChanged(previouslySelectedItemPosition)
            }
            // อัปเดต UI สำหรับรายการที่เลือกในปัจจุบัน
            notifyItemChanged(selectedItemPosition)

            // call back สถานะของรายการที่เลือก
            onItemSelect?.invoke(court[selectedItemPosition])
            selectItem(court[position])
        }
    }

    override fun getItemCount(): Int = court.size

    fun addData(newData: List<TimeTableBookingModelItem>) {
        val oldSize = court.size
        court.addAll(newData)
        val newSize = court.size
        val oldList = ArrayList(court.subList(0, oldSize))
        val newList = ArrayList(court.subList(oldSize, newSize))
        val diffResult = DiffUtil.calculateDiff(CustomDiffCallback(oldList, newList))
        diffResult.dispatchUpdatesTo(this)
    }

    fun removeItem(position: Int) {
        court.removeAt(position)
        notifyItemRemoved(position)
    }

    fun clearData() {
        val size = court.size
        court.clear()
        notifyItemRangeRemoved(0, size)
    }

    // เพิ่มเมธอดนี้เพื่อล้างรายการ Item ที่ถูกเลือก
    fun clearSelectedItem() {
        selectedItems.clear()
        notifyDataSetChanged()
    }

    // เพิ่มเมธอดนี้เพื่อเพิ่ม Item ที่ถูกเลือก
    private fun selectItem(item: TimeTableBookingModelItem) {
        selectedItems.add(item)
        notifyDataSetChanged()
    }


}