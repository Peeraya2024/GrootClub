package com.example.grootclub.ui.booking.subbooking

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appzaza.base.BaseFragment
import com.example.grootclub.R
import com.example.grootclub.SharedViewModel
import com.example.grootclub.adapter.CourtAdapter
import com.example.grootclub.data.MockData
import com.example.grootclub.data.TimeTableBookingModelItem
import com.example.grootclub.databinding.FragmentChooseCourtsBinding
import com.example.grootclub.utils.GlobalVar
import com.example.grootclub.utils.getCurrentFormattedDate
import com.example.grootclub.utils.getCurrentFormattedDatePlusOneDay
import com.example.grootclub.utils.sharedpreferences.SharedPreference
import com.google.gson.Gson

class ChooseCourtsFragment : BaseFragment<FragmentChooseCourtsBinding>() {
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentChooseCourtsBinding
        get() = FragmentChooseCourtsBinding::inflate

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var sharedPref: SharedPreference
    var adapter: CourtAdapter? = null
    private val buttonClickedMap: MutableMap<Int, Boolean> = mutableMapOf()
    private var bookingInfo: MockData.Companion.BookingInformation? = null

    // เพิ่มตัวแปรเพื่อเก็บสถานะการคลิกของแต่ละส่วน
    private var isDateClicked: Boolean = false
    private var isTimeClicked: Boolean = false
    private var isCoachClicked: Boolean = false

    private val buttonDateClickedMap: MutableMap<Int, Boolean> = mutableMapOf()
    private val buttonTimeClickedMap: MutableMap<Int, Boolean> = mutableMapOf()
    private val buttonCoachClickedMap: MutableMap<Int, Boolean> = mutableMapOf()

    private var sportName: String = ""
    private var courtNumber = 0
    private var date: String = ""
    private var time: String = ""
    private var coach: String = ""


    override fun prepareView(savedInstanceState: Bundle?) {
        sharedPref = SharedPreference(this.requireContext())
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        adapter = CourtAdapter(mutableListOf())
        clearData()

        setObserveData()
        setOnClicks()
    }

    @SuppressLint("SetTextI18n")
    private fun initView(data: List<TimeTableBookingModelItem>) {
        binding.tvActivity.text = "Activity: $sportName"
        if (data.isNotEmpty()) {
            setData(data)
        } else {
            Toast.makeText(requireContext(), "No data", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setData(data: List<TimeTableBookingModelItem>) {
        binding.rcvCourts.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rcvCourts.adapter = adapter
        adapter?.addData(data)
        adapter?.notifyDataSetChanged()
        adapter?.onItemSelect = {
//            Toast.makeText(this.requireContext(), "View Clicked ${it.courtNumber}", Toast.LENGTH_SHORT).show()
            courtNumber = it.courtNumber

            enabledViewData(true)

        }
    }

    private fun setOnClicks() {

        //Date
        binding.btnToday.setOnClickListener {
            date = getCurrentFormattedDate()
            updateClickedStatus(R.id.btnToday)
        }
        binding.btnTomorrow.setOnClickListener {
            date = getCurrentFormattedDatePlusOneDay()
            updateClickedStatus(R.id.btnTomorrow)
        }

        //Time
        binding.time1.setOnClickListener {
            time = "06:00"
            updateClickedStatus(R.id.time1)
        }
        binding.time2.setOnClickListener {
            time = "07:00"
            updateClickedStatus(R.id.time2)
        }
        binding.time3.setOnClickListener {
            time = "08:00"
            updateClickedStatus(R.id.time3)
        }
        binding.time4.setOnClickListener {
            time = "09:00"
            updateClickedStatus(R.id.time4)
        }
        binding.time5.setOnClickListener {
            time = "10:00"
            updateClickedStatus(R.id.time5)
        }
        binding.time6.setOnClickListener {
            time = "11:00"
            updateClickedStatus(R.id.time6)
        }
        binding.time7.setOnClickListener {
            time = "12:00"
            updateClickedStatus(R.id.time7)
        }
        binding.time8.setOnClickListener {
            time = "13:00"
            updateClickedStatus(R.id.time8)
        }
        binding.time9.setOnClickListener {
            time = "14:00"
            updateClickedStatus(R.id.time9)
        }
        binding.time10.setOnClickListener {
            time = "15:00"
            updateClickedStatus(R.id.time10)
        }
        binding.time11.setOnClickListener {
            time = "16:00"
            updateClickedStatus(R.id.time11)
        }
        binding.time12.setOnClickListener {
            time = "17:00"
            updateClickedStatus(R.id.time12)
        }
        binding.time13.setOnClickListener {
            time = "18:00"
            updateClickedStatus(R.id.time13)
        }
        binding.time14.setOnClickListener {
            time = "19:00"
            updateClickedStatus(R.id.time14)
        }
        binding.time15.setOnClickListener {
            time = "20:00"
            updateClickedStatus(R.id.time15)
        }
        binding.time16.setOnClickListener {
            time = "21:00"
            updateClickedStatus(R.id.time16)
        }
        binding.time17.setOnClickListener {
            time = "22:00"
            updateClickedStatus(R.id.time17)
        }

        //Coach
        binding.btnCoach.setOnClickListener {
            coach = "Coach"
            updateClickedStatus(R.id.btnCoach)
        }
        binding.btnNoCoach.setOnClickListener {
            coach = "No Coach"
            updateClickedStatus(R.id.btnNoCoach)
        }
    }

    // สร้างฟังก์ชันเพื่ออัพเดทสถานะการคลิกของแต่ละส่วน
    private fun updateClickedStatus(clickedId: Int) {
        when (clickedId) {
            R.id.btnToday, R.id.btnTomorrow -> {
                isDateClicked = true
                isTimeClicked = false
                isCoachClicked = false
                updateButtonDate(clickedId)
            }

            R.id.time1, R.id.time2, R.id.time3, R.id.time4, R.id.time5, R.id.time6,
            R.id.time7, R.id.time8, R.id.time9, R.id.time10, R.id.time11, R.id.time12,
            R.id.time13, R.id.time14, R.id.time15, R.id.time16, R.id.time17 -> {
                isDateClicked = true
                isTimeClicked = true
                isCoachClicked = false
                updateButtonTime(clickedId)
            }

            R.id.btnCoach, R.id.btnNoCoach -> {
                isDateClicked = true
                isTimeClicked = true
                isCoachClicked = true
                updateButtonCoach(clickedId)
            }
        }
        bookingInfo = MockData.Companion.BookingInformation(
            sportName,
            courtNumber,
            date,
            time,
            coach
        )

        Log.e(
            "senDataBundle",
            "senDataBundle coach: ${bookingInfo!!.coach} sportName: ${bookingInfo!!.sportName} courtNumber: ${bookingInfo!!.courtNumber} date: ${bookingInfo!!.date} time: ${bookingInfo!!.time}"
        )

//
//        GlobalVar.bookingInfo = bookingInfo as MockData.Companion.BookingInformation
        sharedViewModel.setBookingInfo(bookingInfo!!)
//        sharedPref.setValueString("bookingInfo", Gson().toJson(bookingInfo))
    }

    private fun updateButtonDate(clickedButtonId: Int) {
        buttonDateClickedMap[clickedButtonId] = true

        buttonDateClickedMap.keys.forEach { buttonId ->
            val button = requireView().findViewById<AppCompatButton>(buttonId)
            button.setBackgroundResource(R.drawable.bg_rounded_button_gray)
        }
        val clickedButton = requireView().findViewById<AppCompatButton>(clickedButtonId)
        clickedButton.setBackgroundResource(R.drawable.bg_rounded_corner_button)

        if (clickedButtonId == R.id.btnToday || clickedButtonId == R.id.btnTomorrow) {
            enabledViewTime(true)
        } else if (
            clickedButtonId == R.id.time1 || clickedButtonId == R.id.time2 || clickedButtonId == R.id.time3 || clickedButtonId == R.id.time4 ||
            clickedButtonId == R.id.time5 || clickedButtonId == R.id.time6 || clickedButtonId == R.id.time7 || clickedButtonId == R.id.time8 ||
            clickedButtonId == R.id.time9 || clickedButtonId == R.id.time10 || clickedButtonId == R.id.time11 || clickedButtonId == R.id.time12 ||
            clickedButtonId == R.id.time13 || clickedButtonId == R.id.time14 || clickedButtonId == R.id.time15 || clickedButtonId == R.id.time16 || clickedButtonId == R.id.time17
        ) {
            enabledViewCoach(true)
        }
    }

    private fun updateButtonTime(clickedButtonId: Int) {
        buttonTimeClickedMap[clickedButtonId] = true

        buttonTimeClickedMap.keys.forEach { buttonId ->
            val button = requireView().findViewById<AppCompatButton>(buttonId)
            button.setBackgroundResource(R.drawable.bg_rounded_button_gray)
        }
        val clickedButton = requireView().findViewById<AppCompatButton>(clickedButtonId)
        clickedButton.setBackgroundResource(R.drawable.bg_rounded_corner_button)

        if (clickedButtonId == R.id.btnToday || clickedButtonId == R.id.btnTomorrow) {
            enabledViewTime(true)
        } else if (
            clickedButtonId == R.id.time1 || clickedButtonId == R.id.time2 || clickedButtonId == R.id.time3 || clickedButtonId == R.id.time4 ||
            clickedButtonId == R.id.time5 || clickedButtonId == R.id.time6 || clickedButtonId == R.id.time7 || clickedButtonId == R.id.time8 ||
            clickedButtonId == R.id.time9 || clickedButtonId == R.id.time10 || clickedButtonId == R.id.time11 || clickedButtonId == R.id.time12 ||
            clickedButtonId == R.id.time13 || clickedButtonId == R.id.time14 || clickedButtonId == R.id.time15 || clickedButtonId == R.id.time16 || clickedButtonId == R.id.time17
        ) {
            enabledViewCoach(true)
        }
    }

    private fun updateButtonCoach(clickedButtonId: Int) {
        buttonCoachClickedMap[clickedButtonId] = true

        buttonCoachClickedMap.keys.forEach { buttonId ->
            val button = requireView().findViewById<AppCompatButton>(buttonId)
            button.setBackgroundResource(R.drawable.bg_rounded_button_gray)
        }
        val clickedButton = requireView().findViewById<AppCompatButton>(clickedButtonId)
        clickedButton.setBackgroundResource(R.drawable.bg_rounded_corner_button)

        if (clickedButtonId == R.id.btnToday || clickedButtonId == R.id.btnTomorrow) {
            enabledViewTime(true)
        } else if (
            clickedButtonId == R.id.time1 || clickedButtonId == R.id.time2 || clickedButtonId == R.id.time3 || clickedButtonId == R.id.time4 ||
            clickedButtonId == R.id.time5 || clickedButtonId == R.id.time6 || clickedButtonId == R.id.time7 || clickedButtonId == R.id.time8 ||
            clickedButtonId == R.id.time9 || clickedButtonId == R.id.time10 || clickedButtonId == R.id.time11 || clickedButtonId == R.id.time12 ||
            clickedButtonId == R.id.time13 || clickedButtonId == R.id.time14 || clickedButtonId == R.id.time15 || clickedButtonId == R.id.time16 || clickedButtonId == R.id.time17
        ) {
            enabledViewCoach(true)
        }
    }

    private fun enabledViewData(enabled: Boolean) {
        if (enabled) {
            binding.btnToday.isEnabled = true
            binding.btnTomorrow.isEnabled = true
        } else {
            binding.btnToday.isEnabled = false
            binding.btnTomorrow.isEnabled = false
        }
    }

    private fun enabledViewTime(enabled: Boolean) {
        if (enabled) {
            binding.time1.isEnabled = true
            binding.time2.isEnabled = true
            binding.time3.isEnabled = true
            binding.time4.isEnabled = true
            binding.time5.isEnabled = true
            binding.time6.isEnabled = true
            binding.time7.isEnabled = true
            binding.time8.isEnabled = true
            binding.time9.isEnabled = true
            binding.time10.isEnabled = true
            binding.time11.isEnabled = true
            binding.time12.isEnabled = true
            binding.time13.isEnabled = true
            binding.time14.isEnabled = true
            binding.time15.isEnabled = true
            binding.time16.isEnabled = true
            binding.time17.isEnabled = true
        } else {
            binding.time1.isEnabled = false
            binding.time2.isEnabled = false
            binding.time3.isEnabled = false
            binding.time4.isEnabled = false
            binding.time5.isEnabled = false
            binding.time6.isEnabled = false
            binding.time7.isEnabled = false
            binding.time8.isEnabled = false
            binding.time9.isEnabled = false
            binding.time10.isEnabled = false
            binding.time11.isEnabled = false
            binding.time12.isEnabled = false
            binding.time13.isEnabled = false
            binding.time14.isEnabled = false
            binding.time15.isEnabled = false
            binding.time16.isEnabled = false
            binding.time17.isEnabled = false
        }
    }

    private fun enabledViewCoach(enabled: Boolean) {
        if (enabled) {
            binding.btnCoach.isEnabled = true
            binding.btnNoCoach.isEnabled = true
        } else {
            binding.btnCoach.isEnabled = false
            binding.btnNoCoach.isEnabled = false
        }

    }

    fun enableView(enabled: Boolean) {
        enabledViewData(enabled)
        enabledViewTime(enabled)
        enabledViewCoach(enabled)
    }

    private fun setObserveData() {
        sharedViewModel.getSelectedSport().observe(this) {
            Log.e("setObserveData", "setObserveData: $it")
            binding.tvActivity.text = "Activity: $it"
        }
    }

    fun updateDataFromActivity(data: List<TimeTableBookingModelItem>, sportCourt: String) {
        sportName = sportCourt.replace("Court", "")
        // ทำการอัพเดทข้อมูลที่ได้รับจาก Activity และปรับปรุงหน้าจอตามต้องการ
        initView(data)
    }

    fun senDataBundle() {
//        sharedViewModel.setBookingInfo(bookingInfo!!)
//        bookingInfo?.let { sharedViewModel.setBookingInfo(it) }

        val bookingInfo = MockData.Companion.BookingInformation(
            sportName,
            courtNumber,
            date,
            time,
            coach
        )
        Log.e(
            "senDataBundle",
            "senDataBundle coach: ${bookingInfo.coach} sportName: ${bookingInfo.sportName} courtNumber: ${bookingInfo.courtNumber} date: ${bookingInfo.date} time: ${bookingInfo.time}"
        )
        val fragment = InformationFragment().apply {
            arguments = Bundle().apply {
                putParcelable("booking_info", bookingInfo)
            }
        }
        fragment.arguments?.let { replaceFragmentWithBundle(fragment, it, R.id.layoutMain) }

//        val bookingInfo = MockData.Companion.BookingInformation(
//            sportName,
//            courtNumber,
//            date,
//            time,
//            coach
//        )
//        GlobalVar.bookingInfo = bookingInfo

        //sharedPref
//        sharedPref.setValueString("bookingInfo", Gson().toJson(bookingInfo))
    }

    fun checkField(): Boolean {
        return !(courtNumber <= 0 || date == "" || time == "" ||
                coach == "")
    }

    fun clearData() {
        adapter?.clearData()
    }

    fun clearSelectedItem() {
        // ทำการเคลียร์การคลิก Item ใน Adapter
        adapter?.clearSelectedItem()
    }
}