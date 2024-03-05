package com.example.grootclub.adapter

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.grootclub.R
import com.example.grootclub.data.TimeTableBookingModelItem
import com.example.grootclub.databinding.ItemListTimeBinding

class TimeTableBookingAdapter(
    private val timeTableBooking: ArrayList<TimeTableBookingModelItem>,
    private var onItemSelect: ((data: TimeTableBookingModelItem) -> Unit)? = null
) : RecyclerView.Adapter<TimeTableBookingAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemListTimeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: TimeTableBookingModelItem) {
            // Clear previous views
            binding.tableRow.removeAllViews()

            // Create TextView for the first column to display slot time
            val timeTextView = TextView(binding.root.context).apply {
                layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f
                )
                gravity = Gravity.CENTER
                text = "${data.slots[0].startTime} - ${data.slots[data.slots.size - 1].endTime}"
            }
            binding.tableRow.addView(timeTextView)

            // Create TextViews for the other columns
            for (slot in data.slots) {
                val textView = TextView(binding.root.context).apply {
                    layoutParams = TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1f
                    )
                    gravity = Gravity.CENTER

                    // Set text based on whether the slot is booked
                    text = if (slot.isBooked) {
                        "Test" // Add additional text "Test" if the slot is booked
                    } else {
                        "Available"
                    }

                    // Set background color based on whether the slot is booked
                    setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            if (slot.isBooked) R.color.booked_color else R.color.available_color
                        )
                    )
                }
                binding.tableRow.addView(textView)
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemListTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = timeTableBooking.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(timeTableBooking[position])
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

    fun addData(newData: List<TimeTableBookingModelItem>) {
        val diffResult = DiffUtil.calculateDiff(CustomDiffCallback(timeTableBooking, newData))
        timeTableBooking.clear()
        timeTableBooking.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    fun clearData() {
        val diffResult = DiffUtil.calculateDiff(CustomDiffCallback(timeTableBooking, emptyList()))
        timeTableBooking.clear()
        diffResult.dispatchUpdatesTo(this)
    }


}