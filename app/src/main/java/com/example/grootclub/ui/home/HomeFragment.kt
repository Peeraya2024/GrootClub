package com.example.grootclub.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appzaza.base.BaseFragment
import com.example.grootclub.R
import com.example.grootclub.SharedViewModel
import com.example.grootclub.adapter.CoachItemClickListener
import com.example.grootclub.adapter.ServiceRulesAdapter
import com.example.grootclub.adapter.TimeTableBookingAdapter
import com.example.grootclub.data.MockData
import com.example.grootclub.data.ProductModel
import com.example.grootclub.data.Remote.ApiService
import com.example.grootclub.data.Remote.Repository.Home.CoachRepository
import com.example.grootclub.databinding.FragmentHomeBinding
import com.example.grootclub.ui.coach.CoachActivity
import com.example.grootclub.ui.coach.CoachAdapter
import com.example.grootclub.ui.coach.CoachVM
import com.example.grootclub.ui.coach.CoachVMFactory
import com.example.grootclub.ui.booking.BookingMain
import com.example.grootclub.ui.signIn.SignInActivity
import com.example.grootclub.utils.SafeClickListener
import com.example.grootclub.utils.initDatePickerCurrentDateTomorrow
import com.example.grootclub.utils.mapTypeSpotToApiValue
import com.example.grootclub.utils.marginLayoutParams
import com.example.grootclub.utils.setArrayAdapter
import com.example.grootclub.utils.sharedpreferences.SharedPreference
import java.util.Calendar

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private var adapterTimeTableBooking = TimeTableBookingAdapter(arrayListOf())
    private var adapterCoach = CoachAdapter(arrayListOf())
    private var adapterServiceRules = ServiceRulesAdapter(arrayListOf())
    private var coachItemClickListener: CoachItemClickListener? = null
    private lateinit var sharedPref: SharedPreference
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var viewModel: CoachVM
    private lateinit var repository: CoachRepository
    private val margin = 1
    private var positionTypeSpot = 0

    interface HomeItemClickListener {
        fun onItemClicked(item: ProductModel.Stadium)
    }

    override fun prepareView(savedInstanceState: Bundle?) {
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        repository = CoachRepository(ApiService().getService())
        viewModel = ViewModelProvider(this, CoachVMFactory(repository))[CoachVM::class.java]
        sharedPref = SharedPreference(requireContext())

        showProgressDialog()
        viewModel.fetchAllCoach()

        adapterTimeTableBooking.clearData()
        adapterCoach.clearData()
        adapterServiceRules.clearData()

        iniView()
        setObserveData()
        iniViewFlipper()
        setOnClicks()
    }

    private fun iniView() {
        // set current date
        val currentDate = Calendar.getInstance()
        val thaiYear = currentDate.get(Calendar.YEAR)
        val thaiMonth = currentDate.get(Calendar.MONTH) + 1
        val thaiDay = currentDate.get(Calendar.DAY_OF_MONTH)
        val formattedThaiDay = String.format("%02d", thaiDay)
        val formattedThaiMonth = String.format("%02d", thaiMonth)
        val currentDateString = "$formattedThaiDay/$formattedThaiMonth/$thaiYear"
        binding.etCalendar.text = currentDateString
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

        binding.spnTypeSpot.adapter = setArrayAdapter(requireContext(), MockData.typeSpotList)

        binding.spnTypeSpot.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                if (binding.etCalendar.text.trim()
                        .isNotEmpty() && binding.spnTypeSpot.adapter != null
                ) {

                    val parts = binding.etCalendar.text.trim().split("/")
                    val day = parts[0]
                    val month = parts[1]
                    val year = parts[2]
                    // สร้างรูปแบบใหม่ "yyyy-MM-dd"
                    val formattedDate = "$year-$month-$day"
                    val selectedTypeSpot = MockData.typeSpotList[position]
                    positionTypeSpot = position
                    // แปลงค่าเป็นรูปแบบที่คาดหวัง
                    val typeSpot = mapTypeSpotToApiValue(selectedTypeSpot)
                    showProgressDialog()
                    viewModel.fetchTimeTableBooking(typeSpot, formattedDate)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(requireContext(), "Nothing Selected", Toast.LENGTH_SHORT).show()
            }

        }

        binding.btnPlayTogether.setOnClickListener {
            if (!sharedPref.isLogIn()) {
                val i = Intent(requireContext(), SignInActivity::class.java)
                startActivity(i)
            } else {
                Intent(requireContext(), BookingMain::class.java).also { startActivity(it) }
            }
        }

        binding.btnGetStart.setOnClickListener {
            if (!sharedPref.isLogIn()) {
                val i = Intent(requireContext(), SignInActivity::class.java)
                startActivity(i)
            } else {
                Intent(requireContext(), BookingMain::class.java).also { startActivity(it) }
            }
        }

        binding.btnSeeMore.setOnClickListener {
            val i = Intent(this.requireContext(), CoachActivity::class.java)
            startActivity(i)
        }
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    private fun setObserveData() {
        viewModel.allCoachList.observe(viewLifecycleOwner) { coachList ->
            hideProgressDialog()
            val layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rcvCoach.layoutManager = layoutManager
            binding.rcvCoach.adapter = adapterCoach
            adapterCoach.addData(coachList)
            adapterCoach.notifyDataSetChanged()
            adapterCoach.coachItemClickListener = coachItemClickListener
            sharedViewModel.selectCoach(coachList)
            mockData()
        }

        viewModel.timeTableBooking.observe(viewLifecycleOwner) { data ->
            try {
                if (data == null) {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
                if (data != null) {
                    hideProgressDialog()
                    // แสดง View ที่ปิดไว้
                    binding.ListOffTable.visibility = View.VISIBLE
                    // ล้าง TableLayout ก่อน
                    binding.ListOffTable.removeAllViews()

                    // สร้าง TableLayout ภายใน ScrollView
                    val scrollView = ScrollView(requireContext())
                    val tableLayout = TableLayout(requireContext())

                    // สร้างแถวเพื่อ Header
                    val headerRow = TableRow(requireContext()).apply {
                        addView(TextView(requireContext()).apply {
                            text = "Time"
                            setTypeface(null, Typeface.BOLD)
                            setTextColor(ContextCompat.getColor(requireContext(), R.color.text_black))
                            setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                            textAlignment = View.TEXT_ALIGNMENT_CENTER
                            setBackgroundColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.text_background_color
                                )
                            )
                        })
                        for (i in 1..6) {
                            addView(TextView(requireContext()).apply {
                                text = "Court $i"
                                setTypeface(null, Typeface.BOLD)
                                setTextColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.text_black
                                    )
                                )
                                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                                textAlignment = View.TEXT_ALIGNMENT_CENTER
                                setBackgroundColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        R.color.gray_e3
                                    )
                                )
                            })
                        }
                    }
                    tableLayout.addView(headerRow)

                    // สร้างแถวเพื่อแสดงข้อมูลสำหรับแต่ละ slot
                    for (i in 0 until 17) {
                        val slotRow = TableRow(requireContext())

                        // เพิ่มเวลาในแนวตั้ง
                        val timeTextView = TextView(requireContext()).apply {
                            text = "${data[0].slots[i].startTime} - ${data[0].slots[i].endTime}"
                            setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                            setBackgroundColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.gray_e3
                                )
                            )
                        }

                        //ทำให้แต่ละช่องมีระยะห่าง
                        val marginLayoutParams = marginLayoutParams(margin, margin, margin, margin)
                        timeTextView.layoutParams = marginLayoutParams

                        slotRow.addView(timeTextView)

                        // เพิ่มสถานะการจองสำหรับแต่ละ court ในแนวตั้ง
                        for (court in data) {
                            if (court.slots.size > i) {
                                val isBooked = court.slots[i].isBooked
                                val courtStatusTextView = TextView(requireContext()).apply {
                                    if (isBooked) {
                                        text = "Booked"
                                        setBackgroundColor(
                                            ContextCompat.getColor(
                                                requireContext(),
                                                R.color.gray_e3
                                            )
                                        )
                                    } else {
                                        text = "Available"
                                        setBackgroundColor(
                                            ContextCompat.getColor(
                                                requireContext(),
                                                R.color.text_background_color
                                            )
                                        )
                                    }
                                    setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                                }
                                val marginLayoutParams =
                                    marginLayoutParams(margin, margin, margin, margin)
                                courtStatusTextView.layoutParams = marginLayoutParams
                                slotRow.addView(courtStatusTextView)
                            } else {
                                // ให้เพิ่มช่องที่ไม่มีข้อมูลเป็นสีขาว
                                val emptyTextView = TextView(requireContext()).apply {
                                    text = ""
                                    setBackgroundColor(
                                        ContextCompat.getColor(
                                            requireContext(),
                                            android.R.color.white
                                        )
                                    )
                                }

                                val marginLayoutParams =
                                    marginLayoutParams(margin, margin, margin, margin)
                                emptyTextView.layoutParams = marginLayoutParams
                                slotRow.addView(emptyTextView)
                            }
                        }

                        tableLayout.addView(slotRow)
                    }

                    scrollView.addView(tableLayout)
                    binding.ListOffTable.addView(scrollView)
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
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

}