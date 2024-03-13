package com.example.grootclub.ui.booking.subbooking

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.appzaza.base.BaseFragment
import com.example.grootclub.SharedViewModel
import com.example.grootclub.data.CoachListModelItem
import com.example.grootclub.data.Remote.ApiService
import com.example.grootclub.data.Remote.Repository.Home.CoachRepository
import com.example.grootclub.databinding.FragmentChooseSportBinding
import com.example.grootclub.ui.coach.CoachVM
import com.example.grootclub.ui.coach.CoachVMFactory


class ChooseSportFragment : BaseFragment<FragmentChooseSportBinding>() {
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentChooseSportBinding
        get() = FragmentChooseSportBinding::inflate

    private lateinit var sharedViewModel: SharedViewModel

    override fun prepareView(savedInstanceState: Bundle?) {
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        initView()
        setOnClicks()
    }

    private fun initView() {}

    private fun setOnClicks() {
        binding.btnTennis.setOnClickListener {
            sentDataIntent("TennisCourt")
        }

        binding.btnBadminton.setOnClickListener {
            sentDataIntent("BadmintonCourt")
        }

        binding.btnTableTennis.setOnClickListener {
            sentDataIntent("TableTennisCourt")
        }

        binding.btnYoga.setOnClickListener {
            sentDataIntent("YogaCourt")
        }

        binding.btnAerobic.setOnClickListener {
            sentDataIntent("AerobicCourt")
        }
    }

    private fun sentDataIntent(sport : String) {
        sharedViewModel.setSelectedSport(sport)
    }

}