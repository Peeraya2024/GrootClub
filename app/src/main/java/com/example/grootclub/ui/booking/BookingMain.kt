package com.example.grootclub.ui.booking

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.appzaza.base.BaseActivity
import com.example.grootclub.R
import com.example.grootclub.SharedViewModel
import com.example.grootclub.adapter.ViewPageAdapter
import com.example.grootclub.data.Remote.ApiService
import com.example.grootclub.data.Remote.Repository.Home.CoachRepository
import com.example.grootclub.databinding.BookingMainBinding
import com.example.grootclub.ui.booking.subbooking.BookingInformationFragment
import com.example.grootclub.ui.booking.subbooking.ChooseCourtsFragment
import com.example.grootclub.ui.booking.subbooking.ChooseSportFragment
import com.example.grootclub.ui.booking.subbooking.InformationFragment
import com.example.grootclub.ui.coach.CoachVM
import com.example.grootclub.ui.coach.CoachVMFactory
import com.example.grootclub.ui.dashboard.DashboardFragment
import com.example.grootclub.utils.getCurrentFormattedDate

class BookingMain : BaseActivity<BookingMainBinding>() {
    override val bindLayout: (LayoutInflater) -> BookingMainBinding
        get() = BookingMainBinding::inflate

    private lateinit var viewModel: CoachVM
    private lateinit var repository: CoachRepository
    private lateinit var sharedViewModel: SharedViewModel

    private val array = ArrayList<Fragment>()
    private var currentPage: Int = 0
    private var sportCourt: String = ""


    override fun prepareView(savedInstanceState: Bundle?) {
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        repository = CoachRepository(ApiService().getService())
        viewModel = ViewModelProvider(this, CoachVMFactory(repository))[CoachVM::class.java]
        array.clear()
        arrayView()
        setViewPager()

        initView()
        initToolBar()
        setObserveData()
        setOnClicks()
    }

    private fun setViewPager() {
        val adapter = ViewPageAdapter(this.supportFragmentManager, array)
        binding.viewPager.adapter = adapter
        binding.viewPager.setSwipePagingEnabled(false)
        binding.viewPager.offscreenPageLimit = 3
    }

    private fun arrayView() {
        array.add(ChooseSportFragment())
        array.add(ChooseCourtsFragment())
        array.add(InformationFragment())
        array.add(BookingInformationFragment())
        array.add(DashboardFragment())
    }

    private fun initView() {
//        showProgressDialog()
    }

    private fun initToolBar() {
        binding.appBarMain.toolbar.toolbarTitle.text = "Booking"
        binding.appBarMain.toolbar.btnIconStart.visibility = View.VISIBLE
        binding.appBarMain.toolbar.btnIconStart.setBackgroundResource(R.drawable.ic_arrow_back)
        binding.appBarMain.toolbar.btnIconStart.setOnClickListener {
            val pageScreen = array[currentPage]
            if (pageScreen is DashboardFragment) {
                navigateToChooseSportFragment()
            } else {
                this.finish()

            }
        }

    }

    private fun setOnClicks() {
        binding.txtNext.setOnClickListener {
            nextPage()
        }

        binding.txtBack.setOnClickListener {
            backPage()
        }
    }

    private fun setObserveData() {
        sharedViewModel.getSelectedSport().observe(this) { sport ->
            showProgressDialog()
            Log.e("initView", "getSelectedSport:: $sport")
            this.sportCourt = sport
            val dateCurrent = getCurrentFormattedDate()
            viewModel.fetchTimeTableBooking(sportCourt, "2024-03-17")
        }

        viewModel.timeTableBooking.observe(this) { data ->
            hideProgressDialog()
            Log.e("initView", "timeTableBooking:: $data")
            if (data != null && data.isNotEmpty()) {
                nextPage()
                val pageScreen = array[1]
                if (pageScreen is ChooseCourtsFragment) {
                    pageScreen.updateDataFromActivity(data)
                }
            } else {
                Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun nextPage() {
        when (currentPage) {
            0 -> {
                val pageScreen = array[0]
                if (pageScreen is ChooseSportFragment) {
                    binding.viewPager.currentItem = binding.viewPager.currentItem + 1
                    currentPage = binding.viewPager.currentItem
                    binding.txtBack.visibility = View.VISIBLE
                    binding.txtNext.visibility = View.VISIBLE
                    binding.appBarMain.toolbar.btnIconStart.visibility = View.GONE
                    binding.txtNext.text = this.getString(R.string.next)
                }
            }

            1 -> {
                val pageScreen = array[1]
                if (pageScreen is ChooseCourtsFragment) {
                    binding.viewPager.currentItem = binding.viewPager.currentItem + 1
                    currentPage = binding.viewPager.currentItem
                    binding.txtBack.visibility = View.VISIBLE
                    binding.txtNext.visibility = View.VISIBLE
                    binding.txtNext.text = this.getString(R.string.next)
                }
            }

            2 -> {
                val pageScreen = array[2]
                if (pageScreen is InformationFragment) {
                    binding.viewPager.currentItem = binding.viewPager.currentItem + 1
                    currentPage = binding.viewPager.currentItem
                    binding.txtBack.visibility = View.VISIBLE
                    binding.txtNext.visibility = View.VISIBLE
                    binding.txtNext.text = this.getString(R.string.submit)
                }
            }

            3 -> {
                val pageScreen = array[3]
                if (pageScreen is BookingInformationFragment) {
                    binding.appBarMain.toolbar.btnIconStart.visibility = View.VISIBLE
                    binding.viewPager.currentItem = binding.viewPager.currentItem + 1
                    currentPage = binding.viewPager.currentItem
                    binding.txtBack.visibility = View.GONE
                    binding.txtNext.visibility = View.GONE
                }
            }
        }
    }

    private fun backPage() {
        when (currentPage) {
            1 -> {
                binding.appBarMain.toolbar.btnIconStart.visibility = View.VISIBLE
                binding.txtBack.visibility = View.GONE
                binding.txtNext.visibility = View.GONE
                binding.viewPager.currentItem = binding.viewPager.currentItem - 1
                currentPage = binding.viewPager.currentItem
            }

            2 -> {
                binding.txtBack.visibility = View.VISIBLE
                binding.txtNext.visibility = View.VISIBLE
                binding.txtNext.text = this.getString(R.string.next)

                binding.viewPager.currentItem = binding.viewPager.currentItem - 1
                currentPage = binding.viewPager.currentItem
            }

            3 -> {
                binding.txtBack.visibility = View.VISIBLE
                binding.txtNext.visibility = View.VISIBLE
                binding.txtNext.text = this.getString(R.string.next)

                binding.viewPager.currentItem = binding.viewPager.currentItem - 1
                currentPage = binding.viewPager.currentItem
            }
        }
    }

    private fun navigateToChooseSportFragment() {
        val chooseSportFragment = array[0]
        if (chooseSportFragment is ChooseSportFragment) {
            binding.viewPager.currentItem = array.indexOf(chooseSportFragment)
            currentPage = array.indexOf(chooseSportFragment)
            binding.txtBack.visibility = View.GONE
            binding.txtNext.visibility = View.GONE
            binding.appBarMain.toolbar.btnIconStart.visibility = View.VISIBLE
        }
    }
}