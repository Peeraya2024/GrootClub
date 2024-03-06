package com.example.grootclub.ui.coach

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appzaza.base.BaseActivity
import com.example.grootclub.MainActivity
import com.example.grootclub.R
import com.example.grootclub.SharedViewModel
import com.example.grootclub.adapter.CoachItemClickListener
import com.example.grootclub.data.CoachListModelItem
import com.example.grootclub.data.MockData
import com.example.grootclub.data.Remote.ApiService
import com.example.grootclub.data.Remote.Repository.Home.CoachRepository
import com.example.grootclub.databinding.FragmentCoachsBinding
import com.example.grootclub.utils.setArrayAdapter

@Suppress("IMPLICIT_CAST_TO_ANY")
class CoachActivity : BaseActivity<FragmentCoachsBinding>() {
    override val bindLayout: (LayoutInflater) -> FragmentCoachsBinding
        get() = FragmentCoachsBinding::inflate

    private var adapter = CoachAdapter(arrayListOf())
    private var coachItemClickListener: CoachItemClickListener? = null
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var viewModel: CoachVM
    private lateinit var repository: CoachRepository
    private var lastClickedButtonId: Int = R.id.btnAll
    private val buttonClickedMap: MutableMap<Int, Boolean> = mutableMapOf()

    override fun prepareView(savedInstanceState: Bundle?) {
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]
        repository = CoachRepository(ApiService().getService())
        viewModel = ViewModelProvider(this, CoachVMFactory(repository))[CoachVM::class.java]

        showProgressDialog()
        viewModel.fetchAllCoach()
        adapter.clearData()

        initToolBar()
        setOnClicks()
        setObserveData()
    }

    private fun initToolBar() {
        binding.appBarMain.toolbar.btnIconEnd.setOnClickListener {
            val i = Intent(applicationContext, MainActivity::class.java)
            startActivity(i)
        }

        binding.appBarMain.toolbar.toolbarTitle.text = "Coach"

        binding.appBarMain.toolbar.btnIconStart.visibility = View.VISIBLE
        binding.appBarMain.toolbar.btnIconStart.setBackgroundResource(R.drawable.ic_arrow_back)
        binding.appBarMain.toolbar.btnIconStart.setOnClickListener {
            this.finish()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setObserveData() {
        viewModel.allCoachList.observe(this) { coachList ->
            hideProgressDialog()
            binding.rcv.layoutManager = LinearLayoutManager(this)
            binding.rcv.adapter = adapter
            adapter.addData(coachList)
            adapter.notifyDataSetChanged()
            adapter.coachItemClickListener = coachItemClickListener
        }
    }

    private fun setOnClicks() {
        binding.btnTennis.setOnClickListener {
            handleButtonClick(
                R.id.btnTennis,
                binding.spnSortSpot.selectedItemPosition
            )
        }
        binding.btnBadminton.setOnClickListener {
            handleButtonClick(
                R.id.btnBadminton,
                binding.spnSortSpot.selectedItemPosition
            )
        }
        binding.btnYoga.setOnClickListener {
            handleButtonClick(
                R.id.btnYoga,
                binding.spnSortSpot.selectedItemPosition
            )
        }
        binding.btnTableTennis.setOnClickListener {
            handleButtonClick(
                R.id.btnTableTennis,
                binding.spnSortSpot.selectedItemPosition
            )
        }
        binding.btnAerobic.setOnClickListener {
            handleButtonClick(
                R.id.btnAerobic,
                binding.spnSortSpot.selectedItemPosition
            )
        }
        binding.btnAll.setOnClickListener {
            handleButtonClick(
                R.id.btnAll,
                binding.spnSortSpot.selectedItemPosition
            )
        }

        binding.spnSortSpot.adapter = setArrayAdapter(this, MockData.nameSpotList)

        binding.spnSortSpot.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                handleButtonClick(getSelectedButtonId(), position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@CoachActivity, "Nothing Selected", Toast.LENGTH_SHORT).show()
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleButtonClick(clickedButtonId: Int, position: Int) {
        buttonClickedMap[clickedButtonId] = true
        lastClickedButtonId = clickedButtonId

        // เซ็ตสีของทุกปุ่มเป็นสีเขียวก่อน
        buttonClickedMap.keys.forEach { buttonId ->
            val button = findViewById<AppCompatButton>(buttonId)
            button.setBackgroundResource(R.drawable.bg_rounded_corner_button)
        }

        // เซ็ตปุ่มที่ถูกคลิกให้เป็นสีขาว
        val clickedButton = findViewById<AppCompatButton>(clickedButtonId)
        clickedButton.setBackgroundResource(R.drawable.bg_rounded_corner)

        // กรองและเรียงลำดับข้อมูล
        val filteredCoachList = when (clickedButtonId) {
            R.id.btnTennis -> filterAndSortCoachList("tennis", position)
            R.id.btnBadminton -> filterAndSortCoachList("badminton", position)
            R.id.btnYoga -> filterAndSortCoachList("yoga", position)
            R.id.btnTableTennis -> filterAndSortCoachList("table_tennis", position)
            R.id.btnAerobic -> filterAndSortCoachList("aerobic_dance", position)
            R.id.btnAll -> filterAndSortCoachList("all", position)
            else -> viewModel.allCoachList.value
        }

        // อัพเดท RecyclerView
        filteredCoachList?.let { coachList ->
            adapter.clearData()
            adapter.addData(coachList)
            adapter.notifyDataSetChanged()
        }
    }

    private fun filterAndSortCoachList(type: String?, position: Int?): List<CoachListModelItem>? {
        var coachList = viewModel.allCoachList.value ?: return null

        if (type != null && type != "all") {
            coachList = coachList.filter { it.type == type }
        }

        if (position != null) {
            coachList = when {
                type == "all" && position == 0 -> coachList
                position == 1 -> coachList.sortedBy { it.name } // A-Z
                position == 2 -> coachList.sortedByDescending { it.name } // Z-A
                else -> coachList
            }
        }

        return coachList
    }


    private fun getSelectedButtonId(): Int {
        return lastClickedButtonId
    }

}
