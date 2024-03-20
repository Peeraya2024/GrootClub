package com.example.grootclub.ui.booking.subbooking

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.appzaza.base.BaseFragment
import com.example.grootclub.R
import com.example.grootclub.SharedViewModel
import com.example.grootclub.data.MockData
import com.example.grootclub.databinding.FragmentInformationBinding
import com.example.grootclub.utils.GlobalVar
import com.example.grootclub.utils.sharedpreferences.SharedPreference
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InformationFragment : BaseFragment<FragmentInformationBinding>() {
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentInformationBinding
        get() = FragmentInformationBinding::inflate

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var sharedPref: SharedPreference

    private var bookingInfo: MockData.Companion.BookingInformation? = null
    private var sportName: String? = null
    private var courtNumber: Int = 0
    private var email: String? = null
    private var date: String? = null
    private var time: String? = null
    private var coach: String? = null

    override fun prepareView(savedInstanceState: Bundle?) {
        sharedPref = SharedPreference(this.requireContext())
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        val bundle = arguments
        if (bundle != null) {
            bookingInfo = bundle.getParcelable("booking_info")
            if (bookingInfo != null) {
                sportName = bookingInfo?.sportName
                courtNumber = bookingInfo?.courtNumber ?: 0
                date = bookingInfo?.date
                time = bookingInfo?.time
                coach = bookingInfo?.coach

                binding.edtSport.setText(sportName)
                binding.edtLocation.setText(courtNumber.toString())
                binding.edtDate.setText(date)
                binding.edtTimeFromTo.setText(time)
                binding.edtCoach.setText(coach)
                Log.e("senDataBundle", "Receive date sport_name: $sportName sport: $courtNumber date: $date time: $time coach: $coach" )
            } else {
                Toast.makeText(context, "bookingInfo is null", Toast.LENGTH_SHORT).show()
            }

        }

//        if (GlobalVar.bookingInfo != null) {
//            bookingInfo = GlobalVar.bookingInfo
//            binding.edtSport.setText(bookingInfo?.sportName)
//            binding.edtLocation.setText(bookingInfo?.courtNumber.toString())
//            binding.edtDate.setText(bookingInfo?.date)
//            binding.edtTimeFromTo.setText(bookingInfo?.time)
//            binding.edtCoach.setText(bookingInfo?.coach)
//        } else {
//            Toast.makeText(context, "bookingInfo is null", Toast.LENGTH_SHORT).show()
//        }

        //sharedPref
//        val bookingInfoJson = sharedPref.getValueString("bookingInfo")
//        if (!bookingInfoJson.isNullOrEmpty()) {
//            val bookingInfo = Gson().fromJson(bookingInfoJson, MockData.Companion.BookingInformation::class.java)
//            // ตอนนี้ bookingInfo คืออ็อบเจ็กต์ของ BookingInformation
//            // สามารถใช้ bookingInfo ได้ตามปกติ
//            binding.edtSport.setText(bookingInfo.sportName)
//            binding.edtLocation.setText(bookingInfo.courtNumber.toString())
//            binding.edtDate.setText(bookingInfo.date)
//            binding.edtTimeFromTo.setText(bookingInfo.time)
//            binding.edtCoach.setText(bookingInfo.coach)
//        } else {
//            // ไม่พบข้อมูล "bookingInfo" ใน SharedPreferences
//            Toast.makeText(this.requireContext(), "BookingInfo is null", Toast.LENGTH_SHORT).show()
//        }

        //viewModel
//        sharedViewModel.getBookingInfo().observe(viewLifecycleOwner) {
//            if (it != null) {
//                binding.edtSport.setText(it.sportName)
//                binding.edtLocation.setText(it.courtNumber.toString())
//                binding.edtDate.setText(it.date)
//                binding.edtTimeFromTo.setText(it.time)
//                binding.edtCoach.setText(it.coach)
//            } else {
//                Toast.makeText(this.requireContext(), "BookingInfo is null", Toast.LENGTH_SHORT).show()
//            }
//        }

        setObserveData()
        setOnClicks()
    }

    private fun initView() {

    }


    fun updateDataFromActivity(bookingInfo : MockData.Companion.BookingInformation) {
        this.bookingInfo = bookingInfo
        binding.edtDate.setText(bookingInfo.date)
        binding.edtTimeFromTo.setText(bookingInfo.time)
        binding.edtCoach.setText(bookingInfo.coach)

        Log.e("senDataBundle", "Receive date: $date time: $time coach: $coach" )
    }


    private fun setOnClicks() {}

    private fun setObserveData() {
//        sharedViewModel.getBookingInfo().observe(viewLifecycleOwner) {
//            viewLifecycleOwner.lifecycleScope.launch {
////                date = sharedPref.getValueString("date")
//                binding.edtDate.setText(it.date)
//                Log.e("senDataBundle", "Receive date : ${it.date} time: ${it.time} coach: ${it.coach}" )
//            }
//        }
    }


//    companion object {
//        @JvmStatic
//        fun newInstance(dataBooking: MockData.Companion.BookingInformation) = InformationFragment().apply {
//            this.dataBooking = dataBooking
//        }
//    }

}