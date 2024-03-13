package com.example.grootclub.ui.booking.subbooking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appzaza.base.BaseFragment
import com.example.grootclub.R
import com.example.grootclub.databinding.FragmentInformationBinding

class InformationFragment : BaseFragment<FragmentInformationBinding>() {
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentInformationBinding
        get() = FragmentInformationBinding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {
        initView()
        setObserveData()
        setOnClicks()
    }

    private fun initView() {}

    private fun setOnClicks() {}

    private fun setObserveData() {}

}