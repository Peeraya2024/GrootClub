package com.example.grootclub.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.grootclub.SharedViewModel
import com.example.grootclub.adapter.CoachItemClickListener
import com.example.grootclub.adapter.ServiceRulesAdapter
import com.example.grootclub.adapter.TimeTableBookingAdapter
import com.example.grootclub.data.MockData
import com.example.grootclub.data.ProductModel
import com.example.grootclub.data.Remote.ApiService
import com.example.grootclub.data.Remote.Repository.Home.CoachRepository
import com.example.grootclub.databinding.FragmentHomeBinding
import com.example.grootclub.ui.coach.CoachAdapter
import com.example.grootclub.ui.coach.CoachVM
import com.example.grootclub.ui.coach.CoachVMFactory
import com.example.grootclub.utils.GlobalVar
import com.example.grootclub.utils.SafeClickListener
import com.example.grootclub.utils.initDatePickerCurrentDateTomorrow
import com.example.grootclub.utils.setArrayAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private var adapterTimeTableBooking = TimeTableBookingAdapter(arrayListOf())
    private var adapterCoach = CoachAdapter(arrayListOf())
    private var adapterServiceRules = ServiceRulesAdapter(arrayListOf())
    private val binding get() = _binding!!
    private var coachItemClickListener: CoachItemClickListener? = null
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var viewModel: CoachVM
    private lateinit var repository: CoachRepository

    interface HomeItemClickListener {
        fun onItemClicked(item: ProductModel.Stadium)
    }

    // สร้างเมทอด setHomeItemClickListener เพื่อให้คุณสามารถตั้งค่า Callback จากหน้า Main Activity
    fun setCoachItemClickListener(listener: CoachItemClickListener) {
        coachItemClickListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        repository = CoachRepository(ApiService().getService())
        viewModel = ViewModelProvider(this, CoachVMFactory(repository))[CoachVM::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.fetchAllCoach()
        viewModel.fetchTimeTableBooking()

        adapterTimeTableBooking.clearData()
        adapterCoach.clearData()
        adapterServiceRules.clearData()

        iniView()
        setRecyclerView()
        iniViewFlipper()
        setOnClicks()

        return root

    }

    private fun iniView() {

    }

    private fun iniViewFlipper() {
        val inAnimation = android.view.animation.AnimationUtils.loadAnimation(
            this.requireContext(), android.R.anim.slide_in_left
        )
        val outAnimation = android.view.animation.AnimationUtils.loadAnimation(
            this.requireContext(), android.R.anim.slide_out_right
        )
        binding.viewFlipper.inAnimation = inAnimation
        binding.viewFlipper.outAnimation = outAnimation

        for (i in MockData.imageList) {
            val imageView = android.widget.ImageView(this.requireContext())
            imageView.setImageResource(i)
            imageView.layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT
            )
            binding.viewFlipper.addView(imageView)
        }

        binding.imvBack.setOnClickListener {
            binding.viewFlipper.showPrevious()
        }

        binding.imvForward.setOnClickListener {
            binding.viewFlipper.showNext()
        }

    }

    private fun setOnClicks() {
        binding.lyCalendar.setOnClickListener(SafeClickListener {
            initDatePickerCurrentDateTomorrow(this.requireContext(), binding.etCalendar)
        })

        binding.spnTypeSpot.adapter = setArrayAdapter(requireContext(), GlobalVar.typeSpotList)

        binding.spnTypeSpot.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
//                Toast.makeText(
//                    requireContext(), GlobalVar.typeSpotList[position], Toast.LENGTH_SHORT
//                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(requireContext(), "Nothing Selected", Toast.LENGTH_SHORT).show()
            }

        }

        binding.btnGetStart.setOnClickListener {
            Toast.makeText(this.requireContext(), "ยังไม่ทำจ้าแม่ป้า สาวววว", Toast.LENGTH_SHORT)
                .show()
        }

        binding.btnSeeMore.setOnClickListener {
            Toast.makeText(this.requireContext(), "See More", Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setRecyclerView() {
        binding.progress.visibility = View.VISIBLE
        viewModel.coachList.observe(viewLifecycleOwner) { coachList ->
            val layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rcvCoach.layoutManager = layoutManager
            binding.rcvCoach.adapter = adapterCoach
            adapterCoach.addData(coachList)
            adapterCoach.notifyDataSetChanged()
            binding.progress.visibility = View.GONE
            adapterCoach.coachItemClickListener = coachItemClickListener

            adapterCoach.setOnItemSelect { coach ->
                sharedViewModel.selectCoach(coach)
            }
            mockData()
        }


        viewModel.timeTableBooking.observe(viewLifecycleOwner){data ->
            binding.rcvCoach.layoutManager = LinearLayoutManager(requireContext())
            binding.rcvCoach.adapter = adapterTimeTableBooking
            adapterTimeTableBooking.addData(data)
            adapterTimeTableBooking.notifyDataSetChanged()
            binding.progress.visibility = View.GONE
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun mockData() {
        binding.rcvServiceRules.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rcvServiceRules.adapter = adapterServiceRules
        // เพิ่มข้อมูลลงใน RecyclerView Adapter
        adapterServiceRules.addData(MockData.serviceRules)
        // แจ้งให้ RecyclerView ทราบว่าข้อมูลเปลี่ยนแปลงและต้องทำการอัปเดต
        adapterServiceRules.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}