package com.example.grootclub.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.example.appzaza.base.BaseFragment
import com.example.grootclub.databinding.FragmentDashboardBinding
import com.example.grootclub.ui.booking.BookingMain
import com.example.grootclub.utils.loadImage

class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDashboardBinding
        get() = FragmentDashboardBinding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {
        initView()
        setOnClick()
    }

    private fun initView() {
        binding.imvProfile.loadImage("") //รูปโปรไฟล์
    }

    private fun setOnClick() {
        binding.btnAddActivity.setOnClickListener {
            Intent(requireContext(), BookingMain::class.java).also { startActivity(it) }
        }
    }

}