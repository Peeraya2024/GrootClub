package com.example.grootclub.ui.booking.subbooking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.appzaza.base.BaseFragment
import com.example.grootclub.databinding.FragmentBookingInfomationBinding

class BookingInformationFragment : BaseFragment<FragmentBookingInfomationBinding>() {
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBookingInfomationBinding
        get() = FragmentBookingInfomationBinding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {
        initView()
        setObserveData()
        setOnClicks()
    }

    private fun initView() {}

    private fun setOnClicks() {}

    private fun setObserveData() {}

}