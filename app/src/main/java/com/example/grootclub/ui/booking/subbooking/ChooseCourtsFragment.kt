package com.example.grootclub.ui.booking.subbooking

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.appzaza.base.BaseFragment
import com.example.grootclub.SharedViewModel
import com.example.grootclub.data.TimeTableBookingModelItem
import com.example.grootclub.databinding.FragmentChooseCourtsBinding

class ChooseCourtsFragment : BaseFragment<FragmentChooseCourtsBinding>() {
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentChooseCourtsBinding
        get() = FragmentChooseCourtsBinding::inflate

    private lateinit var sharedViewModel: SharedViewModel


    override fun prepareView(savedInstanceState: Bundle?) {
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        setObserveData()
        setOnClicks()
    }

    @SuppressLint("SetTextI18n")
    private fun initView(data: List<TimeTableBookingModelItem>) {
        if (data.isNotEmpty()) {
            data.forEach { it ->
                Log.e("setObserveData", "setObserveData: courtNumber: ${it.courtNumber} slots: ${it.slots}")
            }
        } else {
            Toast.makeText(requireContext(), "No data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setOnClicks() {}

    private fun setObserveData() {
        sharedViewModel.getSelectedSport().observe(this) {
            Log.e("setObserveData", "setObserveData: $it")
        }
    }

    fun updateDataFromActivity(data: List<TimeTableBookingModelItem>) {
        // ทำการอัพเดทข้อมูลที่ได้รับจาก Activity และปรับปรุงหน้าจอตามต้องการ
        initView(data)
    }

}